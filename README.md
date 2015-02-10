
# Rapid start development and test
with Android Studio, Gradle, Espresso, Robolectric, AndroidAnnotations, JaCoCo

[![Build Status](https://travis-ci.org/nenick/AndroidAppDevelopment.svg)](https://travis-ci.org/nenick/AndroidAppDevelopment) **& UnitTest** [![Coverage Status](https://coveralls.io/repos/nenick/AndroidAppDevelopment/badge.png?branch=master)](https://coveralls.io/r/nenick/AndroidAppDevelopment?branch=master)

***Wishes, improvements and discussions about the stuff here are welcome***

# This Project already works but not ready yet

See sub modules README.MD for more details
* [App](https://github.com/nenick/AndroidAppDevelopment/tree/master/App)
* [Data Network](https://github.com/nenick/AndroidAppDevelopment/tree/master/DataNetwork)
* [Data Network Json](https://github.com/nenick/AndroidAppDevelopment/tree/master/DataNetworkJson)
* [Presentation](https://github.com/nenick/AndroidAppDevelopment/tree/master/Presentation)

[see also wiki for more help](https://github.com/nenick/AndroidAppDevelopment/wiki)

More simple project templates can be found at https://github.com/nenick/android-gradle-template

## Last tests done with

* Android Studio 1.0.2
* Android Studio Unit Test Plugin 1.4.0
* Gradle Build Tools 1.0.0
* Gradle 2.2.1

## Getting Started

* clone the project
* open it with android studio
* install the plugin https://github.com/evant/android-studio-unit-test-plugin
* Run app or tests

## Test styles

This project should show and compare some different testing styles. So this project have some
redundant tests. At each module i have a short overview about how and what may be tested.

There are different test styles, opinions about what should be tested and
many tools for all approaches. Here some basic questions which should each ask himself

* How important is it to write error less application? <br>
  That indicate how many test you will need. For prototyping you will need no or few tests but for
  high risk applications you will need more than 100% coverage. Also it will help which kind of
  test are necessary.

* Different test steps <br>
  This project divides the tests between *unit tests* which tests the smallest meaningful component
  and this could be one ore more classes in combination. *Integration tests* will combine components
  and check the correct interaction. Components could be project modules or third party frameworks
  like android components. *System tests* will integrate all possible components and check that all
  together work how expected. *Manually testing* is driving the application by a test plan or
  you do it exploratory. That is the best way to find not covered bugs.

* Robolectric is more than unit testing <br>
  There are two ways how robolectric may be used. *The first way* is writing classic unit tests with
  mocking the UI. This kind use only minimal robolectric support and is so fast how fast unit test
  should be.
  *The second way* is driving the activity lifecycle and and have a simulated UI. This is
  slower (>10x) compared to mocked tests but faster then android tests on emulator or devices. It
  depends a bit on the project architecture but mostly this approach integrates parts like
  Android SDK, Activity, Fragment, Presenter, Helper classes, Domain, Data. So driving the
  lifecycle is more like integration testing.

* Some people say Unit Tests are worthless <br>
  It depends. Unit Tests are the fastest one compared to all other steps but most time also the
  less valuable tests. How many times an unit test was failing and it was just of some refactoring
  which forced the test to change too, but it was no functional error? This project will rarely
  use unit tests on a small units. It tries to combine all necessary units which creates a
  together a functionality. The approach is that each layer presents functionality to other layers.
  So most unit tests are written on top of layer services to test functionality. This may be
  compared to protocol tests which mostly check technical aspects. Examples would be check method produce
  NullPointerException, property of class contains the value, getter will return correct value,
  method x will call class internal method y and so on. This kind of tests are this one which
  will only frustrate you when you just factoring some internals. Best parts for small unit tests
  are methods algorithm, they could be best tested on low levels. Test should only change when
  functionality changes.

* Is tests coverage good enough <br>
  Take smart decisions about what and how much to test. Testing each code line with unit tests is
  what we get often told, but brings this the most value? You have manually tester, why should you
  write automatic tests? For a onetime app manually testing could be enough. Not having 100%
  unit test coverage will give you more time for higher test steps.

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
