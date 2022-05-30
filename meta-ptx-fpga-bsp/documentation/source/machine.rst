Supported Hardware
==================

For using different boards with the same BSP, Yocto has the concept of a
``MACHINE`` that allows describing all board-specific configuration and
software.

Machines supported by this BSP are:

* FIXME -- Description
* FIXME -- Description

Their configuration can be found in the ``meta-ptx-fgpa-bsp/conf/machine/``
directory.

You can select the appropriate ``MACHINE`` for your build by either setting
this variable in your ``conf/local.conf`` file, or directly through your
execution environment, e.g.::

  MACHINE=FIXME bitbake <target>

An overview for BSP-related information is the Yocto Project
`Board Support Package (BSP) Developer's Guide <https://docs.yoctoproject.org/2.4/bsp-guide/index.html>`_

FIXME
-------

FIXME: Put Board description here!

Bootstrapping
~~~~~~~~~~~~~

FIXME

Partition Layout
~~~~~~~~~~~~~~~~

FIXME

+----------+--------------------+------+------+
| Name     | Blocks             | Size | Type |
+==========+====================+======+======+
| rootfs 0 | FIXME,FIXME        |  10G | ext4 |
+----------+--------------------+------+------+
| appfs 0  | FIXME,FIXME        |   5G | ext4 |
+----------+--------------------+------+------+

Network Configuration
~~~~~~~~~~~~~~~~~~~~~

FIXME
