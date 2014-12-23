= The Application Module

Short: wires all stuff together to a runnable application.

This is a real android application module. Here you would specify all meta details for your
application.


* proguard-project.txt <br>
  Custom proguard rules. Perhaps all rules may moved to sub modules, where the rules belong to.
* logging.properties <br>
  Experimental: should be the single point for logging configurations.

* src/main
    * AndroidManifest.xml <br>
      App-Name, App-Icon, App-Permissions
    * test-project.properties <br>
      Contains paths to exploded AAR dependencies for robolectric. (Original it comes from
      eclipse project setup, but now it's only for robolectric support)

* src/androidTest
    * Instrumented android tests with Espresso.

* src/test
    * JVM tests supported by robolectric. 
