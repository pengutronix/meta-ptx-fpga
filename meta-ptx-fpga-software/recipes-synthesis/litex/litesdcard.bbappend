FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

LIC_FILES_CHKSUM = "file://LICENSE;md5=84fb2139cffab4ac444f26bfd284a2bb"

SRCREV = "2021.08"
PV = "2021.08"

SRC_URI += " \
    file://0001-litesdcard-frontend-add-missing-__init__.py.patch \
    file://0002-Invert-output-clock-to-satisfy-setup-hold.times.patch \
"
