SUMMARY = "LiteX - Rocket CPU"
HOMEPAGE = "https://github.com/litex-hub/pythondata-cpu-rocket"
SECTION = "devel/hdl"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/litex-hub/pythondata-cpu-rocket.git;protocol=https"
SRCREV = "0449357e6b12d2614b3d1af0c384880016a880ad"
PV = "2020.08+git${SRCPV}"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
