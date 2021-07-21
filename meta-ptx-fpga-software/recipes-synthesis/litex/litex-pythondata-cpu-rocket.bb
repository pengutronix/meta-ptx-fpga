SUMMARY = "LiteX - Rocket CPU"
HOMEPAGE = "https://github.com/litex-hub/pythondata-cpu-rocket"
SECTION = "devel/hdl"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/litex-hub/pythondata-cpu-rocket;protocol=https"
SRCREV = "3e30eb61fca87020d898ee335e36281a004e9c79"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
