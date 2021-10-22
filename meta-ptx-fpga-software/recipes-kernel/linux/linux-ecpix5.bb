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

# FIXME The litex-rebase branch is rebased all the time causing problems with
# the sha1 and reproducibility. Maybe we should put the patches into the BSP
# and apply them to some known base?
BRANCH = "litex-rebase"
SRCREV = "9c9c0195b4a41262c86869a2bd6e333b15356ca8"
PV = "5.12+${SRCPV}"
SRC_URI = " \
    git://github.com/litex-hub/linux.git;protocol=https;branch=${BRANCH} \
    file://defconfig \
"
