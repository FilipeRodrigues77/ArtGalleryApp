## Retrieve Exhibition data 

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

### `getExhibitionsByStatus(String status)`

**Description:**
This method retrieves a list of exhibitions by a specific status from the server by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `status` parameter.
3. Call the `getExhibitionsByStatus(status)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Exhibition` class.
6. Ensure that the HTTP response code is 200.

### `getExhibitionsByStartDate(String startDate)`

**Description:**
This method retrieves a list of exhibitions by a specific start date from the server by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `startDate` parameter.
3. Call the `getExhibitionsByStartDate(startDate)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Exhibition` class.
6. Ensure that the HTTP response code is 200.

### `getExhibitionsByEndDate(String endDate)`

**Description:**
This method retrieves a list of exhibitions by a specific end date from the server by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `endDate` parameter.
3. Call the `getExhibitionsByEndDate(endDate)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Exhibition` class.
6. Ensure that the HTTP response code is 200.

### `getExhibitionsByName(String name)`

**Description:**
This method retrieves a list of exhibitions by a specific name from the server by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `name` parameter.
3. Call the `getExhibitionsByName(name)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Exhibition` class.
6. Ensure that the HTTP response code is 200.

### `getExhibitionsByIdGallery(int idGallery)`

**Description:**
This method retrieves a list of exhibitions associated with a specific gallery ID from the server by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `idGallery` parameter.
3. Call the `getExhibitionsByIdGallery(idGallery)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Exhibition` class.
6. Ensure that the HTTP response code is 200.

