# The Data Network Module

**Short**: Provides access to services through the network.

**Long**: Implements network access with AndroidAnnotations.

* **src/main**
    * **java** <br>
        Contains the implementation classes.
    * **res/values/strings.xml** <br>
        Current it's just a workaround to use AndroidAnnotations because it search for
        a generated R class and this dummy content ensure it.

* **src/test** <br>
    Testing the REST communication module with robolectric support.

## Testing

The value for writing tests on this module is to proof the parsing of a Json request and response.

There are 4 ways how this module may be tested.

1. Don't test it

You could take this decision because you will check this behavior on later test steps
which include more application modules or at real system tests.

2. Real network API

This way could drive into errors, which not belong to you. Missing network access, hard to prepare
test data, etc .. But it will give you the most real feedback if your implementation is correct.

3. Mock Server like Wiremock

With a mock server you can full control the API behavior. It may be reused at later test
steps where you need also fixed test data and must control the behavior. Drawback it's not a real
API and changes to API must be done in the mock configuration but it's not much effort because you
can just record the request and paste it to you mock server configuration.

4. HttpClient mock

Robolectric supports to mock the requests and give back a prepared response. This is the fasted
and less error prone way to check the behavior. This have also the drawback not be a real API.