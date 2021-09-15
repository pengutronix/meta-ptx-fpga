FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_configure_prepend() {
    export LITEX_ENV_CC_TRIPLE="$CC"
}

SRC_URI += "\
    file://0001-software-use-no-pie.patch \
"
