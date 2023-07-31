FitStir
=================================================

FitStir is a fitness application that focuses on the user's *mental health* as well as their physical health. Included in the application are sections every fitness application has (workout logging, calorie tracking, goal keeping) as well as additional features, such as a "mental wellness" calculator.

Table of contents
-----------------

* [Introduction](#introduction)
* [Features](#features)
* [Technologies](#technologies)
* [Installation](#installation)
* [Developmental Setup](#developmental-setup)
* [Known issues and limitations](#known-issues-and-limitations)
* [License](#license)
* [Contributors](#contributors)
* [Project Status](#project-status)
* [Acknowledgments](#acknowledgments)


Introduction
------------

FitStir is an android application designed to help a user utilize healthy eating and workouts to better improve their mental health. 

With the focus on the user's mental health, which differs from other fitness applications, the user can actively see how much exercise and a proper diet can positively affect them mentally. 

Features
--------

There are a variety of features available:
* The ability to create an account/log in to an existing account.
  * (insert picture)
* Various workout tracking options, separated by type of workout:
  * Running: Keeps track of any running workouts data
  * Upper Body: Keeps track of any upper body workouts data
  * Lower Body: Keeps track of any lower body workouts data
  * Weight Lifting: Keeps track of any weight lifting workouts data
  * Circuit Workouts: Keeps track of any circuit workouts data
  * Yoga: Keeps track of any yoga workouts data
  * (insert picture)
* Various health tracking options, separated by category:
  * Calorie Tracking: Keeps track of caloric intake via meals
  * Food Guide: Provides a recommended food guide based on chosen goals
  * Weight Loss: Keeps track of any weight data
  * Recipes: Provides recipes for the user to utilize
  * Finding a Dietitian: Provides a map already filtered to dietitians new the user
  * Logging a Diary: Provides a space where the user can keep track of how they are feeling and any daily/weekly goals they might have
  * (insert picture)
* A sections for adding, viewing, and editting goals for the various categories mentioned above. Each goal provides a graph using quantitative data from logged values (whether from the workouts, or from the health sections).
  * (insert picture)
* A section for users to find videos regarding exercise techniques, recipes, or fitness in general.
  * (insert picture)

Technologies
------------

The current list of known libraries used:
* jrvansuita's PickImage library used for choosing a photo to use for the user's avatar in the profile section
* jjoe64's GraphView library for showing the graphs for each goal

The current list of APIs used:
* Edamam's Food Database for querying food data (food item, calories, portion size, etc.)
 
Installation
------------

Requirements:
* Android 33

Instructions: 

Eventually, the application will be released on the Google Play store for easy installation. 
Once everything has been downloaded, the user is walked through how to set up an account.

Developmental Setup
-------------------

In order for a developer to access the application:
* Save the repository to your device or link a new project from your IDE directly to the repository using [https://github.com/shastye/FitStir.git](https://github.com/shastye/FitStir.git)
* Load up your IDE, if you haven't already
* Using an emulator, make sure the emulated device is Android 33 and is set up in a default portrait orientation
* Using an actual device connected to your device manager, go into the app/gradle.build file and change "minSDK=33" to "minSDK=##" where ## is whatever your devices android API is
  * This should allow the application to run on your device, and as of 7/24/2023, all features will work as they should
* From here, you should be able to build and run the application as normal

Known issues and limitations
----------------------------

Currently known issues include:
* User data is currently static and only applicable in the current run of the application (it doesn't save to the database yet)
* The setting and profile sections don't display or allow editting of *all* the user's data yet

Currently known limitations:
* Not all sections are functional

License
-------

MIT License

Copyright  2023 FitStir

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (FitStir), to deal in FitStir without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of FitStir, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


Contributors
------------

Team Members:
* Arrington Perry
* Sierra Clubb

Project Status
--------------

FitStir is currently still in production. About a third of the features are currently created.

Acknowledgments
---------------
* [http://tom.preston-werner.com/2010/08/23/readme-driven-development.html](https://github.com/mhucka/readmine/blob/main/README.md)
* ['com.github.jrvansuita:PickImage:+'](https://github.com/jrvansuita/PickImage)
* ['com.jjoe64:graphview:4.2.2'](https://github.com/jjoe64/GraphView/wiki#showcase-1)
* [Edamam Food Database API](https://developer.edamam.com/food-database-api-docs#/)
* ['com.google.code.gson:gson:2.10.1'](https://github.com/google/gson)https://github.com/google/gson)
