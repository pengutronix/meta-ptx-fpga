SUMMARY = "RISC-V Proxy Kernel and Boot Loader"
HOMEPAGE = "https://github.com/riscv/riscv-pk"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=80a1dc6f26248c4bf4c61c5de5330918"

inherit autotools deploy

SRCREV = "12edfed73d2f601f7a23fded0d40a68cee958a2c"
SRC_URI = "git://github.com/riscv/riscv-pk.git;branch=master \
          "

S = "${WORKDIR}/git"

DEPENDS += "dtc-native"

DEPENDS += "virtual/kernel"
DEPENDS += "linux-on-litex-rocket"

# Required because vmlinux is the workload
do_compile[depends] += "linux-ecpix5:do_deploy"

EXTRA_OECONF = "\
	--host=riscv64-unknown-linux-gnu \
	--with-arch=rv64imac \
	--with-dts=${DEPLOY_DIR}/images/ecpix5-rocket/ecpix5.dts \
	--with-payload=${DEPLOY_DIR}/images/ecpix5-rocket/vmlinux-initramfs-ecpix5-rocket.bin \
	--enable-logo \
"

autotools_do_configure() {
	# autoreconf results in a broken configure script
	oe_runconf
}

autotools_do_compile() {
	oe_runmake bbl.bin
}

do_install() {
}

do_deploy () {
	install -m 644 ${B}/bbl.bin ${DEPLOYDIR}/
}

addtask deploy before do_build after do_install

COMPATIBLE_HOST = "riscv64.*"
INHIBIT_PACKAGE_STRIP = "1"

SECURITY_CFLAGS = ""
