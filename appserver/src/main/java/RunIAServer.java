

import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

        logger.info("Starting Main server at {}", new Date().toString());

        /*  INSTANTIATE STORAGE */
        MemoryStorage storage = new MemoryStorage();
        // DBStorage storage = new DBStorage();

        /* INSTANTIATE GSON CONVERTER */
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        /* CONFIGURE END POINTS */
        // ARTISTS
        path("/artists",()->{

            get("", (request, response) -> {
                response.type("application/json");
                List<Artist> artistsList = storage.getAllArtists();
                return gson.toJson(artistsList);
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

        path("/galleries", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                List<Gallery> galleriesList = storage.getAllGalleries();
                return gson.toJson(galleriesList);
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

        path("/exhibitions", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                List<Exhibition> exhibitionsList = storage.getAllExhibitions();
                return gson.toJson(exhibitionsList);
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