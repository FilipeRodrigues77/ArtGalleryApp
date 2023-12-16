package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import domain.Gallery;
import okhttp3.*;

import java.io.IOException;


/**
 * The MainCreateGallery class is responsible for adding a new Gallery
 * by making a POST request to a specified endpoint using OkHttpClient.
 * It includes a method {@code addGallery} for this purpose.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainCreateGallery {

    /**
     * The default port used for communication with the server.
     */
    static String port = "8010";

    /**
     * Adds a new Gallery by sending a POST request to the server.
     *
     * @param newGallery The Gallery object to be added.
     */
    public static void addGallery(Gallery newGallery) {
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String galleryJson = gson.toJson(newGallery);

        Request postRequest = new Request.Builder()
                .url("http://localhost:" + port + "/galleries")
                .post(RequestBody.create(MediaType.parse("application/json"), galleryJson))
                .build();

        try {
            Response response = httpClient.newCall(postRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 201) {
                System.out.println("Gallery added successfully!");
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

