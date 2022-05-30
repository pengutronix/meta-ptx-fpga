Update Concept
==============

This Yocto BSP supports updating your devices using the
`RAUC <https://rauc.io>`_
update framework.

The generated rootfs contains all required configuration to run the RAUC
service on the target where it can be controlled either from a command line
tool ``rauc`` or by a third-party program that interacts with it using
``D-Bus``.

The BSP also produces ready-to-install update artifacts, so called 'bundles' that
contain everything to bring the board to a new well-defined software state.
RAUC bundles are cryptographically signed using X509 public-key infrastructure
from OpenSSL.

The BSP contains a basic pair of self-signed certificate and private key as
well as an appropriate keyring for certificate validation on your target.
To make use of the security features of RAUC it is highly recommended to
replace the default certificates with some of your PKI.
Find more about this in the
`Security <http://rauc.readthedocs.io/en/latest/advanced.html#security>`_
chapter of the RAUC documentation.

Using RAUC
----------

RAUC runs in service mode as systemd service on your board.
To inspect its status you can type::

  $ systemctl status rauc

To see the log, run::

  $ journalctl -u rauc

RAUC also provides a command-line tool that allows testing and inspecting the
system the system. To get the status of your system, type::

  $ rauc status

This will list the configured slots, the system compatible string, the
boot selection status,  etc.

Building Bundles
----------------

Run::

  bitbake FIXME

You can inspect the bundle by using the RAUC host tool build by the BSP::

  tmp/deploy/tools/rauc info --keyring=<path-to-keyring> tmp/deploy/images/<machine>/bundle-file.raucb

Installing Bundles
------------------

To install a bundle on the target, you have to move it there first.

For this, either copy it over ssh to the targets tmpfs under ``/run/`` (e.g. by
using scp) or put the bundle on a USB stick, plug it into the system and mount
it.

Then you can use the RAUC command line tool to actually instal it::

  rauc install /path/to/update-bundle.raucb

The tool will report the installation progress and finish with successfully
returning 0 exit code.

Type::

  reboot

to let the device boot into the freshly installed system.

FIXME: HW-NAME
--------------

General Redundancy Setup
~~~~~~~~~~~~~~~~~~~~~~~~

FIXME: Describe basic storage layout / usage. Example:

:Root FS A:	This is the first root file system partition holding all components of the system.
:Root FS B:	This is the second root file system partition holding all components of the system.
:Data FS:	The largest partition that holds the data from the application etc.
                It will not be populated by the RAUC updater.

Boot Handling
~~~~~~~~~~~~~

This describes the general boot setup and boot chain.

FIXME: Describe bootloader handling etc.

FIXME: Use link to relevant RAUC documentation chapter:

https://rauc.readthedocs.io/en/latest/integration.html#barebox
https://rauc.readthedocs.io/en/latest/integration.html#id4 (U-Boot)
https://rauc.readthedocs.io/en/latest/integration.html#grub
https://rauc.readthedocs.io/en/latest/integration.html#efi

Watchdog and Fallback Handling
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

RAUC Configuration
~~~~~~~~~~~~~~~~~~

.. code-block:: cfg

  FIXME: dump system.conf here!

Example status output:

.. code-block:: console

  FIXME: place output of `rauc status` here!

To build an update Bundle for the FIXME, run::

  bitbake FIXME

This will place a new bundle in path 
``<build-dir>/tmp/deploy/images/<MACHINE>/<bundle>.raucb``

