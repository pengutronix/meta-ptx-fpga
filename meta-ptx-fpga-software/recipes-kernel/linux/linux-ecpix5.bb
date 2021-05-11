SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel

# disable kernel-base depending on image, other mechanisms are used to ship the kernel
RDEPENDS_${KERNEL_PACKAGE_NAME}-base = ""

DEFAULT_PREFERENCE = "-1"
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE = "ecpix5.*"

S = "${WORKDIR}/git"

BRANCH = "litex-rebase"
SRCREV = "c8543a1ea22442c113a174501f102854770df2a3"
PV = "5.12+${SRCPV}"
SRC_URI = " \
    git://github.com/litex-hub/linux.git;protocol=https;branch=${BRANCH} \
    file://defconfig \
"
