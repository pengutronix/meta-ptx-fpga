SRC_URI = "git://github.com/strumtrar/litex;protocol=https"
SRCREV = "a96ca4706cc13243e76e77775195283c5e88dbdb"

do_configure_prepend() {
    export LITEX_ENV_CC_TRIPLE="$CC"
}
