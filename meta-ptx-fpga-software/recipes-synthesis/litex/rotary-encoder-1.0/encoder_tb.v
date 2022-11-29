module rotary_encoder_tb();

    reg clk;
    reg	reset;
    reg	A, B;
    wire [4:0] count;
    reg [4:0] din;

    rotary_encoder renc(.clk(clk),
			.reset(reset),
			.A(A),
			.B(B),
			.din(din),
			.dout(count)
			);

    initial begin
	$dumpfile("encoder.vcd");
	$dumpvars;
	clk   = 0;
	reset = 1;
	A     = 0;
	B     = 0;
        din   = 5'b11111;
	#30;
	reset = 0;
	A     = 1;
	#5 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;

	#40 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	#20 B = 0;
	#20 B = 1;
	$display("done");
	$finish;
    end

    always @(posedge clk) begin
	#20
	A = ~A;
    end

    always #5
	clk = ~clk;

endmodule
