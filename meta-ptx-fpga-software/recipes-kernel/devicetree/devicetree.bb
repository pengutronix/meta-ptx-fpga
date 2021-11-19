DESCRIPTION = "device trees"
LICENSE = "GPLv2"
SECTION = "bsp"

inherit devicetree

SRC_URI = "file://litex-vexriscv-ecpix5.dts"

COMPATIBLE_MACHINE = "(ecpix5-vexriscv)"
