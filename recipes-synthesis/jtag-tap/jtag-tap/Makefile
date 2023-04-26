SRC = rtl/tap_top.v rtl/jtag_tw.sv rtl/jtag_vpi.v rtl/top.sv
VPI_SRC = sw/jtag_common.c sw/jtag_vpi.c
TESTBENCH = tb/jtag_tw_tb.v
TBOUTPUT = dump.lx2

COMPILER = iverilog
VIEWER = gtkwave

check : $(TESTBENCH) $(SRC)
	$(COMPILER) -g2012 -v -Iinclude -I$(INCLUDE) -s top $(SRC) -o jtag_tw

build:
	@echo '##### Building #####'
	$(COMPILER)-vpi --name=jtag_tw $(VPI_SRC) > /dev/null
	$(COMPILER) -Irtl/include -o jtag_tw.vvp -g2012 $(SRC) $(TESTBENCH)

vpi:
	@echo '##### Start VPI simulation #####'
	vvp -n -mjtag_tw jtag_tw.vvp +jtag_vpi_enable=1 -lxt2

clean:
	rm -f *.o *.vpi *.vvp
