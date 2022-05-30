Using the Yocto BSP
===================

Initial Checkout and Build
--------------------------

To initially checkout the BSP repository, run::

  git clone -b kirkstone --recursive ssh://gitolite@git-customers.pengutronix.de/YOCTO.BSP-Pengutronix-FPGA

.. note::

  You must have ssh access to the Pengutronix git servers in order to do this.
  Please send us a request with your ssh public key if you do not have access,
  yet.

Change to the checked-out BSP directory::

  cd YOCTO.BSP-Pengutronix-FPGA

This contains the base Yocto poky distribution and layers, some additional
open-source meta-layers we require for building our software components and
finally, the ``meta-ptx-fpga/`` directory containing all meta-layers specific to
Pengutronix.


To build the BSP, source the environment setup-script `oe-init-build-env` which
will bootstrap your build directory with some template configuration and change
into it. You can either use the default build directory by giving no additional
parameter or choose a custom one, either as relative or absolute path::

  source oe-init-build-env <path-to-build-dir>

Check your local configuration file in ``conf/local.conf`` to contain the
appropriate settings for your build. You can set the default MACHINE here, for
example, adjust the ``DL_DIR`` where artifacts are downloaded to, set
``PREMIRRORS`` and so on.

Refer to the
`User Configuration
<https://docs.yoctoproject.org/2.4/overview-manual/concepts.html#user-configuration>`_
chapter in the Yocto reference manual for more detailed information on local
configuration.

Then, you can run ``bitbake`` to build the targets you require, e.g.::

  bitbake core-image-minimal

Pengutronix meta-Layers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

All Pengutronix meta layers are located in a single ``meta-ptx-fpga`` Git
repository::

  ssh://gitolite@git-customers.pengutronix.de/meta-ptx-fpga

The purpose of the different meta layers is:

:meta-ptx-fgpa-bsp:
  Pengutronix BSP layer providing hardware support for all boards

:meta-ptx-fpga-software:
  Pengutronix application layer providing recipes for software and libs required,
  also provides some default images

:meta-ptx-fpga-proprietary:
  Pengutronix proprietary layer providing recipes for software and libs that are not
  accessible externally

BSP Update and Additional Build Targets
---------------------------------------

If there are updates for the BSP you intend to use, there is no need to start
from scratch again with git checkout or BSP building!
Simply follow the below-mentioned steps.

Update your BSP git (in git speech the superproject) to the (latest) version you
want to use::

  git remote update
  git merge origin/kirkstone

And in a second step recursively update all the meta-layers' git repositories
(in git speech the submodules) to the commit recorded in the superproject::

  git submodule update --init

Then, simply run the bitbake task you want.
Yocto will auto-detect everything that needs to be rebuilt automatically and
rebuild it while leaving everything that needs no rebuild untouched::

  bitbake core-image-minimal

One exception is when the newly merged changes affect the set of active layers.
If, after a merge, you encounter build errors due to missing dependencies, you
can compare ``meta-ptx-fpga/meta-ptx-fpga-software/conf/bblayers.conf.sample`` with the
currently used ``<path-to-build-dir>/conf/bblayers.conf``.
If the layers did change, you can generate a new ``bblayers.conf``::

  rm <path-to-build-dir>/conf/bblayers.conf
  source oe-init-build-env <path-to-build-dir>


SDK Build
---------

To build a base SDK, run::

  bitbake core-image-minimal -c populate_sdk

The resulting artifact can then be found in your build folder at::

  tmp/deploy/sdk/FIXME.sh

To actually install it, copy it to your development system and run::

  sh FIXME.sh

This will ask for the path to install the SDK into and to confirm the
installation with 'Y'. Once you've done that, the SDK will extract itself to
this location while prompting progress and usage info as follows::

  FIXME: place output of sh <sdk-name.sh> here!

Now you're done and can use the BSP to build against it by sourcing the
environment setup script as noted above.

.. note:: For more information about building and using SDK features Yocto
  provides, refer to
  https://docs.yoctoproject.org/2.4/sdk-manual/index.html

