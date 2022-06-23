Known Issues
============

There are still various open issues in the FPGA Demo.

BIOS reads corrupted data from SD card
--------------------------------------

Affected machine
~~~~~~~~~~~~~~~~

ecpix5-vexriscv

Symptom
~~~~~~~

The OpenSBI suddenly stops executing and hangs.

Description
~~~~~~~~~~~

Reading files from the SD card with the ptxsoc-vexriscv is unreliable and
corrupted data will be put into RAM. This results in the execution of a
corrupted OpenSBI.

The main difference between the bitstream is, that the bitstream in the BSP
uses a litedram controller, while the bitstream from GitHub uses wishbone
memory. Not sure, if this is related to the patches to enable the l2 cache.

Workaround
~~~~~~~~~~

The Linux-on-Litex-VexRiscV bitstream built by the `GitHub action
<https://github.com/strumtrar/linux-on-litex-vexriscv/actions/runs/2357547480>`_
works. Only the bitstream in the BSP is affected by this issue. Use the
prebuilt bitstream for a working system.

SD card not working in Linux
----------------------------

Affected machine
~~~~~~~~~~~~~~~~

ecpix5-vexriscv

Symptom
~~~~~~~

Linux is not able to use the SD card.

Description
~~~~~~~~~~~

The LiteX MMC driver in Linux does not work, yet.

The LiteX MMC driver (LITEX_MMC) is mainline and enabled in the BSP. However,
it does not detect the SD card, since reading the PHY_CHIPDETECT register does
not detect that an SD card is available.

Hacking the value of the register does not work and further commands result in
errors.

Maybe the device tree entry is not fully in line with the driver or the SoC.
See the ``litex/tools/litex_json2dts_linux.py`` script, which generates the
device tree, for the device tree node for litex,mmc.

Workaround
~~~~~~~~~~

Use a CPIO archive from RAM as rootfs, which can be loaded by the LiteX bios
during the initialization.

Multi-Core Support in Linux
---------------------------

Affected machine
~~~~~~~~~~~~~~~~

expic5-vexriscv

Symptom
~~~~~~~

SMP is disabled in Linux. top shows only a single CPU.

Description
~~~~~~~~~~~

The vexriscv should be synthesized with 2 Cores, but only one core shows up in
Linux. top also shows only a single CPU.

It is unknown, if this is intentional or if this is a workaround for some
other issue.

Multi-Core is a core feature of the VexRiscV and should be enabled.

Workaround
~~~~~~~~~~

-
