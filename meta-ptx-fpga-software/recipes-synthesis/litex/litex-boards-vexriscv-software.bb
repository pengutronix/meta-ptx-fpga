SUMMARY = "LiteX boards files"
HOMEPAGE = "https://github.com/litex-hub/litex-boards"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "\
	git://github.com/strumtrar/litex-boards;protocol=https \
"
SRCREV = "master"
PV = "0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit deploy
inherit litexnative
inherit python3native

# inhibit default deps, to exclude libc
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "virtual/${HOST_PREFIX}gcc"

DEPENDS += "meson-native ninja-native"
DEPENDS += "migen-native"
DEPENDS += "litex-native"
DEPENDS += "litex-boards-native"
DEPENDS += "litedram-native"
DEPENDS += "litesdcard-native"
DEPENDS += "litex-pythondata-software-picolibc-native"
DEPENDS += "litex-pythondata-cpu-vexriscv-smp-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"

# disable any security flags set by security_flags.inc (e.g. poky distro)
SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

do_compile() {
    ${S}/litex_boards/targets/lambdaconcept_ecpix5.py \
        --no-compile-gateware \
        --gateware-dir build/lambdaconcept_ecpix5/gateware \
        --cpu-type vexriscv_smp \
        --cpu-variant linux \
        --sys-clk-freq 50e6 \
        --with-ethernet \
        --with-sdcard
}

do_install() {
   install -d ${D}${datadir}/software

   # The mem.init contains the LiteX bios as ROM code, which can be used to
   # update the mem.init of an existing bitstream.
   install ${B}/build/lambdaconcept_ecpix5/gateware/mem.init ${D}${datadir}/software
}
FILES:${PN} = "${datadir}/software"
