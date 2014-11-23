
# Rapid start development and test
with Android Studio, Gradle, Espresso, Robolectric, AndroidAnnotations, JaCoCo

[![Build Status](https://travis-ci.org/nenick/AndroidAppDevelopment.svg)](https://travis-ci.org/nenick/AndroidAppDevelopment) **& UnitTest** [![Coverage Status](https://coveralls.io/repos/nenick/AndroidAppDevelopment/badge.png?branch=master)](https://coveralls.io/r/nenick/AndroidAppDevelopment?branch=master)

***Wishes, improvements and discussions about the stuff here are welcome***

[see also wiki for more help](https://github.com/nenick/AndroidAppDevelopment/wiki)

More simple project templates can be found at https://github.com/nenick/android-gradle-template

## Last tests done with

* Android Studio 0.8.14
* Gradle Build Tools 0.12.+
* Gradle 1.12

## Features done

* Gradle + AndroidStudio as development Enironment
* Robolectric for unit tests
    * Mockito
    * Code coverage with JaCoCo
    * Code coverage with Teamcity
* Robolectric for component tests
    * Code coverage with JaCoCo
    * Code coverage with Teamcity
* FEST Android assertions
* Espresso for acceptance tests
* [AndroidAnnotations](http://androidannotations.org/) generate dependency injection
* [RoboCoP](https://github.com/mediarain/RoboCoP) generate database management
* Shortcut: jump between test and implementation with default short cut
* [Travis](https://travis-ci.org/) CI runs all test variants
* [Coveralls](https://coveralls.io/) shows unit test code coverage [coveralls-gradle-plugin](https://github.com/kt3k/coveralls-gradle-plugin)
* Clean Architecture based on this create example [https://github.com/android10/Android-CleanArchitecture]

# Some notes

The base example is copied from https://github.com/android10/Android-CleanArchitecture I have only combined this with my favourite development tools and test setup.
