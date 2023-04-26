LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

# No information for SRC_URI yet (only an external source tree was specified)
SRC_URI = "file://rtl/tap_top.v \
	   file://rtl/jtag_tw.sv \
	   file://rtl/jtag_vpi.v \
	   file://rtl/top.sv \
	   file://rtl/include/timescale.v \
	   file://rtl/include/tap_defines.v \
	   file://sw/jtag_common.c \
	   file://sw/jtag_common.h \
	   file://sw/jtag_vpi.c \
	   file://tb/jtag_tw_tb.v \
	   file://Makefile \
	   file://vpi.cfg \
           "

COMPATIBLE_MACHINE = "ecpix5-vexriscv"
PACKAGES = "${PN}"

inherit deploy fpga

DEPENDS += "icarus-verilog-native"
DEPENDS += "openocd-native"

S = "${WORKDIR}"

export VVP = "vvp -M${S}"

do_compile () {
    oe_runmake -f ${S}/Makefile build
}

do_sim[dirs] += "${B}"
do_sim[depends] += "openocd-native:do_populate_sysroot"
do_sim[nostamp] = "1"
do_sim () {
    vvp -n -mjtag_tw -M${S} ${S}/jtag_tw.vvp +jtag_vpi_enable=1 -lxt2 &
    openocd -f ${S}/vpi.cfg
}
addtask sim after do_compile

do_deploy() {
    :
}
addtask deploy before do_build after do_install
