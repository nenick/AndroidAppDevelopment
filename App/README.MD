# The Application Module

Short: wires all stuff together to a runnable application.

![blubber](http://yuml.me/diagram/plain/class/[App]->[Data], [App]->[Domain], [App]->[Presentation], , [Domain]-> [DataRepository], [Data]-> [DataRepository], , [Presentation]-> [DomainInteractor], [Domain]-> [DomainInteractor] "Module organisation")

This is a main android application module. Here you would specify all meta details for your
application. Also it contains some last level tests with robolectric and espresso.

* **proguard-project.txt** <br>
  Custom proguard rules. Perhaps all rules may moved to sub modules, where the rules belong to.
* **logging.properties** <br>
  Experimental: should be the single point for logging configurations.

* **src/main**
    * **AndroidManifest.xml** <br>
      App-Name, App-Icon, App-Permissions
    * **test-project.properties** <br>
      Contains paths to exploded AAR dependencies for robolectric. (Original it comes from
      eclipse project setup, but now it's only for robolectric support)

* **src/androidTest** <br>
    Instrumented android tests with Espresso.

* **src/test** <br>
    JVM tests supported by robolectric. 
