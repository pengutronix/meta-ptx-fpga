FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0ce55a94e98cbec2110586bb5264bd9a"

SRC_URI += "\
	file://0001-HACK-enable-wishbone_memory.patch \
"

SRCREV = "2022.12"
SRCPV = "2022.12"
PV = "2022.12+git${SRCPV}"

do_configure:prepend() {
    export LITEX_ENV_CC_TRIPLE="$CC"

    # HACK: handle gcc 11+ with zicsr separation
    sed -i 's/\(-march=[^ ]*\)/\1_zicsr_zifencei/g' ${S}/litex/soc/cores/cpu/rocket/core.py
}
