LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

# No information for SRC_URI yet (only an external source tree was specified)
SRC_URI = "file://core.py \
	   file://encoder.v \
           "

PACKAGES = "${PN}"

inherit python3-dir
inherit deploy

S = "${WORKDIR}"

do_install () {
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/rotary_encoder/
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/rotary_encoder/verilog
    install -m 0755 ${S}/encoder.v ${D}/${PYTHON_SITEPACKAGES_DIR}/rotary_encoder/verilog/encoder.v
    install -m 0755 ${S}/core.py ${D}/${PYTHON_SITEPACKAGES_DIR}/rotary_encoder/core.py
}
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}"

BBCLASSEXTEND = "native nativesdk"
