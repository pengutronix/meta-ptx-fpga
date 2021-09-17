FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRCREV = "2021.08"
PV = "2021.08"

do_configure_prepend() {
    export LITEX_ENV_CC_TRIPLE="$CC"
}

SRC_URI += "\
    file://0001-software-use-no-pie.patch \
"
