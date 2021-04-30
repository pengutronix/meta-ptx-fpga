SUMMARY = "LiteX - Rocket CPU"
HOMEPAGE = "https://github.com/litex-hub/pythondata-cpu-rocket"
SECTION = "devel/hdl"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/strumtrar/pythondata-cpu-rocket.git;protocol=https"
SRCREV = "897c6606c0d8a0e8b6c30c47a5b1cc74b204d4ec"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
