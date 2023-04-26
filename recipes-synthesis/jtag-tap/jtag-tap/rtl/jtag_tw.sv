module jtag_tw (
		input logic tck_i,
		input logic tdi_i,
		output logic tdo_o,
		input logic rst_i
		);

    assign tdo_o = tdi_i;

endmodule
