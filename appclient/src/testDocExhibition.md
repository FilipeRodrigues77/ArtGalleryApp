## Documentation for the class that retrieves data from Exhibition in the REST API

### `getAllExhibitions()`

**Description:**
This method retrieves a list of all exhibitions from a specified endpoint by making an HTTP GET request. 
It utilizes Gson for deserialization, and a custom `LocalDateAdapter` is registered to handle deserialization 
of `LocalDate` objects.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Call the `getAllExhibitions()` method.
3. Check if the returned list is not null.
4. Verify that the list contains instances of the `Exhibition` class.
5. Ensure that the HTTP response code is 200.

