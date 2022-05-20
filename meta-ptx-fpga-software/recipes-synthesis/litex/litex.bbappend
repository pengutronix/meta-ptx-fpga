FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRCREV = "2022.04"
SRCPV = "2022.04"

do_configure:prepend() {
    export LITEX_ENV_CC_TRIPLE="$CC"
}
