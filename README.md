
# Rapid start development and test
with Android Studio, Gradle, Espresso, Robolectric, AndroidAnnotations, JaCoCo

[![Build Status](https://travis-ci.org/nenick/AndroidAppDevelopment.svg)](https://travis-ci.org/nenick/AndroidAppDevelopment) **& UnitTest** [![Coverage Status](https://coveralls.io/repos/nenick/AndroidAppDevelopment/badge.png?branch=master)](https://coveralls.io/r/nenick/AndroidAppDevelopment?branch=master)

***Wishes, improvements and discussions about the stuff here are welcome***

See sub modules README.MD for more details
* [App](https://github.com/nenick/AndroidAppDevelopment/tree/master/App)

[see also wiki for more help](https://github.com/nenick/AndroidAppDevelopment/wiki)

More simple project templates can be found at https://github.com/nenick/android-gradle-template

## Last tests done with

* Android Studio 1.0.2
* Android Studio Unit Test Plugin 1.4.0
* Gradle Build Tools 1.0.0
* Gradle 2.2.1

## Features done

* Gradle + Android Studio as development Enironment
* Robolectric for unit tests
    * Mockito
    * Code coverage with JaCoCo
* Robolectric for acceptance tests
    * Code coverage with JaCoCo
* FEST Android assertions
* Espresso for acceptance tests
* [AndroidAnnotations](http://androidannotations.org/) generated dependency injection, less code, etc ...
* [Dagger](http://square.github.io/dagger/) dependency injection for runtime dependencies
* [RoboCoP](https://github.com/mediarain/RoboCoP) generate database management
* Native intellij unit test support: jump to tests, refactor test classes too, etc ...
* [Travis](https://travis-ci.org/) CI runs all test variants
* [Coveralls](https://coveralls.io/) shows unit test code coverage [coveralls-gradle-plugin](https://github.com/kt3k/coveralls-gradle-plugin)
* Clean Architecture based on this example [https://github.com/android10/Android-CleanArchitecture]

# Some notes

The base example is copied from https://github.com/android10/Android-CleanArchitecture I have only combined this with my favourite development tools and test setup.

# What does this project show

* Rest Communication <br>
    * How to use and test REST communication with AndroidAnnotations
    * (not yet ready)
* Database Handling
* how to separate the
