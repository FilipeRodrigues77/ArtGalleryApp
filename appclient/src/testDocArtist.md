## Documentation for the class that retrieves data from artist in the REST API

### `getAllArtists()`

**Description:**
This method retrieves a list of all artists from a specified endpoint by making an HTTP GET request.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Call `getAllArtists()` method.
3. Check if the returned list is not null.
4. Verify that the list contains instances of the `Artist` class.
5. Ensure that the HTTP response code is 200.

### `getArtistById(int artistId)`

**Description:**
This method retrieves a specific artist by ID from a specified endpoint by making an HTTP GET request.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `artistId` parameter.
3. Call `getArtistById(artistId)` method.
4. Check if the returned artist object is not null.
5. Verify that the artist object is an instance of the `Artist` class.
6. Ensure that the HTTP response code is 200.

### `getArtistByName(String name)`

**Description:**
This method retrieves a list of artists by name from a specified endpoint by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `name` parameter.
3. Call `getArtistByName(name)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artist` class.
6. Ensure that the HTTP response code is 200.

### `getArtistByNationality(String nationality)`

**Description:**
This method retrieves a list of artists by nationality from a specified endpoint by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `nationality` parameter.
3. Call `getArtistByNationality(nationality)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artist` class.
6. Ensure that the HTTP response code is 200.

### `getArtistByBirthdate(String birthdate)`

**Description:**
This method retrieves a list of artists by birthdate from a specified endpoint by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `birthdate` parameter.
3. Call `getArtistByBirthdate(birthdate)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artist` class.
6. Ensure that the HTTP response code is 200.

### `getArtistByDeathdate(String deathdate)`

**Description:**
This method retrieves a list of artists by deathdate from a specified endpoint by making an HTTP GET request with a query parameter. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `deathdate` parameter.
3. Call `getArtistByDeathdate(deathdate)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artist` class.
6. Ensure that the HTTP response code is 200.