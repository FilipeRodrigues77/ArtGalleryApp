package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import domain.Gallery;
import okhttp3.*;

import java.io.IOException;

/**
 * The MainRemoveGallery class is responsible for removing a gallery by sending a DELETE request
 * to a specified endpoint using OkHttpClient. It includes a method for removing a specific gallery.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainRemoveGallery {

    static String port = "8010";
    // public static void main(String[] args) {}
    /**
     * Removes the specified gallery by sending a DELETE request to the server.
     *
     * @param selectedGallery The Gallery object representing the gallery to be removed.
     */
    public static void removeGallery(Gallery selectedGallery) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String galleryJson = gson.toJson(selectedGallery);

        Request deleteRequest = new Request.Builder()
                .url("http://localhost:" + port + "/galleries/" + selectedGallery.getId())
                .delete(RequestBody.create(MediaType.parse("application/json"), galleryJson))
                .build();

        try {
            Response response = httpClient.newCall(deleteRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                System.out.println("Gallery removed successfully!");
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