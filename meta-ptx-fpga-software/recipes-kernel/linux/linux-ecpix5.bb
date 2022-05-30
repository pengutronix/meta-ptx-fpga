SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel

# disable kernel-base depending on image, other mechanisms are used to ship the kernel
RDEPENDS:${KERNEL_PACKAGE_NAME}-base = ""

DEFAULT_PREFERENCE = "-1"
COMPATIBLE_MACHINE = "ecpix5.*"

S = "${WORKDIR}/linux-${LINUX_VERSION}"

LINUX_VERSION = "5.18-rc1"

PV = "${LINUX_VERSION}"

SRC_URI = "https://git.kernel.org/torvalds/t/linux-${LINUX_VERSION}.tar.gz"
SRC_URI += "file://defconfig"

SRC_URI[sha256sum] = "a7ae23d354937723b3ee65513c2707c02541a0553ae9a7d5c7136525335d4423"
