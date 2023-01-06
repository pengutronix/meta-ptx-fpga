SUMMARY = "Helper script to put the system under stress"
LICENSE = "0BSD"
LIC_FILES_CHKSUM = "file://stress_demo;beginline=1;endline=10;md5=01b12d84848c75bf87ca54974c5df38d"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://stress_demo"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}

    install -m 0755 ${WORKDIR}/stress_demo ${D}/${bindir}
}
