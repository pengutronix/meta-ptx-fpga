SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "\
	file://ptx_ecpix5.py \
"

PACKAGES = "${PN}"
S = "${WORKDIR}"

inherit litexnative
inherit deploy
inherit python3native
inherit fpga

# inhibit default deps, to exclude libc
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "virtual/${HOST_PREFIX}gcc"

DEPENDS += "${@fpga_family_depends(d)}"
DEPENDS += "meson-native ninja-native"
DEPENDS += "yosys-native"
DEPENDS += "migen-native"
DEPENDS += "litex-native"
DEPENDS += "litex-boards-native"
DEPENDS += "blinky-native"
DEPENDS += "rotary-encoder-native"
DEPENDS += "litedram-native"
DEPENDS += "litesdcard-native"
DEPENDS += "litex-pythondata-software-picolibc-native"
DEPENDS += "litex-pythondata-cpu-vexriscv-smp-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"

# disable any security flags set by security_flags.inc (e.g. poky distro)
SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

do_compile() {
    ${S}/ptx_ecpix5.py \
        --no-compile-software \
	--build \
        --cpu-type vexriscv_smp \
        --cpu-variant linux \
        --sys-clk-freq 50e6 \
	--l2-size 2048 \
        --with-ethernet \
        --with-wishbone-memory \
        --with-sdcard \
        --with-ws2812 \
        --with-rotary \
	--csr-json build/csr.json

    cd ${B}/build/lambdaconcept_ecpix5/gateware

    yosys -l lambdaconcept_ecpix5.rpt lambdaconcept_ecpix5.ys

    nextpnr-ecp5 \
        --json lambdaconcept_ecpix5.json \
        --lpf lambdaconcept_ecpix5.lpf \
        --textcfg lambdaconcept_ecpix5.config \
        --um5g-85k \
        --package CABGA554 \
        --speed 8 \
        --seed 1

   ${STAGING_DIR_NATIVE}/usr/bin/litex_json2dts_linux --root-device mmcblk0p2 --initrd disabled ${B}/build/csr.json > ${B}${build}/litex-vexriscv-ecpix5.dts
}

do_install() {
	install -d ${D}${datadir}/gateware

	install ${B}/build/lambdaconcept_ecpix5/gateware/* ${D}${datadir}/gateware
	install ${B}/build/csr.json ${D}${datadir}/gateware

	install ${B}${build}/litex-vexriscv-ecpix5.dts ${D}${datadir}/gateware
}

FILES:${PN} = "${datadir}/gateware"
BBCLASSEXTEND = "native nativesdk"
