SUMMARY = "LiteX boards files"
HOMEPAGE = "https://github.com/litex-hub/litex-boards"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "\
	git://github.com/strumtrar/litex-boards;protocol=https \
"
SRCREV = "7b26f2b3dee2c89c76b945229f9287647a25369b"
PV = "0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit litexnative
inherit deploy
inherit fpga

DEPENDS += "${@fpga_family_depends(d)}"
DEPENDS += "yosys-native"
DEPENDS += "migen-native"
DEPENDS += "litex-native"
DEPENDS += "litex-boards-native"
DEPENDS += "litedram-native"
DEPENDS += "litex-pythondata-cpu-vexriscv-smp-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"

inherit setuptools3

do_compile() {
    ${S}/litex_boards/targets/lambdaconcept_ecpix5.py \
   --no-compile-software \
   --cpu-type vexriscv_smp --sys-clk-freq 50e6 --with-ethernet

   cd ${B}/build/lambdaconcept_ecpix5/gateware
   yosys -l lambdaconcept_ecpix5.rpt lambdaconcept_ecpix5.ys
   nextpnr-ecp5 \
	  --json lambdaconcept_ecpix5.json \
	  --lpf lambdaconcept_ecpix5.lpf \
	  --textcfg lambdaconcept_ecpix5.config \
	  --um5g-85k \
	  --package CABGA554 \
	  --speed 8 \
	  --timing-allow-fail \
	  --seed 1
}

do_install() {
   install -d ${D}${datadir}/gateware
   install ${B}/build/lambdaconcept_ecpix5/gateware/* ${D}${datadir}/gateware
}
FILES_${PN} = "${datadir}/gateware"
BBCLASSEXTEND = "native nativesdk"
