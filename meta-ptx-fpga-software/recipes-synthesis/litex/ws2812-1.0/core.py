import os

from migen import *
from litex.soc.interconnect.csr import *
from litex.soc.interconnect.csr_eventmanager import *

class WS2812(Module, AutoCSR):
    def __init__(self, platform, en):
        reset = Signal()
        data = Signal(24)
        led_num = Signal(8)
        wr = Signal()

        self.reset = CSRStorage(1, fields=[
            CSRField("Reset", size=1, reset=1, description="High-active Reset")
        ])
        self.din = CSRStatus(24, fields=[
            CSRField("din", size=24, description="Data-In")
            CSRField("led", size=8, description="LED")
        ])

        self.comb += [
            reset.eq(self.reset.fields.Reset)
        ]

        self.comb += [
            data.eq(self.din.fields.din)
            led_num.eq(self.din.fields.led)
        ]

        self.specials += Instance("ws2812",
                                   p_NUM_LEDS = 32,
                                   p_CLK_MHZ = 50,
                                   i_clk = ClockSignal(),
                                   i_reset = reset,
                                   i_rgb_data = data,
                                   i_led_num = led_num,
                                   i_en = en
        )

        platform.add_source(os.path.join(os.path.dirname(__file__), "verilog", "ws2812.v"))
