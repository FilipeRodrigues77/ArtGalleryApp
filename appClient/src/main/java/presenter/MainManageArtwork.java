package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artwork;
import okhttp3.*;

import java.io.IOException;

/**
 * Class used to retrieve data about artwork from the database and store it in a list of Artwork object.
 */
public class MainManageArtwork {
    static String port = "8020";

    public static void main(String[] args) {

        Artwork test = new Artwork();
        test.setName("test2");

        test.setIdArtist(48);
        test.setIdGallery(3);
        test.setReferenceImage("Images/Artwork/AllArtworks/{imageVersion}85.jpg");
        addArtwork(test);
        // System.out.println(test);
    }

    public static void addArtwork(Artwork newArtwork) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artworkJson = gson.toJson(newArtwork);
        System.out.println(artworkJson);

        Request postRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artworks")
                .post(RequestBody.create(MediaType.parse("application/json"), artworkJson))
                .build();


        try {
            Response response = httpClient.newCall(postRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 201) {
                System.out.println("Artwork added successfully!");
            } else {
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void updateArtwork(Artwork selectedArtwork) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artworkJson = gson.toJson(selectedArtwork);

        Request putRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artworks/" + selectedArtwork.getId())
                .put(RequestBody.create(MediaType.parse("application/json"), artworkJson))
                .build();

        try {
            Response response = httpClient.newCall(putRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 201) {
                System.out.println("Artwork added successfully!");
            } else {

                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void removeArtwork(Artwork selectedArtwork) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artworkJson = gson.toJson(selectedArtwork);

        Request deleteRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artworks/" + selectedArtwork.getId())
                .delete(RequestBody.create(MediaType.parse("application/json"), artworkJson))
                .build();

        try {
            Response response = httpClient.newCall(deleteRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                System.out.println("Artwork removed successfully!");
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