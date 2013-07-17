
GSILejos
====================

GSILejos is a library to provide a simulation environment for the Lego Mindstorm
robots and the Lejos library, focused on educational purposes.


Installation & Configuration
----------------------------

First, you will need to install maven3 to run and configure this project.

#### Windows

The simplest way to install maven on windows is to follow the instructions on [its
site](http://maven.apache.org/guides/getting-started/windows-prerequisites.html).
In sort: download the zip, uncompress it, and add the folder where the .bat file
is to the Windows PATH

#### GNU/Linux

Most GNU/Linux distribution include maven in their repositories. However, make sure
you are installing version 3 of maven. In Debian-like systems:

        user:~% sudo apt-get install maven
        
#### MAC OS X

You can follow the instructions at [the maven download site](http://maven.apache.org/download.cgi)

### Dependencies:


Even though we use Maven in an attempt to simplify as much as possible the usage
of this library, there are still some dependencies you need to install manually.

The first thing you need to install is the simbad for robot simulations library.
Download the 1.4 version from [its site](http://simbad.sourceforge.net/) and, in
a terminal, run:

        $> mvn install:install-file -DgroupId=simbad -DartifactId=simbad -Dversion=1.4 -Dpackaging=jar -Dfile=/path/to/jarfile    

Also, the simulator uses java3d to display the behaviour of the robots we are testing.
Since java3d needs OS-dependent libraries to work, you will need to install it
on your system. Download the installer of the latest version from [its web](http://www.oracle.com/technetwork/java/javase/tech/index-jsp-138252.html)

#### Windows: 

Simply run the .exe installer and it should take care of everything.

#### GNU/Linux

Get the .bin installer for your architecture (either i586 or amd64), and, in a
terminal, change to the directory where your jdk is installed. As root, run the
installer, and it should do the trick. For example:
   
        user:~/Downloads$ wget http://download.oracle.com/otn-pub/java/java3d/1.5.1/java3d-1_5_1-linux-amd64.bin
        user:~/Downloads$ cd $JAVA_HOME
        user:/usr/lib/jvm/jdk1.7.0_21$ sudo ~/Downloads/java3d-1_5_1-linux-amd64.bin
   

#### MAC OS X

There is no installer available, so you need to get the .zip file, and uncompress
it in your jdk directory


Further instructions can be found at the [java website](http://download.java.net/media/java3d/builds/release/1.5.1/README-download.html)

### Configuration

At this point, you should not require further configuration.

Contributing
------------

At the moment this document was edited, we are migrating the simulator from lejos
0.8 to 0.9.1. Any help, insight or contributions to the project are welcome and
 appreciated. Please, feel free to contact us any time.


About the Project
-----------------

This project is intended to be used in educational environments as an introduction
to behavior based programming, and a basic subsumption architecture.


Contributors
----------------

* Alberto Mardomingo Mardomingo
* Felipe Echanique Torres


License
-------
GSILejos
Copyright (C) 2010-2013  GSI-UPM

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
