SUMMARY = "LiteX boards files"
HOMEPAGE = "https://github.com/litex-hub/litex-boards"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "\
	git://github.com/litex-hub/litex-boards;protocol=https \
	file://0001-ecpix5-enable-dynamic-ip.patch \
"
SRCREV = "2021.08"
PV = "2021.08"

S = "${WORKDIR}/git"

inherit deploy
inherit litexnative
inherit fpga

DEPENDS += "yosys-native"
DEPENDS += "${@fpga_family_depends(d)}"
DEPENDS += "migen-native"
DEPENDS += "litex-native"
DEPENDS += "litex-boards-native"
DEPENDS += "litex-pythondata-cpu-rocket-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"
DEPENDS += "litedram-native"
DEPENDS += "litesdcard-native"

inherit setuptools3

do_compile() {
    ${S}/litex_boards/targets/lambdaconcept_ecpix5.py --build \
   --cpu-type rocket --cpu-variant linuxd --sys-clk-freq 50e6 \
   --with-ethernet --with-sdcard
}

do_deploy () {
    install -Dm 0644 ${B}/build/lambdaconcept_ecpix5/gateware/lambdaconcept_ecpix5.bit ${DEPLOYDIR}/top.bit
    install -Dm 0644 ${B}/build/lambdaconcept_ecpix5/gateware/lambdaconcept_ecpix5.svf ${DEPLOYDIR}/top.svf
}
do_install[noexec] = "1"

addtask deploy before do_build after do_install

