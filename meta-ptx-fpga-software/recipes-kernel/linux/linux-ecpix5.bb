inherit kernel

SECTION = "kernel"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PR = "r0"
PV = "5.18.1"

SRC_URI = " \
    https://www.kernel.org/pub/linux/kernel/v5.x/linux-${PV}.tar.xz \
    file://defconfig \
"

S = "${WORKDIR}/linux-${PV}"

SRC_URI[sha256sum] = "83d14126c660186a7a1774a4a5c29d38e170fa5e52cfd2d08fd344dcf1f57d22"

DEFAULT_PREFERENCE = "-1"

COMPATIBLE_MACHINE = "ecpix5.*"

RDEPENDS:${KERNEL_PACKAGE_NAME}-base = ""
