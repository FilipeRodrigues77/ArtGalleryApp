package presenter;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Exhibition;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to retrieve data about exhibitions from the database and store it in a list of Exhibitions object.
 */
public class MainGetExhibitions {
    static String port = "8010";

    public static void main(String[] args) {
        System.out.println(getExhibitionsByIdGallery(2));
    }

    public static List<Exhibition> getAllExhibitions() {

        List<Exhibition> listExhibitions = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url( "http://localhost:" + port +"/exhibitions")  // Update the URL to the endpoint for exhibitions
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of exhibitions
                Type listType = new TypeToken<ArrayList<Exhibition>>() {}.getType();

                if (response.body() != null) {
                    listExhibitions = gson.fromJson(response.body().string(), listType);
                }

            } else {
                // Something failed, maybe the exhibition does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listExhibitions;
    }

    public static List<Exhibition> getExhibitionsByStatus(String status) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port + "/exhibitions/searchByStatus?status=" + status)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of exhibitions
                Type listType = new TypeToken<ArrayList<Exhibition>>() {}.getType();

                if (response.body() != null) {
                    listExhibitions = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe no exhibitions found for the given status
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listExhibitions;
    }

    public static List<Exhibition> getExhibitionsByStartDate(String startDate) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port + "/exhibitions/searchByStartDate?startDate=" + startDate)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of exhibitions
                Type listType = new TypeToken<ArrayList<Exhibition>>() {}.getType();

                if (response.body() != null) {
                    listExhibitions = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe no exhibitions found for the given start date
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listExhibitions;
    }

    public static List<Exhibition> getExhibitionsByEndDate(String endDate) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port + "/exhibitions/searchByEndDate?endDate=" + endDate)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of exhibitions
                Type listType = new TypeToken<ArrayList<Exhibition>>() {}.getType();

                if (response.body() != null) {
                    listExhibitions = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe no exhibitions found for the given end date
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listExhibitions;
    }

    public static List<Exhibition> getExhibitionsByName(String name) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port + "/exhibitions/search?name=" + name)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of exhibitions
                Type listType = new TypeToken<ArrayList<Exhibition>>() {}.getType();

                if (response.body() != null) {
                    listExhibitions = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe no exhibitions found for the given name
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listExhibitions;
    }

    public static List<Exhibition> getExhibitionsByIdGallery(int idGallery) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port + "/exhibitions/searchByGallery?idGallery=" + idGallery)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of exhibitions
                Type listType = new TypeToken<ArrayList<Exhibition>>() {}.getType();

                if (response.body() != null) {
                    listExhibitions = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe no exhibitions found for the given idGallery
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listExhibitions;
    }



}
