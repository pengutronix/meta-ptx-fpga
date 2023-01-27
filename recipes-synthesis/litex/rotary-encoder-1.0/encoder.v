// based on Quad_compact from https://shadowcode.io/quadrature-decoder-verilog/

module rotary_encoder(
                      input             clk,
                      input             A,
                      input             B,
                      input             reset,
                      input wire [4:0]  din,
                      output wire [4:0] dout,
                      output reg        direction,
                      output wire       update
                      );

    reg [1:0] 		sync, AB;
    reg [1:0]       cs;
    wire [1:0]      tmp, ns;
    reg [6:0]       signal;

    // sync/debounce
    always @(posedge clk) begin
        sync <= {A, B};
        AB <= sync;
    end

    // OR signal[0] with signal[1] - creates pulses on every edge of quad signal
    assign tmp = {AB[1], AB[1] ^ AB[0]};

    assign ns = tmp - cs;
    assign update = ns[0];

    // the encoder we use, generates 4 pulses per "click".
    assign dout = signal >> 2;

    always @(posedge clk) begin
        if(reset) begin
            signal <= din;
            cs <= 0;
        end else if(ns[0] == 1'b1)
            begin
                cs <= cs + ns; // set current state to old state
                if (ns[1] == 1'b1) begin
                    signal <= signal - 1'b1;
                    direction <= 1'b0;
                end else begin
                    signal <= signal + 1'b1;
                    direction <= 1'b1;
                end
            end
    end
endmodule
