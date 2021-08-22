FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_configure_prepend() {
    export LITEX_ENV_CC_TRIPLE="$CC"
}

SRC_URI += "\
    file://0001-soc-software-bios.elf-link-statically.patch \
    file://0002-soc-software-Fix-objcopy-with-build-id-builds.patch \
    file://0003-software-use-no-pie.patch \
"
