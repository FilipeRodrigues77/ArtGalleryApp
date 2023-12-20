# I~A REST API DOCUMENTATION

## Introduction

-> This REST API provides endpoints to manage information about artists, 
artworks, galleries, and exhibitions in the art world.

### Purpose
- The primary purpose of this API is to facilitate the creation, retrieval, 
updating, and deletion of information. It allows developers to seamlessly 
integrate art-related functionalities into their applications, providing a 
backend for managing diverse aspects of the art industry.

### Base URL
- The base URL for accessing the API is http://localhost:8020, and all 
endpoints are appended to this base URL.

### Technologies Used
- This API is built using Java with the Spark Framework, offering a lightweight 
and expressive approach to building web applications. 
It utilises the Gson library for JSON serialisation and deserialization, providing 
a seamless data interchange format.


### Endpoints Overview
- The API is organized into four main sections, each corresponding to a fundamental entity :

1. Artists: Manage information about artists, such as their biographies, and artworks.

2. Artworks: Handle details of individual artworks, including their titles, descriptions, and associated artists.

3. Galleries: Store information about art galleries, such as names, locations, and featured exhibitions.

4. Exhibitions: Manage details about art exhibitions, including titles and associated galleries.

### Authentication
- Currently, the API does not require authentication for basic operations. 

### Error Handling
- The API returns appropriate HTTP status codes for both successful and failed requests. 
Error responses include detailed JSON objects with a **message** property, aiding 
developers in identifying and resolving issues.

### Global Exception Handling
- Unhandled exceptions are caught and result in a 500 Internal Server Error.
The error details are logged, and a meaningful error response is sent.

  
### Resources
- Building Simple RESTful API
- Spark Framework REST API
- SparkJava Example

---------------------------------------------------------------------------------------------------------

# Artists

### Get All Artists
#### Get All Artists
- Endpoint: /artists
- Method: GET
- Description: Retrieve a list of all artists.
- Response: JSON array containing artist information.

### Response Sample

````
[
  {
    "id": 1,
    "name": "Francisco de Goya",
    "nationality": "Spanish",
    "biography": "",
    "birthdate": "1746",
    "deathdate": "1828",
    "slug": "francisco-de-goya"
  },
  {
    "id": 2,
    "name": "Rembrandt van Rijn",
    "nationality": "Dutch",
    "biography": "",
    "birthdate": "1606",
    "deathdate": "1669",
    "slug": "rembrandt-van-rijn"
  },
  {
    "id": 3,
    "name": "Édouard Manet",
    "nationality": "French",
    "biography": "",
    "birthdate": "1832",
    "deathdate": "1883",
    "slug": "edouard-manet"
  }
]

````
### Get Artist by ID
#### Endpoint: /artists/:id
- Method: GET
- Description: Retrieve details of a specific artist by ID.
- Parameters: id (integer): Artist ID
- Response: JSON object containing artist details.

###  Response Sample

````
{
    "id": 1,
    "name": "Francisco de Goya",
    "nationality": "Spanish",
    "biography": "",
    "birthdate": "1746",
    "deathdate": "1828",
    "slug": "francisco-de-goya"
}

````

### Artist Search with query parameters 

- Method: GET (Query Parameters)
- Description: Retrieve a list of artists matching the provided parameter.
- Response: JSON array containing artist/s information.

1. Parameter **name**: Endpoint -> **/artists/searchName**
2. Parameter **nationality**: Endpoint -> **/artists/searchNationality**
3. Parameter **birthdate**: Endpoint -> **/artists/searchBirthdate**
4. Parameter **deathdate**: Endpoint -> **/artists/searchDeathdate**

````
````

### Create New Artist
#### Endpoint: /artists
- Method: POST
- Description: Create a new artist.
- Request Body: JSON object representing the new artist.
- Response: JSON object containing details of the created artist.

-> To create an artist, there will be required the following request body :
   ** Please note that there is no need to send the key and value for id, 
      as the id is automatically generated.  

###  Sample for Request Body

````
{
    "name": "Creating an artist",
    "nationality": "Swedish",
    "biography": "This is just a sample on how to create an artist",
    "birthdate": "1756",
    "deathdate": "1821",
    "slug": "creating-an-artist"
}
````

### Response sample 
````
{
    "id": 20,
    "name": "Creating an artist",
    "nationality": "Swedish",
    "biography": "This is just a sample on how to create an artist",
    "birthdate": "1756",
    "deathdate": "1821",
    "slug": "creating-an-artist"
}

````

### Update Artist
#### Endpoint: /artists/:id
- Method: PUT
- Description: Update details of a specific artist by ID.
- Parameters: id (integer): Artist ID
- Request Body: JSON object representing the updated artist.
- Response: JSON object containing details of the updated artist or an error message.

-> To update an artist, there will be required the following request body:
   **Please note that you do not have to fill all the keys for your request body.
      You can choose to send only the required keys for the update.** 
   !id field cannot be updated!

###  Sample for Request Body 
````
{
    "name": "Updating an artist",
    "nationality": "French",
    "biography": "This is just a sample on how to update an artist",
    "birthdate": "1756",
    "deathdate": "1821",
    "slug": "updating-an-artist"
}
````

###  Response sample
** It is important to know that for now, the response sample will always reflect the request body,
meaning that if the request body has all the keys, the response body will have all the keys, and if 
the request body has only two keys, for example, the response body will have those two keys plus the artist's id.
````
{
    "id": 20,
    "name": "Updating an artist",
    "nationality": "French",
    "biography": "This is just a sample on how to update an artist",
    "birthdate": "1756",
    "deathdate": "1821",
    "slug": "updating-an-artist"
}
````

### Delete Artist
#### Endpoint: /artists/:id
- Method: DELETE
- Description: Delete a specific artist by ID.
- Parameters:
- id (integer): Artist ID
- Response: JSON object containing details of the deleted artist.

###  Response Sample
````
{
  "id": 1
}
-> The response will be a simple JSON containng only the id for the deleted object.
````

---------------------------------------------------------------------------------------------------------

# Artworks

### Get All Artworks
#### Endpoint: /artworks
- Method: GET
- Description: Retrieve a list of all artworks.
- Response: JSON array containing artwork information.

###  Response Sample
````
[
  {
    "id": 1,
    "price": 292872.0,
    "dimensionCm": "\"266.2 × 345.2 cm\"",
    "dimensionIN": "\"104 4/5 × 135 9/10 in\"",
    "name": "The Third of May",
    "medium": "Oil on canvas",
    "creationDate": "1814",
    "category": "Painting",
    "collectingInstitution": "Museo Nacional del Prado, Madrid",
    "slugReferenceArtist": "francisco-de-goya-the-third-of-may",
    "referenceImage": "https://d32dm0rphc51dk.cloudfront.net/m4X41Fun8gpDjn7Gat9cUg/{image_version}.jpg",
    "idGallery": 1,
    "idArtist": 1
  },
  {
    "id": 2,
    "price": 101807.0,
    "dimensionCm": "\"363.2 × 437.4 cm\"",
    "dimensionIN": "\"143 × 172 1/5 in\"",
    "name": "The Company of Frans Banning Cocq and Willem van Ruytenburch (The Night Watch)",
    "medium": "Oil on canvas",
    "creationDate": "1642",
    "category": "Painting",
    "collectingInstitution": "Rijksmuseum, Amsterdam",
    "slugReferenceArtist": "rembrandt-van-rijn-the-company-of-frans-banning-cocq-and-willem-van-ruytenburch-the-night-watch",
    "referenceImage": "https://d32dm0rphc51dk.cloudfront.net/IG8ZLvVmZgQiTn2zK0Bp8w/{image_version}.jpg",
    "idGallery": 2,
    "idArtist": 2
  }
  
]
  
````

### Get Artwork by ID
#### Endpoint: /artworks/:id
- Method: GET
- Description: Retrieve details of a specific artwork by ID.
- Parameters: id (integer): Artwork ID
- Response: JSON object containing artwork details.

### Response Sample
````
{
  "id": 1,
  "price": 292872.0,
  "dimensionCm": "\"266.2 × 345.2 cm\"",
  "dimensionIN": "\"104 4/5 × 135 9/10 in\"",
  "name": "The Third of May",
  "medium": "Oil on canvas",
  "creationDate": "1814",
  "category": "Painting",
  "collectingInstitution": "Museo Nacional del Prado, Madrid",
  "slugReferenceArtist": "francisco-de-goya-the-third-of-may",
  "referenceImage": "https://d32dm0rphc51dk.cloudfront.net/m4X41Fun8gpDjn7Gat9cUg/{image_version}.jpg",
  "idGallery": 1,
  "idArtist": 1
}

````

### Artwork Search with query parameters

- Method: GET (Query Parameters)
- Description: Retrieve a list of artworks matching the provided parameter.
- Response: JSON array containing artwork/s information.

1. Parameter **name**: Endpoint -> **/artworks/searchName**
2. Parameter **medium**: Endpoint -> **/artworks/searchMedium**
3. Parameters **min & max**: Endpoint -> **/artworks/searchPriceRange**
4. Parameter **idArtist**: Endpoint -> **/artworks/searchByArtist** 
   - <span style="color: pink;">_(returns all the artworks with the provided artist id)_</span>
5. Parameter **idExhibition**: Endpoint -> **/artworks/searchByExhibition**
   - <span style="color: pink;">_(returns all the artworks with the provided exhibition id)_</span>
6. Parameter **idGallery**: Endpoint -> **/artworks/searchByGallery**
   - <span style="color: pink;">_(returns all the artworks with the provided gallery id)_</span>
7. Parameter **date**: Endpoint -> **/artworks/searchByDate**
   - <span style="color: pink;">_(returns all the artworks with the provided creation date)_</span>
8. Parameters **startDate & endDate**: Endpoint -> **/artworks/searchByDateRange**
   - <span style="color: pink;">_(returns all the artworks that the creation date is between the provided dates)_</span>
9. Parameter **category**: Endpoint -> **/artworks/searchByCategory**

````
````

### Create New Artwork
#### Endpoint: /artworks
- Method: POST
- Description: Create a new artwork.
- Request Body: JSON object representing the new artwork.
- Response: JSON object containing details of the created artwork.

### Sample Request body
-> To create an artwork, there will be required the following request body :
   ** Please note that there is no need to send the key and value for id,
      as the id is automatically generated.
   ** Similarly happens for keys "collectingInstitution" and "slugReferenceArtist",
      they are automatically generated once the object is created.

````
{
  "price": 292872.0,
  "dimensionCm": "\"266.2 × 345.2 cm\"",
  "dimensionIN": "\"104 4/5 × 135 9/10 in\"",
  "name": "Creating an artwork",
  "medium": "Oil on canvas",
  "creationDate": "1814",
  "category": "Painting",
  "referenceImage": "https://d32dm0rphc51dk.cloudfront.net/m4X41Fun8gpDjn7Gat9cUg/{image_version}.jpg",
  "idGallery": 1,
  "idArtist": 1
}

````

### Response sample 

```
{
  "id": 18
  "price": 292872.0,
  "dimensionCm": "\"266.2 × 345.2 cm\"",
  "dimensionIN": "\"104 4/5 × 135 9/10 in\"",
  "name": "Creating an artwork",
  "medium": "Oil on canvas",
  "creationDate": "1814",
  "category": "Painting",
  "collectingInstitution": "Museo Nacional del Prado, Madrid",
  "slugReferenceArtist": "francisco-de-goya-creating-an-artwork",
  "referenceImage": "https://d32dm0rphc51dk.cloudfront.net/m4X41Fun8gpDjn7Gat9cUg/{image_version}.jpg",
  "idGallery": 1,
  "idArtist": 1
}

```

### Update Artwork
#### Endpoint: /artworks/:id
- Method: PUT
- Description: Update details of a specific artwork by ID.
- Parameters: id (integer): Artwork ID
- Request Body: JSON object representing the updated artwork.
- Response: JSON object containing details of the updated artwork or an error message.


-> To update an artwork, there will be required the following request body :
** Please note that you do not have to fill all the keys for your request body.
You can choose to send only the required keys for the update.
** !id field cannot be updated!

###  Sample for Request Body
````
{
  "price": 100000.0,
  "dimensionCm": "\"266.2 × 345.2 cm\"",
  "dimensionIN": "\"104 4/5 × 135 9/10 in\"",
  "name": "Updating an artwork",
  "medium": "Oil on canvas",
  "creationDate": "1814",
  "category": "Painting",
  "referenceImage": "https://d32dm0rphc51dk.cloudfront.net/m4X41Fun8gpDjn7Gat9cUg/{image_version}.jpg",
  "idGallery": 1,
  "idArtist": 1
}

````

### Response sample
** For now, the response sample will always reflect the request body, meaning that if 
the request body has all the keys, the response body will have all the keys, and if
the request body has only two keys, for example, the response body will have those 
two keys plus the artwork's id.

````
{
  "id": 18
  "price": 100000.0,
  "dimensionCm": "\"266.2 × 345.2 cm\"",
  "dimensionIN": "\"104 4/5 × 135 9/10 in\"",
  "name": "Updating an artwork",
  "medium": "Oil on canvas",
  "creationDate": "1814",
  "category": "Painting",
  "collectingInstitution": "Museo Nacional del Prado, Madrid",
  "slugReferenceArtist": "francisco-de-goya-updating-an-artwork",
  "referenceImage": "https://d32dm0rphc51dk.cloudfront.net/m4X41Fun8gpDjn7Gat9cUg/{image_version}.jpg",
  "idGallery": 1,
  "idArtist": 1
}
````

### Delete Artwork
#### Endpoint: /artworks/:id
- Method: DELETE
- Description: Delete a specific artwork by ID.
- Parameters: id (integer): Artwork ID
- Response: JSON object containing details of the deleted artwork.

###  Response Sample
````
{
  "id": 1
}

-> The response will be a simple JSON containng only the id for the deleted object.
````

---------------------------------------------------------------------------------------------------------------

# Galleries

### Get All Galleries
#### Endpoint: /galleries
- Method: GET
- Description: Retrieve a list of all galleries.
- Response: JSON array containing gallery information.

###  Response Sample

````
[
  {
    "id": 1,
    "nameGallery": "Museo Nacional del Prado",
    "email": "",
    "regionName": "Europe"
  },
  {
    "id": 2,
    "nameGallery": "Rijksmuseum",
    "email": "",
    "regionName": "Europe"
  },
  {
    "id": 3,
    "nameGallery": "Musée d'Orsay",
    "email": "",
    "regionName": "Europe"
  },
  {
    "id": 4,
    "nameGallery": "Musée du Louvre",
    "email": "",
    "regionName": "Europe"
  }
]
````

### Get Gallery by ID
#### Endpoint: /galleries/:id
- Method: GET
- Description: Retrieve details of a specific gallery by ID.
- Parameters: id (integer): Gallery ID
- Response: JSON object containing gallery details.

###  Response Sample
````
{
    "id": 1,
    "nameGallery": "Museo Nacional del Prado",
    "email": "",
    "regionName": "Europe"
}
````

### Gallery Search with query parameters

- Method: GET (Query Parameters)
- Description: Retrieve a list of galleries matching the provided parameter.
- Response: JSON array containing gallery/ies information.

1. Parameter **name**: Endpoint -> **/galleries/search**
2. Parameter **region**: Endpoint -> **/galleries/searchByRegion**

````
````

### Create New Gallery
#### Endpoint: /galleries
- Method: POST
- Description: Create a new gallery.
- Request Body: JSON object representing the new gallery.
- Response: JSON object containing details of the created gallery.

###  Sample for Request Body
````

{
    "nameGallery": "Creating a gallery",
    "email": "demoGallery@ia.net",
    "regionName": "Europe"
}
````
###  Response Sample
** The galley id is auto generated.
````
{
    "id": 13,
    "nameGallery": "Creating a gallery",
    "email": "demoGallery@ia.net",
    "regionName": "Europe"
}
````

### Update Gallery
#### Endpoint: /galleries/:id
- Method: PUT
- Description: Update details of a specific gallery by ID.
- Parameters: id (integer): Gallery ID
- Request Body: JSON object representing the updated gallery.
- Response: JSON object containing details of the updated gallery or an error message.

###  Sample for Request Body
````

{
    "nameGallery": "Creating a gallery",
    "email": "demoGallery@ia.net",
    "regionName": "Europe"
}
````
###  Response Sample
** The galley id is auto generated.
````
{
    "id": 13,
    "nameGallery": "Creating a gallery",
    "email": "demoGallery@ia.net",
    "regionName": "Europe"
}
````

### Delete Gallery
#### Endpoint: /galleries/:id
- Method: DELETE
- Description: Delete a specific gallery by ID.
- Parameters: id (integer): Gallery ID
- Response: JSON object containing details of the deleted gallery or an error message.

###  Response Sample
````
{
  "id": 1
}

-> The response will be a simple JSON containng only the id for the deleted object.
````

---------------------------------------------------------------------------------------------------------------

# Exhibitions

### Get All Exhibitions
#### Endpoint: /exhibitions
- Method: GET
- Description: Retrieve a list of all exhibitions.
- Response: JSON array containing exhibition information.

###  Response Sample
````
[
  {
    "id": 1,
    "nameExhibition": "Georges de La Tour 1593 - 1652",
    "startDate": {
      "day": 23,
      "month": 2,
      "year": 2016
    },
    "endDate": {
      "day": 12,
      "month": 6,
      "year": 2016
    },
    "Exdescription": "Rediscovered during the 20th century, Georges de la Tour is now recognized as a 
    leading figure in 17th-century French art. He was one of the leading Caravaggesque painters, 
    a style he may have discovered through the Utrecht school painters, who used the same chiaroscuro effects.",
    "Exstatus": "closed",
    "idGallery": "1"
  },
  {
    "id": 2,
    "nameExhibition": "Bosch. The 5th Centenary Exhibition",
    "startDate": {
      "day": 31,
      "month": 5,
      "year": 2016
    },
    "endDate": {
      "day": 11,
      "month": 9,
      "year": 2016
    },
    "Exdescription": "To celebrate the 5th centenary of the death of Hieronymus Bosch, 
    The Museo del Prado and Fundación BBVA is staging the first monographic exhibition 
    to be devoted to the artist in Spain, the most complete and the one of the highest 
    quality organised to date. ",
    "Exstatus": "closed",
    "idGallery": "1"
  }
  
]
````

### Get Exhibition by ID
#### Endpoint: /exhibitions/:id
- Method: GET
- Description: Retrieve details of a specific exhibition by ID.
- Parameters:
- id (integer): Exhibition ID
- Response: JSON object containing exhibition details

###  Response Sample
````
{
    "id": 1,
    "nameExhibition": "Georges de La Tour 1593 - 1652",
    "startDate": {
      "day": 23,
      "month": 2,
      "year": 2016
    },
    "endDate": {
      "day": 12,
      "month": 6,
      "year": 2016
    },
    "Exdescription": "Rediscovered during the 20th century, Georges de la Tour is now recognized as a 
    leading figure in 17th-century French art. He was one of the leading Caravaggesque painters, 
    a style he may have discovered through the Utrecht school painters, who used the same chiaroscuro effects.",
    "Exstatus": "closed",
    "idGallery": "1"
}

````

### Exhibition Search with query parameters

- Method: GET (Query Parameters)
- Description: Retrieve a list of exhibitions matching the provided parameter.
- Response: JSON array containing exhibition/s information.

1. Parameter **name**: Endpoint -> **/exhibitions/search**
2. Parameter **status**: Endpoint -> **/exhibition/searchByStatus**
3. Parameter **startDate**: Endpoint -> **/exhibition/searchByStartDate**
4. Parameter **endDate**: Endpoint -> **/exhibition/searchByEndDate**
5. Parameter **idGallery**: Endpoint -> **/exhibition/searchByGallery**
   - <span style="color: pink;">_(returns all the exhibitions with the provided gallery id)_</span>

````
````

### Create New Exhibition
#### Endpoint: /exhibitions
- Method: POST
- Description: Create a new exhibition.
- Request Body: JSON object representing the new exhibition.
- Response: JSON object containing details of the created exhibition.


###  Sample Request body
** id is auto generated.
````
{
    "nameExhibition": "Creating an exhibition",
    "startDate": {
      "day": 23,
      "month": 11,
      "year": 2023
    },
    "endDate": {
      "day": 12,
      "month": 1,
      "year": 2024
    },
    "Exdescription": "Sample on how to create an exhibition",
    "idGallery": "1"
}
````

###  Response Sample
** The value for exhibition status (Exstatus) is assigned automatically according to the entered dates.
````
{
    "id": 15,
    "nameExhibition": "Creating an exhibition",
     "startDate": {
      "day": 23,
      "month": 11,
      "year": 2023
    },
    "endDate": {
      "day": 12,
      "month": 1,
      "year": 2024
    },
    "Exdescription": "Sample on how to create an exhibition",
    "Exstatus": "open",
    "idGallery": "1"
}
````
### Update Exhibition
#### Endpoint: /exhibitions/:id
- Method: PUT
- Description: Update details of a specific exhibition by ID.
- Parameters: id (integer): Exhibition ID
- Request Body: JSON object representing the updated exhibition.
- Response: JSON object containing details of the updated exhibition.

###  Sample Request body
** id is auto generated.
````
{
    "nameExhibition": "Update an exhibition",
    "startDate": {
      "day": 23,
      "month": 11,
      "year": 2023
    },
    "endDate": {
      "day": 12,
      "month": 1,
      "year": 2023
    },
    "Exdescription": "Sample on how to update an exhibition",
    "idGallery": "1"
}
````

###  Response Sample
** The value for exhibition status (Exstatus) is assigned automatically according to the entered dates.
````
{
    "id": 15,
     "nameExhibition": "Update an exhibition",
    "startDate": {
      "day": 23,
      "month": 11,
      "year": 2023
    },
    "endDate": {
      "day": 1,
      "month": 12,
      "year": 2023
    },
    "Exdescription": "Sample on how to update an exhibition",
    "Exstatus": "closed",
    "idGallery": "1"
}
````

### Delete Exhibition
#### Endpoint: /exhibitions/:id
- Method: DELETE
- Description: Delete a specific exhibition by ID.
- Parameters: id (integer): Exhibition ID
- Response: JSON object containing details of the deleted exhibition or an error message.

###  Response Sample
````
{
  "id": 15
}

-> The response will be a simple JSON containng only the id for the deleted object.
````

---------------------------------------------------------------------------------------------------------------

# Exhibitions

### Get All Nationalities 
#### Endpoint: /nationalities

- Method: GET
- Description: Retrieve a list of all nationalities.
- Parameters:  no parameters.
- Response: JSON array containing nationality information.

###  Response Sample

````
[
  {
    "nationality": "Algerian"
  },
  {
    "nationality": "American"
  },
  {
    "nationality": "Angolan"
  },
  {
    "nationality": "Argentinian"
  },
  ...
]
````

---------------------------------------------------------------------------------------------------------------

# Regions

### Get All Regions
#### Endpoint: /regions

- Method: GET
- Description: Retrieve a list of all regions.
- Parameters:  no parameters.
- Response: JSON array containing region information.

###  Response Sample

````
[
  {
    "region": "Africa"
  },
  {
    "region": "Asia"
  },
  {
    "region": "Central America"
  },
  ...
]
````























