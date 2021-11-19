# umpf-base: v5.16-rc1
# umpf-name: 5.16/customers/ptx/litex
# umpf-version: 5.16/customers/ptx/litex/20211119-2
# umpf-topic: v5.16/topic/litex
# umpf-hashinfo: 95a9659da5008ee0a2b8680f9a091f19199b0ee1
# umpf-topic-range: fa55b7dcdc43c1aa1ba12bca9d2dd4318c2a0dbf..5758b8d99ab71da0308940491786bd318d4fb8a6
SRC_URI += "\
  file://patches/0001-LiteX-LiteEth-add-polling-support.patch \
  file://patches/0002-LiteX-driver-for-LiteSPI.patch \
  file://patches/0003-LiteX-driver-for-LiteSDCard-litex_mmc.patch \
  file://patches/0004-LiteX-add-defconfig-files-for-linux-on-litex-rocket-.patch \
  file://patches/0005-litex.h-FIXME-restore-variable-length-accessors.patch \
  file://patches/0006-LiteX-driver-for-LiteGPIO-interface.patch \
  file://patches/0007-LiteX-driver-for-I2CMaster.patch \
  file://patches/0008-LiteX-driver-for-XADC-hwmon.patch \
  file://patches/0009-LiteX-driver-for-PWM.patch \
  file://patches/0010-pwm-litex-Don-t-check-the-return-code-of-pwmchip_rem.patch \
  file://patches/0011-LiteX-driver-for-SPI-Flash-mtd-device.patch \
  file://patches/0012-LiteX-driver-for-ICAPBitstream-fpga-manager.patch \
  file://patches/0013-LiteX-support-for-VexRiscV-interrupt-controller.patch \
  file://patches/0014-LiteX-driver-for-LiteVideo.patch \
  file://patches/0015-LiteX-driver-for-MMCM.patch \
  file://patches/0016-openrisc-add-dts-for-ecpix5.patch \
  "
# umpf-release: 5.16/customers/ptx/litex/20211119-2
# umpf-topic-range: 5758b8d99ab71da0308940491786bd318d4fb8a6..3045cd54134f7643d2d73ff601b4c3d94d864360
SRC_URI += "\
  file://patches/0101-Release-5.16-customers-ptx-litex-20211119-2.patch \
  "
# umpf-end
