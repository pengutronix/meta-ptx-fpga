SUMMARY = "Helper script to set LEDs for FPGA demo"
LICENSE = "0BSD"
LIC_FILES_CHKSUM = "file://set_led;beginline=1;endline=13;md5=18651c67a41fb88fa19bcf885b20737c"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://set_led"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}

    install -m 0755 ${WORKDIR}/set_led ${D}/${bindir}
}
