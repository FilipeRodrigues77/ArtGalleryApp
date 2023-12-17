package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import okhttp3.*;

import java.io.IOException;

/**
 * MainCreateArtists is a class responsible for adding new artists to a server.
 * It uses the OkHttpClient library to perform HTTP POST requests and Gson for
 * JSON serialization. The server endpoint is specified as "http://localhost:port/artists",
 * where 'port' is a configurable static variable.
 *
 * @author Nuely Furtado & Filipe Alves
 * @version v1.0
 */
public class MainCreateArtists {

    /**
     * The default port to be used in the server endpoint URL.
     */
    static final String PORT = "8010";

    /**
     * Adds a new artist to the server by sending an HTTP POST request.
     *
     * @param newArtist The Artist object representing the new artist to be added.
     */
    public static void addArtist(Artist newArtist) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artistJson = gson.toJson(newArtist);

        Request postRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/artists")
                .post(RequestBody.create(MediaType.parse("application/json"), artistJson))
                .build();

        try {
            Response response = httpClient.newCall(postRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 201) {
                System.out.println("Artist added successfully!");
            } else {
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


