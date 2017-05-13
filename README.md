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
    compile 'com.github.masoodfallahpoor.JalaliDatePicker:lib:v0.4'
}
```

Alternatively you can download JalaliDatePicker.jar from release tab and add it to your project.

The [sample](https://github.com/MasoodFallahpoor/JalaliDatePicker/blob/master/sample/src/main/java/ir/fallahpoor/sample/Main.java)
demonstrates some common methods of JalaliDatePicker.

License
--------
Copyright 2017 Masood Fallahpoor

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.

You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations
under the License.