MR-Botcontrol
=============

Mixed-Reality Bot Control Module


DEPENDENCIES
------------
All dependencies are already includes into projects.


INSTALLATION
------------

1. Import project into eclipse.



Linux device permissions
------------------------
If your're using linux, maybe you have no
permissions to use FTDI devices.
So create a new udev rule in /etc/udev/rules.d.

	sudo nano /etc/udev/rules.d/90-ftdichip-permissions.rules
	
And enter the following rule: 

	ATTRS{idVendor}=="0403",ATTRS{idProduct}=="6001",MODE="0666",GROUP="users"
	
After that restart udev

	sudo stop udev
	sudo udev start
