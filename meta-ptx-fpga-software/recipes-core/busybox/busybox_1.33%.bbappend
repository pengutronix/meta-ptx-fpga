# Custom busybox configuration
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_riscv64 = " file://busybox-rv64gc.config"
