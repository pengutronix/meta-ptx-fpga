import os

from migen import *
from litex.soc.interconnect.csr import *
from litex.soc.interconnect.csr_eventmanager import *
from litex.soc.interconnect import wishbone
from litex.soc.cores.dma import WishboneDMAWriter

class Rotary_Encoder(Module, AutoCSR):
    def __init__(self, platform, A, B):
        self.bus = bus = wishbone.Interface(data_width=32)
        dma_writer = WishboneDMAWriter(self.bus)
        self.submodules += dma_writer

        reset = Signal()

        Pin_A = Signal()
        Pin_B = Signal()
        Dout = Signal(5)
        Din = Signal(5)
        direction = Signal()
        update = Signal()

        self.reset = CSRStorage(1, fields=[
            CSRField("Reset", size=1, reset=0, description="High-active Reset")
        ])
        self.din = CSRStatus(5, fields=[
            CSRField("din", size=5, description="Start value")])
        self.dout = CSRStatus(5, fields=[
            CSRField("dout", size=5, description="Current value")])
        self.rgb = CSRStorage(24, fields=[
            CSRField("rgb", size=24, description="LED RGB value")])
        self.direction = CSRStatus(1, fields=[
            CSRField("direction", size=1, description="Current direction (0=down,1=up)")])

        self.dma_ctrl = CSRStorage(2, fields=[
            CSRField("reset", size=1, reset=0, description="Reset DMA Engine"),
            CSRField("run", size=1, description="Start DMA Engine")])
        self.dma_status = CSRStatus(32, description = "DMA Status", fields = [
            CSRField(name="idle", size=1, description="DMA Idle"),
            CSRField(name="write", size=1, description="DMA Write"),
            CSRField(name="done", size=1, description="DMA Done"),
        ]);

        # DMA Engine
        self.dma_addr = CSRStorage(32, atomic_write=True)

        shift = log2_int(self.bus.data_width//8)
        dma_addr_base = Signal(self.bus.adr_width)
        dma_addr_offset = Signal(self.bus.adr_width)
        dma_data = Signal(self.bus.data_width)
        null = Signal(self.bus.data_width)

        self.comb += dma_addr_base.eq(self.dma_addr.storage[shift:])
        self.comb += dma_addr_offset.eq(self.dout.fields.dout)
        self.comb += dma_data.eq(self.rgb.fields.rgb)
        self.comb += null.eq(0)

        fsm = FSM(reset_state="IDLE")
        fsm = ResetInserter()(fsm)
        self.comb += fsm.reset.eq(self.dma_ctrl.fields.reset)
        self.submodules += fsm

        fsm.act("IDLE",
                self.dma_status.fields.done.eq(0),
                self.dma_status.fields.write.eq(0),
                self.dma_status.fields.idle.eq(1),
                If(self.dma_ctrl.fields.run,
                   NextState("DMA-CLEAN")))
        fsm.act("DMA-CLEAN",
                self.dma_status.fields.idle.eq(0),
                self.dma_status.fields.write.eq(1),

                If(direction,
                   If(dma_addr_offset == 0,
                      dma_writer.sink.address.eq(dma_addr_base + 0x1f)
                   ).Else(
                      dma_writer.sink.address.eq(dma_addr_base + dma_addr_offset - 1))
                ).Else(
                   If(dma_addr_offset == 0x1f,
                      dma_writer.sink.address.eq(dma_addr_base)
                   ).Else(
                      dma_writer.sink.address.eq(dma_addr_base + dma_addr_offset + 1))
                ),
                dma_writer.sink.data.eq(null),
                dma_writer.sink.last.eq(1),
                dma_writer.sink.valid.eq(1),
                If(dma_writer.sink.ready,
                   NextState("DMA-WRITE")))
        fsm.act("DMA-WRITE",
                self.dma_status.fields.idle.eq(0),
                self.dma_status.fields.write.eq(1),

                dma_writer.sink.address.eq(dma_addr_base + dma_addr_offset),
                dma_writer.sink.data.eq(dma_data),
                dma_writer.sink.last.eq(1),
                dma_writer.sink.valid.eq(1),
                If(dma_writer.sink.ready,
                   NextState("DONE")))
        fsm.act("DONE",
                self.dma_status.fields.write.eq(0),
                self.dma_status.fields.done.eq(1),
                If(update,
                   NextState("IDLE")))

        self.comb += [
            reset.eq(self.reset.fields.Reset)
        ]

        self.comb += [
            self.dout.fields.dout.eq(Dout),
            Din.eq(self.din.fields.din),
            self.direction.fields.direction.eq(direction)
        ]

        self.pin_a = CSRStorage(1)
        self.pin_b = CSRStorage(1)

        self.comb += Pin_A.eq(self.pin_a.storage)
        self.comb += Pin_B.eq(self.pin_b.storage)

        self.specials += Instance("rotary_encoder",
                                  i_clk = ClockSignal(),
                                  i_reset = reset,
                                  i_A = Pin_A,
                                  i_B = Pin_B,
                                  i_din = Din,
                                  o_dout = Dout,
                                  o_direction = direction,
                                  o_update = update
        )

        platform.add_source(os.path.join(os.path.dirname(__file__), "verilog", "encoder.v"))
