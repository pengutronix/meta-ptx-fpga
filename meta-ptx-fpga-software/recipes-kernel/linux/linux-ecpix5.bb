SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel

DEFAULT_PREFERENCE = "-1"
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE = "ecpix5.*"

S = "${WORKDIR}/linux-${LINUX_VERSION}"

LINUX_VERSION = "5.16-rc1"

PV = "${LINUX_VERSION}"

SRC_URI = "https://git.kernel.org/torvalds/t/linux-${LINUX_VERSION}.tar.gz"
SRC_URI += "file://defconfig"

SRC_URI[sha256sum] = "f15cb8ed94671ac2e094380b429bafdab04f44bc3f29cca0b050ee2370a21d2c"

require files/patches/series.inc
