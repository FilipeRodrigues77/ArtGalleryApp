## Documentation for the class that retrieves data from Artwork in the REST API


### `getAllArtworks()`

**Description:**
This method retrieves a list of all artworks from a specified endpoint by making an HTTP GET request. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Call `getAllArtworks()` method.
3. Check if the returned list is not null.
4. Verify that the list contains instances of the `Artwork` class.
5. Ensure that the HTTP response code is 200.

### `getArtworksByArtistId(int idArtist)`

**Description:**
This method retrieves a list of artworks by artist ID from a specified endpoint by making an HTTP GET request. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `idArtist` parameter.
3. Call `getArtworksByArtistId(idArtist)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artwork` class.
6. Ensure that the HTTP response code is 200.

### `getArtworksByCategory(String category)`

**Description:**
This method retrieves a list of artworks by category from a specified endpoint by making an HTTP GET request. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `category` parameter.
3. Call `getArtworksByCategory(category)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artwork` class.
6. Ensure that the HTTP response code is 200.

### `getArtworksByMedium(String medium)`

**Description:**
This method retrieves a list of artworks by medium from a specified endpoint by making an HTTP GET request. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `medium` parameter.
3. Call `getArtworksByMedium(medium)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artwork` class.
6. Ensure that the HTTP response code is 200.

### `getArtworkByName(String name)`

**Description:**
This method retrieves a list of artworks by name from a specified endpoint by making an HTTP GET request with a query parameter. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `name` parameter.
3. Call `getArtworkByName(name)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artwork` class.
6. Ensure that the HTTP response code is 200.

### `getArtworksByDate(String startDate, String endDate)`

**Description:**
This method retrieves a list of artworks by date range from a specified endpoint by making an HTTP GET request with query parameters. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide valid `startDate` and `endDate` parameters.
3. Call `getArtworksByDate(startDate, endDate)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artwork` class.
6. Ensure that the HTTP response code is 200.

### `getArtworksByPrice(double minPrice, double maxPrice)`

**Description:**
This method retrieves a list of artworks by price range from a specified endpoint by making an HTTP GET request with query parameters. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide valid `minPrice` and `maxPrice` parameters.
3. Call `getArtworksByPrice(minPrice, maxPrice)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artwork` class.
6. Ensure that the HTTP response code is 200.

### `getArtworksByGalleryId(int idGallery)`

**Description:**
This method retrieves a list of artworks by gallery ID from a specified endpoint by making an HTTP GET request. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `idGallery` parameter.
3. Call `getArtworksByGalleryId(idGallery)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artwork` class.
6. Ensure that the HTTP response code is 200.

### `getArtworksByExhibitionId(int idExhibition)`

**Description:**
This method retrieves a list of artworks by exhibition ID from a specified endpoint by making an HTTP GET request. 

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `idExhibition` parameter.
3. Call `getArtworksByExhibitionId(idExhibition)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Artwork` class.
6. Ensure that the HTTP response code is 200.

