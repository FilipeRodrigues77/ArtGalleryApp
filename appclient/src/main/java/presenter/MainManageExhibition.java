package presenter;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Exhibition;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Class used to retrieve data about exhibitions from the database and store it in a list of Exhibition object.
 */
public class MainManageExhibition {
    static String port = "8020";

    public static void main(String[] args) {}

    public static void addExhibition(Exhibition newExhibition) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String exhibitionJson = gson.toJson(newExhibition);

        Request postRequest = new Request.Builder()
                .url("http://localhost:" + port + "/exhibitions")
                .post(RequestBody.create(MediaType.parse("application/json"), exhibitionJson))
                .build();

        try {
            Response response = httpClient.newCall(postRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 201) {
                System.out.println("Exhibition added successfully!");
            } else {

                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void updateExhibition(Exhibition selectedExhibition) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String exhibitionJson = gson.toJson(selectedExhibition);

        Request putRequest = new Request.Builder()
                .url("http://localhost:" + port + "/exhibitions/" + selectedExhibition.getId())
                .put(RequestBody.create(MediaType.parse("application/json"), exhibitionJson))
                .build();

        try {
            Response response = httpClient.newCall(putRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 201) {
                System.out.println("Exhibition added successfully!");
            } else {

                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void removeExhibition(Exhibition selectedExhibition) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String exhibitionJson = gson.toJson(selectedExhibition);

        Request deleteRequest = new Request.Builder()
                .url("http://localhost:" + port + "/exhibitions/" + selectedExhibition.getId())
                .delete(RequestBody.create(MediaType.parse("application/json"), exhibitionJson))
                .build();

        try {
            Response response = httpClient.newCall(deleteRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                System.out.println("Exhibition removed successfully!");
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