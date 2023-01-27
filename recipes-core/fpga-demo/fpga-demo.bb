SUMMARY = "Startup script for the FPGA Demo"
LICENSE = "0BSD"
LIC_FILES_CHKSUM = "file://init;beginline=5;endline=15;md5=95d2685256abad0079f724360d7a58a1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://init"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${INIT_D_DIR}

    install -m 0755 ${WORKDIR}/init ${D}/${INIT_D_DIR}/fpga-demo
}

inherit update-rc.d

INITSCRIPT_NAME = "fpga-demo"
INITSCRIPT_PARAMS = "start 9 5 2 . stop 20 0 1 6 ."

FILES:${PN} += "\
    ${INIT_D_DIR}/fpga-demo \
"
