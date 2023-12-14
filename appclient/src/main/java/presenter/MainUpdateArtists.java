package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import okhttp3.*;

import java.io.IOException;

/**
 * Class used to retrieve data about artists from the database and store it in a list of Artist object.
 */
public class MainUpdateArtists {
    static String port = "8010";

    public static void main(String[] args) {

    }

    public static void updateArtist(Artist selectedArtist) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artistJson = gson.toJson(selectedArtist);

        Request putRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artists/" + selectedArtist.getId())
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