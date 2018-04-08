# Yildiz-Engine module-window-swt-win64.

This is the official repository of the swt win64 Module, part of the Yildiz-Engine project.
The swt module is an implementation of the window-module, based on SWT library for windows 64.

## Features

* SWT implementation for windows 64.
* ...

## Requirements

To build this module, you will need the latest JDK, and Maven 3.

## Coding Style and other information

Project website:
http://www.yildiz-games.be

Issue tracker:
https://yildiz.atlassian.net

Wiki:
https://yildiz.atlassian.net/wiki

Quality report:
https://sonarqube.com/overview?id=be.yildiz-games:module-window-swt-win64

## License

All source code files are licensed under the permissive MIT license
(http://opensource.org/licenses/MIT) unless marked differently in a particular folder/file.

## Build instructions

Go to your root directory, where you POM file is located.

Then invoke maven

	mvn clean install

This will compile the source code, then run the unit tests, and finally build a jar file and DLL/SO for the environment you chose.

## Usage

In your maven project, add the dependency

```xml
<dependency>
    <groupId>be.yildiz-games</groupId>
    <artifactId>module-window-swt-win64</artifactId>
    <version>LATEST</version>
</dependency>
```

## Contact
Owner of this repository: Grégory Van den Borre
