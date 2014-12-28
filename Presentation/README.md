# The Data Network Module

**Short**: Provides access to services through the network.

**Long**: Implements network access with AndroidAnnotations.

* **src/main**
    * **java** <br>
        Contains the implementation classes.
    * **res** <br>
        Contains the resources.

* **src/test** <br>
    Testing the presentation with robolectric support.

## Cursor vs DTO

In android is it common to use a cursor (data implementation detail) inside view classes. There
are tools which try to force the usage of cursors like CursorAdapter and CursorLoader. But we could
also take alternative adapter and tools like async tasks to get the same result. Cursors are hard
to mock, its much easier to prepare bunch of DTOs instead a collection at a cursor.

The academic clean way is not to use data implementation details at presentation layer but the
cursor could also be handled as a DTO, because it transport data and the only exception it may have
notification registration for data changes.

*Current I trying the DTO way. The notification registration for data changes will be the hardest
one to archive with custom DTO techniques. We will see how far i come or when to use cursors again.*

## Architecture

### Activity
Responsible for page navigation. This one decide whether to start another activity or update
other fragments at multi pane views. Current activities have no presenter, because they never have
a need to interact with the model, only fragments should have it. Activities only combine fragments
and transport some intent data to the fragments.

### Fragment
Each view is designed as fragment. Gives you the best possibilities to implement multi pane views
or reuse views. Implements how things are displayed.

### Presenter
Each Fragment have his own presenter. Receives view events, which should interact with the model
and say the view what it should do but not how to do.

## Testing

The value for writing tests on this module is to proof the UI behavior.

Testing UI is not easy to automate. Robolectric give you much support to simulate the UI but
it's more complex and slower compared to other unit test styles.

The MVP pattern should remove the test complexity. With this pattern you may avoid unit tests on
UI implementation and focus on the behavior specified at presenter.

There are different ways how this module may be tested.

### Don't test it

You could take this decision because you will check this behavior on later test steps
which include more application modules or at real system tests.

### Driving the lifecycle with robolectric

Robolectric supports to take an activity and start it with lifecycle methods. This will give you
a simulated running application with headless ui. But you can access ui elements and invoke action
on them like click buttons, check title string, etc... This will slow down the test execution but

Classes with *ViewFunctionTest drive all view functionality, ignores special cases.
Classes with *ViewTest will use lifecycle and produce alone very high coverage.

### Just invoke methods on objects

Just instantiate new class object inject some mocks and invoke methods to test. Will be very fast
but may need much time to create. E.g. onCreate() method will call super.onCreate() but this
method call will only work with "Drive lifecycle with robolectric" way. So you are forced to use
techniques like mockito spy to avoid errors.

### Use real Domain implementation

This way could drive into errors, which not belong to you. Missing network access, hard to prepare
test data, etc .. But it will give you the most real feedback if your implementation is correct. To
avoid some of the drawbacks you could inject mocked data modules.

### Mock the dependencies to the Domain

With dagger you have possibility to inject a mocked Domain module. Here you have big units, but
you could see the activity, fragment, presenter combination as a one unit because the extra classes
belongs to a micro architecture to get clean and maintainable code.

This approach is used for lifecycle driving test.

### Class with mocked dependencies

Common unit test style see also "Basic: Just invoke methods on objects"

Classes with *PresenterTest will just use the presenter to verify the UI behavior.