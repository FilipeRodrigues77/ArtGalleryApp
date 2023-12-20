package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import okhttp3.*;

import java.io.IOException;

/**
 * Class used to retrieve data about artists from the database and store it in a list of Artist object.
 */
public class MainManageArtists {
    static String port = "8020";

    public static void main(String[] args) {}

    public static void addArtist(Artist newArtist) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artistJson = gson.toJson(newArtist);

        Request postRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artists")
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
    public static void removeArtist(Artist selectedArtist) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        String artistJson = gson.toJson(selectedArtist);

        Request deleteRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artists/" + selectedArtist.getId())
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