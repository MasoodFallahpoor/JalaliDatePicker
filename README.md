JalaliDatePicker [![Build Status](https://travis-ci.org/MasoodFallahpoor/JalaliDatePicker.svg?branch=master)](https://travis-ci.org/MasoodFallahpoor/JalaliDatePicker) [![Release](https://jitpack.io/v/masoodfallahpoor/JalaliDatePicker.svg)](https://jitpack.io/#masoodfallahpoor/JalaliDatePicker)
=========
JalaliDatePicker is a Java swing component that provides an easy way for selecting a Jalali (a.k.a. Shamsi) date.

![Screenshot of JalaliDatePicker](screenshots/screenshot1.PNG?raw=true "Screenshot of JalaliDatePicker")

Dependency
----------
Add the URL of Jitpack repository in your root level build.gradle at the end of repositories.
```groovy
allprojects {
    repositories {
        // other repositories go here
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your module level build.gradle file.
```groovy
dependencies {
    compile 'com.github.masoodfallahpoor.JalaliDatePicker:lib:v0.2'
}
```

Alternatively you can download JalaliDatePicker.jar from release tab and add it to your project.

The [sample](https://github.com/MasoodFallahpoor/JalaliDatePicker/blob/master/sample/src/main/java/ir/fallahpoor/sample/Main.java)
demonstrates some common methods of JalaliDatePicker.

License
--------
Copyright (C) 2017 masood@fallahpoor.ir

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.