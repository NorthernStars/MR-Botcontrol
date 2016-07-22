# MR-Botcontrol

MR-Botcontrol is a Java-based software for controling the Mixed-Reality robots mrShark via serial transmitter. It requires a serial port (FTDI232 chip based USB-serial converter was tested successfully). It includes a graphical user interface (gui) for connecting to the serial transmitter and controlling the robots. In background a server is listining for incoming control data from Mixed-Reality server. It's also possible to start MR-Botcontrol with autoconnect feature and without gui (see section Command-line arguments).

## INSTALLATION

1. Import project into eclipse.

2. Import jFTDIserial from https://github.com/hanneseilers/jFTDIserial



### Linux serial device permissions
If your're using linux, maybe you have no
permissions to use FTDI devices.
So create a new udev rule in /etc/udev/rules.d.

	sudo nano /etc/udev/rules.d/90-ftdichip-permissions.rules
	
And enter the following rule: 

	ATTRS{idVendor}=="0403",ATTRS{idProduct}=="6001",MODE="0666",GROUP="users"
	
After that restart udev

	sudo stop udev
	sudo udev start
	
## Command-line arguments
MR-Botcontrol currently supports the following command-line arguments

     -q			Quiet start (without GUI and autoconnect enabled)
     -d	<DEVICE>	Sets serial <DEVICE> (on Linux something like /dev/ttyUSB0) to connect to. Requires -a to work.
     -a			Autoconnect feature on. Software autoconnects to device set with -d or to first serial device found.
     -p <PORT>		Port number to use for server socket for incoming control data from MR game server. (default: 9080)
