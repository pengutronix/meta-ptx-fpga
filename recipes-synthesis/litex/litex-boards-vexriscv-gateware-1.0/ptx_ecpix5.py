#!/usr/bin/env python3

#
# This file is part of LiteX-Boards.
#
# Copyright (c) 2023 Steffen Trumtrar <kernel@pengutronix.de>
# SPDX-License-Identifier: BSD-2-Clause

from migen import *
import tempfile
import litex_boards.targets.lambdaconcept_ecpix5 as lc_ecpix5
from litex_boards.platforms import lambdaconcept_ecpix5
from litex.soc.integration.builder import *
from litex.soc.integration.soc import SoCRegion
from litex.soc.integration.soc_core import *
from litex.build.lattice.trellis import trellis_args, trellis_argdict
from litex.soc.cores.cpu.vexriscv_smp import VexRiscvSMP

class PtxSoC(lc_ecpix5.BaseSoC):
    def __init__(self, device="85F", sys_clk_freq=int(75e6),
        with_ethernet          = False,
        with_led_chaser        = False,
        with_ws2812            = True,
        with_rotary            = True,
        with_blinky            = False,
        **kwargs):
        self.gateware_dir = ""

        kwargs["integrated_sram_size"] = 1024 * 6
        kwargs["integrated_rom_size"]  = 1024 * 64
        # use Wishbone and L2 for memory access
        kwargs["l2_size"] = 2048

        # SoCCore ----------------------------------------------------------------------------------
        super().__init__(device, sys_clk_freq, with_ethernet, with_led_chaser, **kwargs)

        if with_ws2812:
            from litex.build.generic_platform import Pins, IOStandard
            from litex.soc.integration.soc import SoCRegion
            from litex.soc.cores.led import WS2812
            self.platform.add_extension([("ws2812", 0, Pins("pmod7:1"), IOStandard("LVCMOS33"))])

            # - mem_list to get base address
            # - mem_write $BASE+i 0xGGRRBB
            self.submodules.ws2812 = WS2812(
                self.platform.request("ws2812"),
                nleds=32, sys_clk_freq=sys_clk_freq)
            self.bus.add_slave(name="ws2812", slave=self.ws2812.bus, region=SoCRegion(
                origin = 0x20000000,
                size = 4*32,
            ))

        if with_rotary:
            from litex.build.generic_platform import Pins, IOStandard
            from rotary_encoder.core import Rotary_Encoder

            # - BASE=0xf0002000
            # - RESET=0x0
            # - DIN=0x4
            # - DOUT=0x8
            # - DIRECTION=0xc
            self.platform.add_extension([("rotary", 0, Pins("pmod7:4", "pmod7:5"), IOStandard("LVCMOS33"))])
            rotary_pads = self.platform.request("rotary", 0)
            encoder = Rotary_Encoder(
                platform = self.platform,
                A = rotary_pads[0],
                B = rotary_pads[1]
            )
            self.submodules.encoder = encoder
            self.bus.add_master(name="encoder", master=self.encoder.bus)
            self.csr.add("rotary", n=13)

        if with_blinky:
            from blinky.core import Blinky

            leds_pads = []
            for i in range(4):
                rgb_led_pads = self.platform.request("rgb_led", i)
                comb += [getattr(rgb_led_pads, n).eq(1) for n in "rg"]
                leds_pads += [getattr(rgb_led_pads, n) for n in "b"]

            self.submodules.blinky1 = Blinky(
                platform = self.platform,
                pads = leds_pads[0],
                clk_div = 25000000)
            self.csr.add("blinky1", n=9)
            self.submodules.blinky2 = Blinky(
                platform = self.platform,
                pads = leds_pads[1],
                clk_div = 25000000)
            self.csr.add("blinky2", n=10)
            self.submodules.blinky3 = Blinky(
                platform = self.platform,
                pads = leds_pads[2],
                clk_div = 25000000)
            self.csr.add("blinky3", n=11)
            self.submodules.blinky4 = Blinky(
                platform = self.platform,
                pads = leds_pads[3],
                clk_div = 25000000)
            self.csr.add("blinky4", n=12)

    def set_gateware_dir(self, gateware_dir):
        self.gateware_dir = gateware_dir

    def initialize_rom(self, data, args=[]):
        if args and args.no_compile_software:
            (_, path) = tempfile.mkstemp()
            subprocess.check_call(["ecpbram", "-g", path, "-w", str(self.rom.mem.width), "-d", str(int(self.integrated_rom_size / 4)), "-s" "0"])
            random_file = open(path, 'r')
            data = []
            random_lines = random_file.readlines()
            for line in random_lines:
                data.append(int(line, 16))

            os.remove(path)

        self.init_rom(name="rom", contents=data, auto_size=True)

        # Save actual expected contents for future use as gateware/mem.init
        content = ""
        formatter = "{:0" + str(int(self.rom.mem.width / 4)) + "X}\n"
        for d in data:
            content += formatter.format(d).zfill(int(self.rom.mem.width / 4))
        romfile = os.open(os.path.join(self.gateware_dir, "mem.init"), os.O_WRONLY | os.O_CREAT)
        os.write(romfile, content.encode())
        os.close(romfile)

def main():
    from litex.soc.integration.soc import LiteXSoCArgumentParser
    parser = LiteXSoCArgumentParser(description="LiteX SoC on ECPIX-5")
    target_group = parser.add_argument_group(title="Target options")
    target_group.add_argument("--build",           action="store_true", help="Build bitstream.")
    target_group.add_argument("--device",          default="85F",       help="ECP5 device (45F or 85F).")
    target_group.add_argument("--sys-clk-freq",    default=75e6,        help="System clock frequency.")
    target_group.add_argument("--with-sdcard",     action="store_true", help="Enable SDCard support.")
    target_group.add_argument("--with-blinky",     action="store_true", help="Enable Blinky support.")
    target_group.add_argument("--with-ws2812",     action="store_true", help="Enable WS2812 support.")
    target_group.add_argument("--with-rotary",     action="store_true", help="Enable Rotary support.")
    ethopts = target_group.add_mutually_exclusive_group()
    ethopts.add_argument("--with-ethernet",  action="store_true", help="Enable Ethernet support.")

    builder_args(parser)
    soc_core_args(parser)
    trellis_args(parser)
    VexRiscvSMP.args_fill(parser)
    args = parser.parse_args()

    soc = PtxSoC(
        device                 = args.device,
        sys_clk_freq           = int(float(args.sys_clk_freq)),
        with_ethernet          = args.with_ethernet,
        with_blinky            = args.with_blinky,
        with_ws2812            = args.with_ws2812,
        with_rotary            = args.with_rotary,
        with_led_chaser        = False,
        **soc_core_argdict(args)
    )
    if args.with_sdcard:
        soc.add_sdcard()

    args.with_wishbone_memory = True

    VexRiscvSMP.args_read(args)
    builder = Builder(soc, **builder_argdict(args))
    soc.set_gateware_dir(builder.gateware_dir)
    builder.build(**trellis_argdict(args), run=args.build)

    soc.initialize_rom([], args)

if __name__ == "__main__":
    main()
