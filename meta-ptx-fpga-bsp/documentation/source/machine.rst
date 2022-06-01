Supported Hardware
==================

For using different boards with the same BSP, Yocto has the concept of a
``MACHINE`` that allows describing all board-specific configuration and
software.

Machines supported by this BSP are:

* ecpix5-vexriscv -- ECPIX-5 with Linux on a VexRiscV softcore
* ecpix5-rocket -- ECPIX-5 with Linux on a Rocket Chip softcore

Their configuration can be found in the ``meta-ptx-fgpa-bsp/conf/machine/``
directory.

You can select the appropriate ``MACHINE`` for your build by either setting
this variable in your ``conf/local.conf`` file, or directly through your
execution environment, e.g.::

  MACHINE=ecpix5-vexriscv bitbake <target>

An overview for BSP-related information is the Yocto Project
`Board Support Package (BSP) Developer's Guide <https://docs.yoctoproject.org/2.4/bsp-guide/index.html>`_

ECPIX-5 with PTXSOC VexRiscV
----------------------------

For a more comprehensive description of the hardware see the
`ECPIX-5 documentation <http://docs.lambdaconcept.com/ecpix-5/index.html>`_

General boot process
~~~~~~~~~~~~~~~~~~~~

The board loads the bitstream from the SPI flash. The bitstream contains the
LiteX bios as ROM code. The bios accesses the first partition of the SD card,
reads the ``boot.json`` file and loads the binaries from the partition to the
RAM addresses that are written in the ``boot.json``. Finally execution jumps
to the address of the last entry in the ``boot.json``, which is the OpenSBI.

The OpenSBI further initializes the hardware, and finally jumps to
``0x40000000`` (``FW_JUMP_ADDR`` in the VexRiscV platform support in the
OpenSBI) and passes the device tree at ``0x40EF0000`` (``FW_JUMP_FDT_ADDR``).
The LiteX bios is responsible for putting the binaries at exactly these
locations.

Connectors
~~~~~~~~~~

FIXME

Bootstrapping
~~~~~~~~~~~~~

Copy ``core-image-minimal-ecpix5-vexriscv.wic`` from the BSP or `Jenkins
<https://jenkins.stw.pengutronix.de/job/yocto-bsps/job/YOCTO.BSP-Pengutronix-FPGA/job/kirkstone-ecpix5-vexriscv/lastSuccessfulBuild/>`
onto an SD card::

   cp core-image-minimal-ecpix5-vexriscv.wic /dev/sdX

The following manual step is necessary, as Linux requires the rootfs as a cpio
in the boot partition (:ref:`SD card not working in Linux`) but it is not
possible to automatically add the cpio rootfs archive into the wic image.
Copy the ``core-image-minimal-ecpix5-vexriscv.cpio`` as ``rootfs.cpio`` from
your BSP build or jenkins into the first partition::

   cp core-image-minimal-ecpix5-vexriscv.cpio /media/sdX1/rootfs.cpio

.. note::
   The ``rootfs.cpio`` file name is important. Otherwise, the LiteX bios will
   not find the rootfs. Make sure, the ``rootfs.cpio`` entry exists in the
   ``boot.json`` file in the same partition.

Now put the SD card into the SD card slot of the ECPIX-5 and power up the
board.

If there is a bitstream in the SPI flash, it will by default start to load the
bitstream from the SPI flash.

Flash the bitstream into the SPI flash. The default mechanism to write the
bitstream to the SPI flash is the `openFPGALoader
<https://github.com/trabucayre/openFPGALoader>`_::

        openFPGALoader -b ecpix5 -f ptxsoc.bit

.. warning::
   The ptxsoc.bit in the BSP (and Jenkins)  and will hang in the OpenSBI. Use
   the bitstream from `GitHub
   <https://github.com/strumtrar/linux-on-litex-vexriscv/actions/runs/2357547480>`_
   when preparing a demo.

The openFPGALoader will reset the hardware and restart with the new bitstream
from the flash.

.. note::
   The openFPGALoader is not included in the BSP. Build it yourself on the
   development machine or install it using your package manager.

Partition Layout
~~~~~~~~~~~~~~~~

The SD card contains two partitions.

+----------+--------------------+------+------+
| Name     | Blocks             | Size | Type |
+==========+====================+======+======+
| boot     | FIXME,FIXME        |  20M | vfat |
+----------+--------------------+------+------+
| root     | FIXME,FIXME        |      | ext4 |
+----------+--------------------+------+------+

The boot partition contains the OpenSBI, Linux kernel and device tree, which
are loaded by the LiteX bios.

The root partition contains the Linux rootfs.

.. note::
   The root partition is not used by Linux, yet, since the SD card is not
   working in Linux.

Network Configuration
~~~~~~~~~~~~~~~~~~~~~

FIXME

.. note::
   LiteEth in Linux is not working, yet.

ECPIX-5 with PTXSOC Rocket
--------------------------

FIXME

Development Notes
-----------------

There are a few tricks to simplify the development with the ECPIX-5 and LiteX.
These may help you to be quicker.

.. note::
   Please extend the list with your own development workflows.

OpenOCD
~~~~~~~

OpenOCD may be used as an alternative to the openFPGALoader to write the
bitstream to the ECPIX-5.::

        openocd -f openocd-ecpix5.cfg -c "init" -c "svf -quiet ptxsoc.svf" -c "exit"

The ``openocd-ecpix5.cfg`` file has the following content::

      interface ftdi
      ftdi_vid_pid 0x0403 0x6010
      ftdi_channel 0
      ftdi_layout_init 0xfff8 0xfffb
      reset_config none
      adapter_khz 25000

      jtag newtap ecp5 tap -irlen 8 -expected-id 0x81112043

See the `<http://docs.lambdaconcept.com/ecpix-5/features/debug.html#openocd>`_ for
further details.

Network boot
~~~~~~~~~~~~

The LiteX bios built by the BSP supports loading binaries via TFTP.

Interrupt the boot process with ``Esc`` or ``Q``. Press the button early,
because otherwise the LiteX bios may not pick up the button press.

Run the following commands to configure an IP address and load the
``boot.json`` and other binaries via TFTP::

        eth_local_ip <your-local-ip-address>
        eth_remote_ip <ip-address-of-the-tftp-server>
        netboot <file-name-of-boot.json-on-tftp>

The bios will automatically load the files in the ``boot.json`` via TFTP, too.

Using the USB-SD-Mux
~~~~~~~~~~~~~~~~~~~~

The ECPIX-5 in the remote lab has a USB-SD-Mux to switch the SD card between
the ECPIX-5 and the remote lab server. The USB-SD-Mux can be controlled via
the usual Labgrid commands.

In order to copy single files to the SD card or to modify files, you may use
``pmount`` to mount the SD card partitions.

Modifying the LiteX bios
~~~~~~~~~~~~~~~~~~~~~~~~

Making changes to the LiteX bios has a few surprises, since the source code is
provided by the litex-native recipe, is compiled by the
litex-boards-vexriscv-software recipe, and put into the bitstream by the
ptxsoc-vexriscv recipe.

While it would be ideal to change the source code in litex-native, this causes
a rebuild of the entire bitstream, which will take some time. Therefore, the
ptxsoc-vexriscv recipe allows to update the bios of an existing bitstream.

The ``litex-boards-vexriscv-software`` has the source code of the LiteX bios
in ``recipe-sysroot-native/usr/lib/python3.10/site-packages/litex/soc/software/bios``
in its build directory ``build/tmp/work/riscv32-poky-linux/litex-boards-vexriscv-software/``.

Change the LiteX source there, rebuild the bios and update the bios in the
gateware to build a bitstream with a modified bios::

        bitbake -c compile -f litex-boards-vexriscv-software
        bitbake ptxsoc-vexriscv

OpenSBI
~~~~~~~

The console output of the OpenSBI looks as follows::

        OpenSBI v0.8-2-ga9ce3ad
           ____                    _____ ____ _____
          / __ \                  / ____|  _ \_   _|
         | |  | |_ __   ___ _ __ | (___ | |_) || |
         | |  | | '_ \ / _ \ '_ \ \___ \|  _ < | |
         | |__| | |_) |  __/ | | |____) | |_) || |_
          \____/| .__/ \___|_| |_|_____/|____/_____|
                | |
                |_|

        Platform Name       : LiteX / VexRiscv-SMP
        Platform Features   : timer,mfdeleg
        Platform HART Count : 8
        Boot HART ID        : 0
        Boot HART ISA       : rv32imas
        BOOT HART Features  : time
        BOOT HART PMP Count : 0
        Firmware Base       : 0x40f00000
        Firmware Size       : 120 KB
        Runtime SBI Version : 0.2
