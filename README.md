# Lecture: IA51 - Artificial Intelligence for Games and Virtual Environments

This lecture aims to provide the bakcground for creating agent-oriented simulators for the modeling and
the simulation of mobility behavior in games or virtual environments.
This lecture was known as VI51, Virtual Life for Games and Serious Games.

This repository contains the source code that is used by the students during the lab works associated to the lecture.

Author: Stéphane Galland.
License: Apache License 2

## Content of this repository
 
* `src/main/java/fr/utbm/info/ia51/framework` contains a very simple simulation framework (following the guidelines given during the seminar sessions) based on the [SARL agent-oriented language](http://www.sarl.io).
* `src/main/java/fr/utbm/info/ia51/general` contains the different general libraries that are coded by the students during the lab works.
* `src/main/java/fr/utbm/info/ia51/labworkX` contains the complete source code for a specific lab work.

## Installation

* Download the [SARL Eclipse product 0.13.0](http://www.sarl.io/download/) (or higher) for the SARL agent programming language.
* Launch the SARL Eclipse product.
* Create a SARL project.
* Copy the source code from this repository in the project.

## Launching

* Create a launch configuration for a Java application.
* Select the main function in the `MainProgram` class of one of the lab works.
