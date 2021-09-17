SUMMARY = "Experiments with Linux on LiteX-Rocket"
HOMEPAGE = "https://github.com/litex-hub/linux-on-litex-rocket"
SECTION = "devel/hdl"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "\
    git://github.com/litex-hub/linux-on-litex-rocket;protocol=https \
"
SRCREV = "af07b00d26a9594fbf31c158dd20a3c0008456d5"
PV = "0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit deploy

# do not depend on libc or compiler libs, only the compiler is needed
DEPENDS_remove = "virtual/${TARGET_PREFIX}compilerlibs virtual/libc"

DEPENDS += "dtc-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "ecpix5"

do_compile() {
    dtc -I dts -O dtb -o ${B}/ecpix5.dtb ${S}/conf/ecpix5.dts
}

do_deploy() {
    install -Dm 0644 ${B}/ecpix5.dtb ${DEPLOYDIR}/ecpix5.dtb
    install -Dm 0644 ${S}/boot.json ${DEPLOYDIR}/boot.json
}
do_install[noexec] = "1"
addtask deploy before do_build after do_install
