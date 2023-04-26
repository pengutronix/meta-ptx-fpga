module jtag_tw_tb;

    logic tck;
    logic tdi;
    logic tdo;
    logic tms;
    logic rst;

		jtag_vpi #(.DEBUG_INFO(0))
		jtag_vpi0 (
							 .tms (tms),
							 .tck (tck),
							 .tdi (tdi),
							 .tdo (tdo),

							 .enable(1'b1),
							 .init_done(1'b1)
							 );

    top dut(
						.tck_pad_i(tck),
						.tdi_pad_i(tdi),
						.tdo_pad_o(tdo),
						.tms_pad_i(tms),
						.rst_i(rst)
						);

    initial begin
				$dumpvars;
				$dumpfile("dump.lx2");
    end

		initial
				$monitor("t=%3d TCK=%b TMS=%b TDI=%b TDO=%b", $time, tck, tms, tdi, tdo);

endmodule
