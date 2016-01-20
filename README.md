# Lecture: VI51 - Virtual Life Simulation

This lecture aims to provide the bakcground for creating agent-oriented simulators for the modeling and
the simulation of mobility behavior in virtual environments.

This repository contains the source code that is used by the students during the lab works associated to the lecture.

Author: Stéphane Galland.
License: Apache License 2

## Content of this repository
 
* `src/main/java/fr/utbm/info/vi51/framework` contains a very simple simulation framework (following the guidelines given during the seminar sessions) based on the [SARL agent-oriented language](http://www.sarl.io) and the [Janus platform](http://www.janusproject.io).
* `src/main/java/fr/utbm/info/vi51/general` contains the different general libraries that are coded by the students during the lab works.
* `src/main/java/fr/utbm/info/vi51/labworkX` contains the complete source code for a specific lab work.

## Installation

* Download the [version 2.0.3.0](http://www.janusproject.io/#download) (or higher) of the Janus platform.
* Download the [Eclipse product 0.3.0](http://www.sarl.io/download/) (or higher) for the SARL agent programming language.
* CAUTION: The digits 2 and 3 of the Janus platform version must be the same as the digits 1 and 2 of the Eclipse product, here "0.3".
* Launch the SARL Eclipse product.
* Create a SARL project.
* In the buildpath of the project: remove the "SARL libraries".
* In the buildpath of the project: add the external Jar of the Janus platform.
* Copy the source code from this repository in the project.

## Launching

* Create a launch configuration for a Java application.
* Select the main function in the `MainProgram` class of one of the lab works.
