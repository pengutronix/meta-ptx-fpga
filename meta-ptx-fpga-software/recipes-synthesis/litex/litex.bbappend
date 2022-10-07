FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
	file://0001-HACK-enable-wishbone_memory.patch \
"

SRCREV = "2022.04"
SRCPV = "2022.04"
PV = "2022.04+git${SRCPV}"

do_configure:prepend() {
    export LITEX_ENV_CC_TRIPLE="$CC"
}
