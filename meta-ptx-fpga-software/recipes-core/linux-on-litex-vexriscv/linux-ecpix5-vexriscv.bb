SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel

# disable kernel-base depending on image, other mechanisms are used to ship the kernel
RDEPENDS_${KERNEL_PACKAGE_NAME}-base = ""

DEFAULT_PREFERENCE = "-1"
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE_ecpix5 = ".*"

S = "${WORKDIR}/git"

BRANCH = "litex-rebase"
SRCREV = "060dc05af90823c3b5b2eea7423c48fb4e8c2bf6"
PV = "5.12+${SRCPV}"
SRC_URI = " \
    git://github.com/litex-hub/linux.git;protocol=https;branch=${BRANCH} \
    file://defconfig \
"
