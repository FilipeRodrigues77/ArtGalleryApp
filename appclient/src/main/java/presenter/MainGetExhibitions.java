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
 * The MainGetExhibitions class is responsible for retrieving information about exhibitions
 * by making various types of GET requests to a specified endpoint using OkHttpClient.
 * It includes methods for getting all exhibitions, exhibitions by status, start date, end date,
 * name, and exhibitions associated with a specific gallery.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainGetExhibitions {

    static final String PORT = "8010";

    public static void main(String[] args) {
        System.out.println(getExhibitionsByIdGallery(2));
    }

    /**
     * Retrieves a list of all exhibitions from the server.
     *
     * @return A list of Exhibition objects representing all exhibitions.
     */
    public static List<Exhibition> getAllExhibitions() {

        List<Exhibition> listExhibitions = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url( "http://localhost:" + PORT +"/exhibitions")  // Update the URL to the endpoint for exhibitions
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

    /**
     * Retrieves a list of exhibitions by a specific status from the server.
     *
     * @param status The status to be used as a search parameter.
     * @return A list of Exhibition objects representing exhibitions with the specified status.
     */
    public static List<Exhibition> getExhibitionsByStatus(String status) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/exhibitions/searchByStatus?status=" + status)
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

    /**
     * Retrieves a list of exhibitions by a specific start date from the server.
     *
     * @param startDate The start date to be used as a search parameter.
     * @return A list of Exhibition objects representing exhibitions with the specified start date.
     */
    public static List<Exhibition> getExhibitionsByStartDate(String startDate) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/exhibitions/searchByStartDate?startDate=" + startDate)
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

    /**
     * Retrieves a list of exhibitions by a specific end date from the server.
     *
     * @param endDate The end date to be used as a search parameter.
     * @return A list of Exhibition objects representing exhibitions with the specified end date.
     */
    public static List<Exhibition> getExhibitionsByEndDate(String endDate) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/exhibitions/searchByEndDate?endDate=" + endDate)
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

    /**
     * Retrieves a list of exhibitions by a specific name from the server.
     *
     * @param name The name to be used as a search parameter.
     * @return A list of Exhibition objects representing exhibitions with the specified name.
     */
    public static List<Exhibition> getExhibitionsByName(String name) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/exhibitions/search?name=" + name)
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

    /**
     * Retrieves a list of exhibitions associated with a specific gallery ID from the server.
     *
     * @param idGallery The unique identifier of the gallery.
     * @return A list of Exhibition objects representing exhibitions associated with the specified gallery.
     */
    public static List<Exhibition> getExhibitionsByIdGallery(int idGallery) {
        List<Exhibition> listExhibitions = null;

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/exhibitions/searchByGallery?idGallery=" + idGallery)
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
