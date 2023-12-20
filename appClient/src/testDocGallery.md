# Retrieve/ Add / Update and Delete Gallery data

### `getAllGalleries()`

**Description:**
This method retrieves a list of all galleries from a specified endpoint by making an HTTP GET request.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Call the `getAllGalleries()` method.
3. Check if the returned list is not null.
4. Verify that the list contains instances of the `Gallery` class.
5. Ensure that the HTTP response code is 200.

### `getGalleryById(int galleryId)`

**Description:**
This method retrieves a specific gallery by its ID from a specified endpoint by making an HTTP GET request.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `galleryId` parameter.
3. Call the `getGalleryById(galleryId)` method.
4. Check if the returned `Gallery` object is not null.
5. Ensure that the HTTP response code is 200.

### `getGalleriesByRegion(String region)`

**Description:**
This method retrieves a list of galleries by region from a specified endpoint by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `region` parameter.
3. Call the `getGalleriesByRegion(region)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Gallery` class.
6. Ensure that the HTTP response code is 200.

### `getGalleryByName(String name)`

**Description:**
This method retrieves a list of galleries based on a specified name from a specified endpoint by making an HTTP GET request with a query parameter.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `name` parameter.
3. Call the `getGalleryByName(name)` method.
4. Check if the returned list is not null.
5. Verify that the list contains instances of the `Gallery` class.
6. Ensure that the HTTP response code is 200.

### `updateGallery(Gallery selectedGallery)`

**Description:**
This method updates a gallery by sending a PUT request to a specified endpoint with the updated gallery information.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `selectedGallery` parameter with updated information.
3. Call the `updateGallery(selectedGallery)` method.
4. Ensure that the HTTP response code is 201, indicating a successful update.

### `removeGallery(Gallery selectedGallery)`

**Description:**
This method removes a gallery by sending a DELETE request to a specified endpoint with the ID of the gallery to be removed.

**Test Scenario:**
1. Ensure that the server is running and accessible at the specified URL.
2. Provide a valid `selectedGallery` parameter with the ID of the gallery to be removed.
3. Call the `removeGallery(selectedGallery)` method.
4. Ensure that the HTTP response code is 200, indicating a successful removal.

