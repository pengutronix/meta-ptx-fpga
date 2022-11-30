# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# Unable to find any files that looked like license statements. Check the accompanying
# documentation and source headers and set LICENSE and LIC_FILES_CHKSUM accordingly.
#
# NOTE: LICENSE is being set to "CLOSED" to allow you to at least start building - if
# this is not accurate with respect to the licensing of the software being built (it
# will not be in most cases) you must specify the correct value before using this
# recipe for anything other than initial testing/development!
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

# No information for SRC_URI yet (only an external source tree was specified)
SRC_URI = "file://core.py \
	   file://ws2812.v \
           "

PACKAGES = "${PN}"

inherit python3-dir
inherit deploy

S = "${WORKDIR}"

do_install () {
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/ws2812/
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/ws2812/verilog
    install -m 0755 ${S}/ws2812.v ${D}/${PYTHON_SITEPACKAGES_DIR}/ws2812/verilog/ws2812.v
    install -m 0755 ${S}/core.py ${D}/${PYTHON_SITEPACKAGES_DIR}/ws2812/core.py
}
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}"

BBCLASSEXTEND = "native nativesdk"
