do_compile() {
    mkdir -p ${S}/build
    ln -sf lambdaconcept_ecpix5 ${S}/build/ecpix5

    ${S}/make.py --board ecpix5 --local-ip 192.168.8.79 --remote-ip 192.168.8.13 --build
}

do_deploy () {
    install -Dm 0644 ${B}/build/ecpix5/gateware/lambdaconcept_ecpix5.bit ${DEPLOYDIR}/top.bit
    install -Dm 0644 ${B}/build/ecpix5/gateware/lambdaconcept_ecpix5.svf ${DEPLOYDIR}/top.svf
    install -Dm 0655 ${B}/images/rv32.dtb ${DEPLOYDIR}/rv32.dtb

    # rewrite paths in boot.json for use from deploy dir
    cp ${S}/images/boot.json ${B}/boot-${MACHINE}.json
    sed -i 's/opensbi.bin/fw_jump.bin/' ${B}/boot-${MACHINE}.json
    sed -i "s/rootfs.cpio/core-image-minimal-${MACHINE}.cpio/" ${B}/boot-${MACHINE}.json
    install -Dm 0644 ${B}/boot-${MACHINE}.json ${DEPLOYDIR}/boot.json
}
COMPATIBLE_MACHINE = "ecpix5"
do_load() {
    ${S}/make.py --board ecpix5 --load
}

