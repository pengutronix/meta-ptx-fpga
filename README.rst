The meta-ptx-fpga layer provides support for LiteX-based SoCs on Lattice ECP5 FPGAs.

Adding the ptx-fpga layer to your build
=======================================

In order to use this layer, you need to make the build system aware of it.

Assuming the ptx-fpga layer exists at the top-level of your
yocto build tree, you can add it to the build system by adding the
location of the ptx-fpga layer to bblayers.conf, along with any
other layers needed. e.g.::

  BBLAYERS ?= " \
    /path/to/yocto/meta \
    /path/to/yocto/meta-poky \
    /path/to/yocto/meta-openembedded/meta-oe \
    /path/to/yocto/meta-openembedded/meta-python \
    /path/to/yocto/meta-hdl \
    /path/to/yocto/meta-ptx-fpga \
    "

Dependencies
============

This layer depends on::

   URI: git@github.com:nathanrossi/meta-hdl
   branch: master

   URI: https://github.com/openembedded/openembedded-core
   layers: meta

   URI: https://github.com/openembedded/meta-openembedded
   layers: meta-oe, meta-python
   branch: kirkstone
