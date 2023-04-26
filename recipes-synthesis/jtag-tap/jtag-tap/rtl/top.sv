module top(
	   input logic	tck_pad_i,
	   input logic	tdi_pad_i,
	   output logic	tdo_pad_o,
	   input logic	tms_pad_i,
	   input logic	rst_i
	   );

    logic jtag_tw_tdo;
    logic jtag_tw_tdi;

    jtag_tw jtag_tw(
										.tck_i (tck_pad_i),
										.tdi_i (jtag_tw_tdi),
										.tdo_o (jtag_tw_tdo),
										.rst_i (rst_i)
										);

    tap_top jtag_tap (
				 // Ports to pads
				 .tdo_pad_o (tdo_pad_o),
				 .tms_pad_i	(tms_pad_i),
				 .tck_pad_i	(tck_pad_i),
				 .tdi_pad_i	(tdi_pad_i),
				 .trst_pad_i (rst_i),

				 .tdo_padoe_o (),

				 .tdo_o	(jtag_tw_tdi),

				 .shift_dr_o (jtag_tap_shift_dr),
				 .pause_dr_o (jtag_tap_pause_dr),
				 .update_dr_o	(jtag_tap_update_dr),
				 .capture_dr_o (jtag_tap_capture_dr),

				 .extest_select_o (),
				 .sample_preload_select_o	(),
				 .mbist_select_o (),
				 .debug_select_o (dbg_if_select),

				 .bs_chain_tdi_i (1'b0),
				 .mbist_tdi_i	(1'b0),
				 .debug_tdi_i	(jtag_tw_tdo)
				 );
endmodule
