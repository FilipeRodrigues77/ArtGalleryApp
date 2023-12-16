package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import domain.Gallery;
import okhttp3.*;

import java.io.IOException;

/**
 * The MainUpdateGalleries class is responsible for updating a gallery by sending a PUT request
 * to a specified endpoint using OkHttpClient.
 * It includes a method for updating a specific gallery.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainUpdateGalleries {

    static String port = "8010";

    // public static void main(String[] args) {}

    /**
     * Updates the specified gallery by sending a PUT request to the server.
     *
     * @param selectedGallery The Gallery object representing the gallery to be updated.
     */
    public static void updateGallery(Gallery selectedGallery) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String galleryJson = gson.toJson(selectedGallery);

        Request putRequest = new Request.Builder()
                .url("http://localhost:" + port + "/galleries/" + selectedGallery.getId())
                .put(RequestBody.create(MediaType.parse("application/json"), galleryJson))
                .build();

        try {
            Response response = httpClient.newCall(putRequest).execute();
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