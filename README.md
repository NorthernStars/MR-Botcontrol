MR-Botcontrol
=============

Mixde-Reality Bot Control Module

INSTALLATION
------------

1. Import project into eclipse.

2. Copy librxtxSerial library of your OS
   from lib folder to root folder.
   
3. Add librxtxSerial to build path.



Linux device permissions
------------------------
If your're using windows, maybe you have no
permissions to use FTDI devices.
So create a new udev rule in /etc/udev/rules.d.

	sudo nano /etc/udev/rules.d/90-ftdichip-permissions.rules
	
And enter the following rule: 

	ATTRS{idVendor}=="0403",ATTRS{idProduct}=="6001",MODE="0666",GROUP="users"
	
After that restart udev

	sudo stop udev
	sudo udev start
