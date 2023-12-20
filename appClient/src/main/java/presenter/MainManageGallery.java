package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Gallery;
import okhttp3.*;

import java.io.IOException;

/**
 * Class used to retrieve data about galleries from the database and store it in a list of Gallery object.
 */
public class MainManageGallery {
    static String port = "8020";

    public static void main(String[] args) {}

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