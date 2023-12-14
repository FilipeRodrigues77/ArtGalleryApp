

import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static spark.Spark.*;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Gallery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.MemoryStorage;
import util.MessageResponse;

/**
 * Useful resources:
 * <p>https://dzone.com/articles/building-simple-restful-api</p>
 * <p>https://www.baeldung.com/spark-framework-rest-api</p>
 * <p>https://github.com/andrisasuke/sparkjava-example/blob/master/src/main/java/com/hydra/spark/sample/Main.java</p>
 *
 */
public class RunIAServer {

    private static final Logger logger = LoggerFactory.getLogger(RunIAServer.class);
    public static void main(String[] args) {

        // Default port is 4567, hence we're running at http://localhost:4567/<endpoint>

        port(8010);
        logger.info("Starting Main server at {}", new Date().toString());

        /*  INSTANTIATE STORAGE */
        MemoryStorage storage = new MemoryStorage();
        // DBStorage storage = new DBStorage();

        /* INSTANTIATE GSON CONVERTER */
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        /* CONFIGURE END POINTS */

        // ARTIST
        path("/artists",()->{

            get("", (request, response) -> {
                response.type("application/json");
                List<Artist> artistsList = storage.getAllArtists();
                return gson.toJson(artistsList);
            });

            get("/searchName", (request, response) -> {
                String artistName = request.queryParams("name");

                if (artistName != null && !artistName.isEmpty()) {
                    response.type("application/json");
                    List<Artist> matchingArtists = storage.getArtistByName(artistName);

                    if (matchingArtists.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artists found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtists);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid artist name");
                    return jsonObject.toString();
                }
            });

            get("/searchNationality", (request, response) -> {
                String nationality = request.queryParams("nationality");

                if (nationality != null && !nationality.isEmpty()) {
                    response.type("application/json");
                    List<Artist> matchingArtists = storage.getArtistsByNationality(nationality);

                    if (matchingArtists.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artists found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtists);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid artist nationality");
                    return jsonObject.toString();
                }
            });

            get("/searchBirthdate", (request, response) -> {
                String birthdate = request.queryParams("birthdate");
                boolean isBirthdate = true;

                if (birthdate != null && birthdate.matches("\\d{4}")) {
                    response.type("application/json");
                    List<Artist> matchingArtists = storage.getArtistsByDate(birthdate,isBirthdate);

                    if (matchingArtists.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artists found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtists);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid birthdate (4 digits)");
                    return jsonObject.toString();
                }
            });

            get("/searchDeathdate", (request, response) -> {
                String deathdate = request.queryParams("deathdate");
                boolean isBirthdate = false;

                if (deathdate != null && deathdate.matches("\\d{4}")) {
                    response.type("application/json");
                    List<Artist> matchingArtists = storage.getArtistsByDate(deathdate, isBirthdate);

                    if (matchingArtists.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artists found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtists);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid death date (4 digits)");
                    return jsonObject.toString();
                }
            });

            get("/:id", (request, response) -> {
                try {
                    int idArtist = Integer.parseInt(request.params(":id"));
                    // Define the type of response.
                    response.type("application/json");
                    Artist artist = storage.getArtistByID(idArtist);

                    if (artist == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Artist not found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(artist);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid artist ID format");
                    return jsonObject.toString();
                }



            });

            post("",(request,response)->{
                Artist newArtist = gson.fromJson(request.body(),Artist.class);
                Artist createdArtist = storage.createArtist(newArtist);
                response.type("application/json");
                return gson.toJson(createdArtist);
            });

            put("/:id", (request, response) -> {

                try {
                    int artistId = Integer.parseInt(request.params(":id"));
                    Artist artist = gson.fromJson(request.body(), Artist.class);
                    artist.setId(artistId);

                    Artist verifyArtist = storage.getArtistByID(artistId);

                    // handle cases where the artist with the given ID is not found
                    if (verifyArtist == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Artist not found");
                        return jsonObject.toString();

                    } else {
                        Artist updatedArtist = storage.updateArtist(artist);
                        response.type("application/json");
                        return gson.toJson(updatedArtist);
                    }

                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid artist ID format");
                    return jsonObject.toString();
                }

            });

            delete("/:id", (request, response) -> {

                try {
                    int artistId = Integer.parseInt(request.params(":id"));
                    Artist artistToDelete = storage.getArtistByID(artistId);
                    // handle cases where the artist with the given ID is not found
                    if (artistToDelete == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Artist not found");
                        return jsonObject.toString();
                    } else {
                        artistToDelete = storage.deleteArtist(artistId);
                        response.type("application/json");
                        return gson.toJson(artistToDelete);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid artist ID format");
                    return jsonObject.toString();
                }

            });

        });

        // ARTWORK
        path("/artworks", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                List<Artwork> artworksList = storage.getAllArtworks();
                return gson.toJson(artworksList);
            });

            get("/searchName", (request, response) -> {
                String artworkName = request.queryParams("name");

                if (artworkName != null && !artworkName.isEmpty()) {
                    response.type("application/json");
                    List<Artwork> matchingArtworks = storage.getArtworkByName(artworkName);

                    if (matchingArtworks.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artworks found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtworks);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid artwork name");
                    return jsonObject.toString();
                }
            });

            get("/searchMedium", (request, response) -> {
                String artworkMedium = request.queryParams("medium");

                if (artworkMedium != null && !artworkMedium.isEmpty()) {
                    response.type("application/json");
                    List<Artwork> matchingArtworks = storage.getArtworksByMedium(artworkMedium);

                    if (matchingArtworks.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artworks found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtworks);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid artwork medium");
                    return jsonObject.toString();
                }
            });

            get("/searchPriceRange", (request, response) -> {
                String minPriceParam = request.queryParams("min");
                String maxPriceParam = request.queryParams("max");

                if (minPriceParam != null && maxPriceParam != null && !minPriceParam.isEmpty() && !maxPriceParam.isEmpty()) {
                    double minPrice = Double.parseDouble(minPriceParam);
                    double maxPrice = Double.parseDouble(maxPriceParam);

                    if (maxPrice <= minPrice) {
                        response.status(400); // Bad Request
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "maxPrice must be greater than minPrice");
                        return jsonObject.toString();
                    }

                    response.type("application/json");
                    List<Artwork> matchingArtworks = storage.getArtworksByPriceRange(minPrice, maxPrice);

                    if (matchingArtworks.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artworks found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtworks);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide valid minPrice and maxPrice parameters");
                    return jsonObject.toString();
                }
            });

            get("/searchByArtist", (request, response) -> {
                // CHECK IF THE ARTIST EXISTS
                try{
                    int artistID = Integer.parseInt(request.queryParams("idArtist"));
                    Artist verifyArtist = storage.getArtistByID(artistID);
                    if (verifyArtist == null) {

                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Artist does not exist");
                        return jsonObject.toString();

                    } else {
                        response.type("application/json");
                        List<Artwork> matchingArtworks = storage.getArtworksByArtist(artistID);
                        return gson.toJson(matchingArtworks);
                    }

                } catch (NumberFormatException e){
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid artist ID format");
                    return jsonObject.toString();
                }

            });

            get("/searchByExhibition", (request, response) -> {
                // CHECK IF THE EXHIBITION EXISTS
                try {
                    int exhibitionId = Integer.parseInt(request.queryParams("idExhibition"));
                    Exhibition verifyExhibition = storage.getExhibitionByID(exhibitionId);

                    if (verifyExhibition == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Exhibition does not exist");
                        return jsonObject.toString();
                    } else {
                        response.type("application/json");
                        List<Artwork> matchingArtworks = storage.getArtworksByExhibition(exhibitionId);
                        return gson.toJson(matchingArtworks);
                    }

                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid exhibition ID format");
                    return jsonObject.toString();
                }
            });

            get("/searchByGallery", (request, response) -> {
                // CHECK IF THE GALLERY EXISTS
                try {
                    int galleryId = Integer.parseInt(request.queryParams("idGallery"));
                    Gallery verifyGallery = storage.getGalleryByID(galleryId);

                    if (verifyGallery == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Gallery does not exist");
                        return jsonObject.toString();
                    } else {
                        response.type("application/json");
                        List<Artwork> artistsInGallery = storage.getArtistsByGallery(galleryId);
                        return gson.toJson(artistsInGallery);
                    }

                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid gallery ID format");
                    return jsonObject.toString();
                }
            });

            get("/searchByCategory", (request, response) -> {
                String artworkCategory = request.queryParams("category");

                if (artworkCategory != null && !artworkCategory.isEmpty()) {
                    response.type("application/json");
                    List<Artwork> matchingArtworks = storage.getArtworksByCategory(artworkCategory);

                    if (matchingArtworks.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artworks found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtworks);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid artwork category");
                    return jsonObject.toString();
                }
            });

            get("/searchByDate", (request, response) -> {
                String creationDate = request.queryParams("date");

                if (creationDate != null && creationDate.matches("\\d{4}")) {
                    response.type("application/json");
                    List<Artwork> matchingArtists = storage.getArtworksByDate(creationDate);

                    if (matchingArtists.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artwork found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtists);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid date (4 digits)");
                    return jsonObject.toString();
                }
            });

            get("/searchByDateRange", (request, response) -> {
                String startDate = request.queryParams("startDate");
                String endDate = request.queryParams("endDate");

                if (startDate != null && endDate != null
                   && startDate.matches("\\d{4}") && endDate.matches("\\d{4}")) {

                    response.type("application/json");
                    List<Artwork> matchingArtworks = storage.getArtworksByDateRange(startDate, endDate);

                    if (matchingArtworks.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching artwork found within the specified date range");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingArtworks);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide valid start and end dates");
                    return jsonObject.toString();
                }
            });


            get("/:id", (request, response) -> {
                try {
                    int idArtwork = Integer.parseInt(request.params(":id"));
                    response.type("application/json");
                    Artwork artwork = storage.getArtworkByID(idArtwork);

                    if (artwork == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Artwork not found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(artwork);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid artwork ID format");
                    return jsonObject.toString();
                }
            });

            post("", (request, response) -> {
                Artwork newArtwork = gson.fromJson(request.body(), Artwork.class);
                Artwork createdArtwork = storage.createArtwork(newArtwork);
                response.type("application/json");
                return gson.toJson(createdArtwork);
            });

            put("/:id", (request, response) -> {
                try {
                    int artworkId = Integer.parseInt(request.params(":id"));

                    Artwork artworkToUpdate = gson.fromJson(request.body(), Artwork.class);
                    artworkToUpdate.setId(artworkId);

                    Artwork verifyArtwork = storage.getArtworkByID(artworkId);

                    // handle cases where the artworkToUpdate with the given ID is not found
                    if (verifyArtwork == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Artwork not found");
                        return jsonObject.toString();
                    } else {
                        Artwork updatedArtwork = storage.updateArtwork(artworkToUpdate);
                        response.type("application/json");
                        return gson.toJson(updatedArtwork);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid artwork ID format");
                    return jsonObject.toString();
                }
            });

            delete("/:id", (request, response) -> {
                try {

                    int artworkId = Integer.parseInt(request.params(":id"));
                    Artwork artworkToDelete = storage.getArtworkByID(artworkId);

                    // handle cases where the artwork with the given ID is not found
                    if (artworkToDelete == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Artwork not found");
                        return jsonObject.toString();
                    } else {
                        artworkToDelete = storage.deleteArtwork(artworkId);
                        response.type("application/json");
                        return gson.toJson(artworkToDelete);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid artwork ID format");
                    return jsonObject.toString();
                }
            });

        });

        // GALLERY
        path("/galleries", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                List<Gallery> galleriesList = storage.getAllGalleries();
                return gson.toJson(galleriesList);
            });

            get("/search", (request, response) -> {
                String galleryName = request.queryParams("name");

                if (galleryName != null && !galleryName.isEmpty()) {
                    response.type("application/json");
                    List<Gallery> matchingGalleries = storage.getGalleryByName(galleryName);

                    if (matchingGalleries.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching galleries found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingGalleries);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid gallery name");
                    return jsonObject.toString();
                }
            });

            get("/searchByRegion", (request, response) -> {
                String region = request.queryParams("region");

                if (region != null && !region.isEmpty()) {
                    response.type("application/json");
                    List<Gallery> matchingGalleries = storage.getGalleryByRegion(region);

                    if (matchingGalleries.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching galleries found for the specified region");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingGalleries);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid region");
                    return jsonObject.toString();
                }
            });


            get("/:id", (request, response) -> {
                try {
                    int idGallery = Integer.parseInt(request.params(":id"));
                    response.type("application/json");
                    Gallery gallery = storage.getGalleryByID(idGallery);

                    if (gallery == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Gallery not found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(gallery);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid gallery ID format");
                    return jsonObject.toString();
                }
            });

            post("", (request, response) -> {
                Gallery newGallery = gson.fromJson(request.body(), Gallery.class);
                Gallery createdGallery = storage.createGallery(newGallery);
                response.type("application/json");
                return gson.toJson(createdGallery);
            });

            put("/:id", (request, response) -> {
                try {
                    int galleryId = Integer.parseInt(request.params(":id"));

                    Gallery galleryToUpdate = gson.fromJson(request.body(), Gallery.class);
                    galleryToUpdate.setId(galleryId);

                    Gallery verifyGallery = storage.getGalleryByID(galleryId);

                    // handle cases where the galleryToUpdate with the given ID is not found
                    if (verifyGallery == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Gallery not found");
                        return jsonObject.toString();
                    } else {
                        Gallery updatedGallery = storage.updateGallery(galleryToUpdate);
                        response.type("application/json");
                        return gson.toJson(updatedGallery);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid gallery ID format");
                    return jsonObject.toString();
                }
            });

            delete("/:id", (request, response) -> {
                try {

                    int galleryId = Integer.parseInt(request.params(":id"));
                    Gallery galleryToDelete = storage.getGalleryByID(galleryId);

                    // handle cases where the gallery with the given ID is not found
                    if (galleryToDelete == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Gallery not found");
                        return jsonObject.toString();
                    } else {
                        galleryToDelete = storage.deleteGallery(galleryId);
                        response.type("application/json");
                        return gson.toJson(galleryToDelete);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid gallery ID format");
                    return jsonObject.toString();
                }
            });

        });

        // EXHIBITION
        path("/exhibitions", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                List<Exhibition> exhibitionsList = storage.getAllExhibitions();
                return gson.toJson(exhibitionsList);
            });

            get("/search", (request, response) -> {
                String exhibitionName = request.queryParams("name");

                if (exhibitionName != null && !exhibitionName.isEmpty()) {
                    response.type("application/json");
                    List<Exhibition> matchingExhibitions = storage.getExhibitionByName(exhibitionName);

                    if (matchingExhibitions.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching exhibitions found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingExhibitions);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid exhibition name");
                    return jsonObject.toString();
                }
            });

            get("/searchByStatus", (request, response) -> {
                // Get the status and exhibition name from the query parameters
                String exhibitionStatus = request.queryParams("status");

                if (exhibitionStatus != null && !exhibitionStatus.isEmpty()) {
                    response.type("application/json");

                    // Call the storage method to get exhibitions by status
                    List<Exhibition> matchingExhibitions = storage.getExhibitionByStatus(exhibitionStatus);

                    if (matchingExhibitions.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching exhibitions found for the provided status");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingExhibitions);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid exhibition status");
                    return jsonObject.toString();
                }
            });

            get("/searchByStartDate", (request, response) -> {
                // Get the startDate from the query parameters
                String dateRegexPattern = "\\d{4}-\\d{2}-\\d{2}";
                String startDateStr = request.queryParams("startDate");

                if (startDateStr != null && startDateStr.matches(dateRegexPattern)) {
                    response.type("application/json");

                    // Call the storage method to get exhibitions by startDate
                    List<Exhibition> matchingExhibitions = storage.getExhibitionByStartDate(startDateStr);

                    if (matchingExhibitions.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching exhibitions found for the provided startDate");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingExhibitions);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid startDate");
                    return jsonObject.toString();
                }
            });

            get("/searchByEndDate", (request, response) -> {
                // Get the endDate from the query parameters
                String dateRegexPattern = "\\d{4}-\\d{2}-\\d{2}";
                String endDateStr = request.queryParams("endDate");

                if (endDateStr != null && endDateStr.matches(dateRegexPattern)) {
                    response.type("application/json");

                    // Call the storage method to get exhibitions by endDate
                    List<Exhibition> matchingExhibitions = storage.getExhibitionByEndDate(endDateStr);

                    if (matchingExhibitions.isEmpty()) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "No matching exhibitions found for the provided endDate");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(matchingExhibitions);
                    }
                } else {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Please provide a valid endDate");
                    return jsonObject.toString();
                }
            });

            get("/searchByGallery", (request, response) -> {
                // CHECK IF THE GALLERY EXISTS
                try {
                    int galleryId = Integer.parseInt(request.queryParams("idGallery"));
                    Gallery verifyGallery = storage.getGalleryByID(galleryId);

                    if (verifyGallery == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Gallery does not exist");
                        return jsonObject.toString();
                    } else {
                        response.type("application/json");
                        List<Exhibition> exhibitionsInGallery = storage.getExhibitionsByGallery(galleryId);
                        return gson.toJson(exhibitionsInGallery);
                    }

                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid gallery ID format");
                    return jsonObject.toString();
                }
            });

            get("/:id", (request, response) -> {
                try {
                    int idExhibition = Integer.parseInt(request.params(":id"));
                    response.type("application/json");
                    Exhibition exhibition = storage.getExhibitionByID(idExhibition);

                    if (exhibition == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Exhibition not found");
                        return jsonObject.toString();
                    } else {
                        return gson.toJson(exhibition);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid exhibition ID format");
                    return jsonObject.toString();
                }
            });

            post("", (request, response) -> {
                Exhibition newExhibition = gson.fromJson(request.body(), Exhibition.class);
                Exhibition createdExhibition = storage.createExhibition(newExhibition);
                response.type("application/json");
                return gson.toJson(createdExhibition);
            });

            put("/:id", (request, response) -> {
                try {
                    int exhibitionId = Integer.parseInt(request.params(":id"));

                    Exhibition exhibitionToUpdate = gson.fromJson(request.body(), Exhibition.class);
                    exhibitionToUpdate.setId(exhibitionId);

                    Exhibition verifyExhibition = storage.getExhibitionByID(exhibitionId);

                    // handle cases where the exhibitionToUpdate with the given ID is not found
                    if (verifyExhibition == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Exhibition not found");
                        return jsonObject.toString();
                    } else {
                        Exhibition updatedExhibition = storage.updateExhibition(exhibitionToUpdate);
                        response.type("application/json");
                        return gson.toJson(updatedExhibition);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid exhibition ID format");
                    return jsonObject.toString();
                }
            });

            delete("/:id", (request, response) -> {
                try {
                    int exhibitionId = Integer.parseInt(request.params(":id"));
                    Exhibition exhibitionToDelete = storage.getExhibitionByID(exhibitionId);

                    // handle cases where the exhibition with the given ID is not found
                    if (exhibitionToDelete == null) {
                        response.status(404); // Not Found
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Exhibition not found");
                        return jsonObject.toString();
                    } else {
                        exhibitionToDelete = storage.deleteExhibition(exhibitionId);
                        response.type("application/json");
                        return gson.toJson(exhibitionToDelete);
                    }
                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Invalid exhibition ID format");
                    return jsonObject.toString();
                }
            });

        });

        // global exception handler
        exception(Exception.class, (e, request, response) -> {
            logger.error("{} : Got an exception for request : {}  ", e.getLocalizedMessage(), request.url());
            response.status(500);
            response.body(new MessageResponse(e).toString());
        });
    }

}
