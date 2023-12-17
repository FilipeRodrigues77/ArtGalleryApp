package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import okhttp3.*;

import java.io.IOException;

/**
 * The MainUpdateArtists class is responsible for updating an artist by sending a PUT request
 * to a specified endpoint using OkHttpClient. It includes a method for updating a specific artist.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainUpdateArtists {

    static final String PORT = "8010";

    // public static void main(String[] args) {}

    /**
     * Updates the specified artist by sending a PUT request to the server.
     *
     * @param selectedArtist The Artist object representing the artist to be updated.
     */
    public static void updateArtist(Artist selectedArtist) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artistJson = gson.toJson(selectedArtist);

        Request putRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/artists/" + selectedArtist.getId())
                .put(RequestBody.create(MediaType.parse("application/json"), artistJson))
                .build();

        try {
            Response response = httpClient.newCall(putRequest).execute();
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