SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

inherit deploy
inherit fpga

DEPENDS += "${@fpga_family_depends(d)}"
DEPENDS += "dtc-native"
DEPENDS += "litex-boards-vexriscv-software"
DEPENDS += "litex-boards-vexriscv-gateware"

SRC_URI += "file://boot.json"

do_compile () {
   cp ${STAGING_DIR_TARGET}/usr/share/software/mem.init ${STAGING_DIR_TARGET}/usr/share/gateware/software.init

   cd ${STAGING_DIR_TARGET}/usr/share/gateware/

   ecpbram -v -i lambdaconcept_ecpix5.config -o lambdaconcept_ecpix5_update.config \
	--from mem.init --to software.init

   ecppack lambdaconcept_ecpix5_update.config \
	  --svf lambdaconcept_ecpix5.svf \
	  --bit lambdaconcept_ecpix5.bit \
	  --bootaddr 0

   dtc -I dts -O dtb -o ${B}/litex-vexriscv-ecpix5.dtb ${STAGING_DIR_TARGET}/usr/share/software/litex-vexriscv-ecpix5.dts
}

do_deploy () {
	cd ${STAGING_DIR_TARGET}/usr/share/gateware
	install -Dm 0644 lambdaconcept_ecpix5.bit ${DEPLOYDIR}/ptxsoc.bit
	install -Dm 0644 lambdaconcept_ecpix5.svf ${DEPLOYDIR}/ptxsoc.svf
	cd ${B}
	install -Dm 0644 litex-vexriscv-ecpix5.dtb ${DEPLOYDIR}/litex-vexriscv-ecpix5.dtb

	# HACK: The boot.json describes where the LiteX bios puts the binaries
	# that is loads. It would be better, to generate it with the image,
	# while putting the binaries into the partition. However, the
	# addresses need to be in RAM and its address is defined in the
	# bitstream. Therefore, it is part of the bitstream recipe for now.
	cp ${WORKDIR}/boot.json ${DEPLOYDIR}/boot.json
}
do_install[noexec] = "1"

addtask deploy before do_build after do_install

BBCLASSEXTEND = "native nativesdk"
