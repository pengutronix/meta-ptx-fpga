SUMMARY = "LiteX boards files"
HOMEPAGE = "https://github.com/litex-hub/litex-boards"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "\
	git://github.com/litex-hub/litex-boards;protocol=https \
"
SRCREV = "26943959b53ca86cf908de83bf197383d1538fc4"
PV = "0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit deploy
inherit litexnative

DEPENDS += "migen-native"
DEPENDS += "litex-native"
DEPENDS += "litex-boards-native"
DEPENDS += "litedram-native"
DEPENDS += "litex-pythondata-cpu-vexriscv-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"

inherit setuptools3

do_compile() {
    ${S}/litex_boards/targets/lambdaconcept_ecpix5.py \
   --no-compile-gateware \
   --gateware-dir build/lambdaconcept_ecpix5/gateware \
   --cpu-type vexriscv --sys-clk-freq 50e6 --with-ethernet
}

do_install() {
   install -d ${D}${datadir}/software
   install ${B}/build/lambdaconcept_ecpix5/gateware/* ${D}${datadir}/software
}
FILES_${PN} = "${datadir}/software"
