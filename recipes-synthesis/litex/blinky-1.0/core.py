import os

from migen import *
from litex.soc.interconnect.csr import *
from litex.soc.interconnect.csr_eventmanager import *

class Blinky(Module, AutoCSR):
    def __init__(self, platform, clk_div, pads):
        self.pads = pads

        ena = Signal()
        nreset = Signal()
        status = Signal()
        # generate a signal with bitwidth 28
        counter= Signal(28)

        self.ledCtrl = CSRStorage(fields=[
            CSRField("ena", size=1, description="Enable LED"),
            CSRField("write", size=1, description="Issue DMA Write")
        ])
        self.nreset = CSRStorage(1, fields=[
            CSRField("nReset", size=1, reset=1, description="Low-active Reset")
        ])
        self.led = CSRStatus(fields=[
            CSRField("status", size=1, description="LED Status"),
        ])
        self.counter = CSRStatus(28, fields=[
            CSRField("counter", size=28, description="Counter")])

        self.comb += [
            ena.eq(self.ledCtrl.fields.ena),
            nreset.eq(self.nreset.fields.nReset)
        ]
        self.comb += [
            self.led.fields.status.eq(status),
            self.counter.fields.counter.eq(counter),
        ]
        self.comb += pads.eq(status)

        self.specials += Instance("blinky",
                                   p_CLK_DIV = clk_div,
                                   i_clk = ClockSignal(),
                                   i_reset = nreset,
                                   i_set_led = ena,
                                   o_led = status,
                                   o_counter = counter
        )

        platform.add_source(os.path.join(os.path.dirname(__file__), "verilog", "blinky.v"))
