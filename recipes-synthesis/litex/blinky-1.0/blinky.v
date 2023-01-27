module blinky(input wire clk,
	      input wire reset,
	      input wire set_led,
              output reg led,
	      output reg [27:0] counter);

parameter CLK_DIV = 50000000;

reg clk_divider;

always @(posedge clk) begin
    if (reset == 1'b0)
	counter <= CLK_DIV;
    else if (counter == 0)
	counter <= CLK_DIV;
    else
	counter <= counter - 1;
end

always @(posedge clk) begin
		if (reset == 1'b0)
				clk_divider <= 0;
		else if (counter == 0)
				clk_divider <= ~clk_divider;
end

always @(posedge clk_divider or negedge reset)
		if (reset == 1'b0)
				led <= 1'b0;
		else if (set_led == 1'b1)
				led <= 1'b1;
		else
				led <= ~led;

endmodule
