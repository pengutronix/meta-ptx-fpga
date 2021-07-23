SUMMARY = "Experiments with Linux on LiteX-Rocket"
HOMEPAGE = "https://github.com/litex-hub/linux-on-litex-rocket"
SECTION = "devel/hdl"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "\
    git://github.com/litex-hub/linux-on-litex-rocket;protocol=https \
    file://0001-conf-ecpix5.dts-fix-typo.patch \
"
SRCREV = "c9e032c1e1fc2d2c42cffedcbfdaca72b221f062"
PV = "0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit deploy

# do not depend on libc or compiler libs, only the compiler is needed
DEPENDS_remove = "virtual/${TARGET_PREFIX}compilerlibs virtual/libc"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "ecpix5"

# HACK: riscv-pk requires a dts file for the board. Install the dts for the
# ecpix5 into the deploy directory for the riscv-pk recipe.
do_deploy() {
    install -Dm 0644 ${WORKDIR}/git/conf/ecpix5.dts ${DEPLOYDIR}/ecpix5.dts
}
do_install[noexec] = "1"
addtask deploy before do_build after do_install
