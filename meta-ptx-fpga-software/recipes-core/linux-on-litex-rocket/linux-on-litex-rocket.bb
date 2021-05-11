SUMMARY = "Experiments with Linux on LiteX-Rocket"
HOMEPAGE = "https://github.com/litex-hub/linux-on-litex-rocket"
SECTION = "devel/hdl"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "git://github.com/litex-hub/linux-on-litex-rocket;protocol=https"
SRCREV = "cc45c13b2860653c08abb59aaeb0b6681e3cf098"
PV = "0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit deploy
inherit litexnative
inherit fpga

DEPENDS += "dtc-native"
DEPENDS += "yosys-native"
DEPENDS += "${@fpga_family_depends(d)}"
DEPENDS += "migen-native"
DEPENDS += "litex-native"

# device modules
DEPENDS += "litex-pythondata-cpu-rocket-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"
DEPENDS += "litex-pythondata-misc-tapcfg-native"
DEPENDS += "litex-boards-rocket"
DEPENDS += "litedram-native"
DEPENDS += "liteeth-native"
DEPENDS += "litevideo-native"
DEPENDS += "litescope-native"
DEPENDS += "litesdcard-native"

# do not depend on libc or compiler libs, only the compiler is needed
DEPENDS_remove = "virtual/${TARGET_PREFIX}compilerlibs virtual/libc"

# prevent the population of the build-id section into the output
CC += "-Wl,--build-id=none"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "ecpix5"
