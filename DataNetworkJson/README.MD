# The Data Network Json Module

**Short**: Contains json model classes for rest communication.

**Long**:

The model classes are generated from a schema. So you must only specify important things
like json mapping tool, models with properties and all the boilerplate stuff will be
written for you.

The files will be automatic generated if you trigger a build.

* **schema** <br>
    Contains a file for each model. The file name will be the class name

* **build.gradle**
    Here you find the generation tool configuration. Change here your preferred mapping tool.

* **gen** <br>
    Contains the generated files. This is under CVS control to trigger changes when a new tool
    version produce different class outcome.

For more information see also
* http://www.jsonschema2pojo.org/
* https://github.com/joelittlejohn/jsonschema2pojo
* https://github.com/joelittlejohn/jsonschema2pojo/wiki/Reference
