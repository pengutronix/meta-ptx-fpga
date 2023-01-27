SUMMARY = "Helper script to set LEDs for FPGA demo"
LICENSE = "0BSD"
LIC_FILES_CHKSUM = "file://set_led;beginline=1;endline=13;md5=a357fc47cae81bc4dec6fc11352b2882"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://set_led"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}

    install -m 0755 ${WORKDIR}/set_led ${D}/${bindir}
}
