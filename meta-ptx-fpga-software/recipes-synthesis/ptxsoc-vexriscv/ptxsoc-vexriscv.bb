SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

inherit deploy
inherit fpga

DEPENDS += "${@fpga_family_depends(d)}"
DEPENDS += "litex-boards-vexriscv-software"
DEPENDS += "litex-boards-vexriscv-gateware"

do_compile () {
   cp ${STAGING_DIR_TARGET}/usr/share/software/mem.init ${STAGING_DIR_TARGET}/usr/share/gateware/software.init

   cd ${STAGING_DIR_TARGET}/usr/share/gateware/

   pwd

   ecpbram -v -i lambdaconcept_ecpix5.config -o lambdaconcept_ecpix5_update.config \
	--from mem.init --to software.init

   ecppack lambdaconcept_ecpix5_update.config \
	  --svf lambdaconcept_ecpix5.svf \
	  --bit lambdaconcept_ecpix5.bit \
	  --bootaddr 0
}

do_deploy () {
	cd ${STAGING_DIR_TARGET}/usr/share/gateware
	install -Dm 0644 lambdaconcept_ecpix5.bit ${DEPLOYDIR}/ptxsoc.bit
	install -Dm 0644 lambdaconcept_ecpix5.svf ${DEPLOYDIR}/ptxsoc.svf
}
do_install[noexec] = "1"

addtask deploy before do_build after do_install

BBCLASSEXTEND = "native nativesdk"
