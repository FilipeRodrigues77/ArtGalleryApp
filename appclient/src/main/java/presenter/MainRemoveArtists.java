package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import okhttp3.*;

import java.io.IOException;

/**
 * The MainRemoveArtists class is responsible for removing an artist by sending a DELETE request
 * to a specified endpoint using OkHttpClient. It includes a method for removing a specific artist.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainRemoveArtists {

    static final String PORT = "8020";

    // public static void main(String[] args) {}

    /**
     * Removes the specified artist by sending a DELETE request to the server.
     *
     * @param selectedArtist The Artist object representing the artist to be removed.
     */
    public static void removeArtist(Artist selectedArtist) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artistJson = gson.toJson(selectedArtist);

        Request deleteRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/artists/" + selectedArtist.getId())
                .delete(RequestBody.create(MediaType.parse("application/json"), artistJson))
                .build();

        try {
            Response response = httpClient.newCall(deleteRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                System.out.println("Artist removed successfully!");
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