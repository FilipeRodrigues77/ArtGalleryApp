package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;
import domain.Gallery;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The MainGetGalleries class is responsible for retrieving information about galleries
 * by making various types of GET requests to a specified endpoint using OkHttpClient.
 * It includes methods for getting all galleries, a specific gallery by ID, galleries by region,
 * and galleries by name.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainGetGalleries {

    static final String PORT = "8010";

    /**
     * Retrieves a list of all galleries from the server.
     *
     * @return A list of Gallery objects representing all galleries.
     */
    public static List<Gallery> getAllGalleries () {

        List<Gallery> listGalleries = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT +"/galleries")  // Update the URL to the endpoint for galleries
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of galleries
                Type listType = new TypeToken<ArrayList<Gallery>>(){}.getType();


                if (response.body() != null) {
                    listGalleries = gson.fromJson(response.body().string(), listType);
                }
                if (listGalleries != null) {
                    for (Gallery gallery : listGalleries) {
                        //System.out.println(gallery);
                    }
                }
            } else {
                // Something failed, maybe the gallery does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listGalleries;
    }

    /**
     * Retrieves information about a specific gallery based on its ID.
     *
     * @param galleryId The unique identifier of the gallery.
     * @return A Gallery object representing the specified gallery.
     */
    public static Gallery getGalleryById(int galleryId ) {

        Gallery gallery = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT +"/galleries/"+galleryId)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code()+"\n");
            // System.out.println("Response content type: " + response.header("content-type"));

            if(response.code() == 200) {
                // Deserialize a list of clients

                Type dataType = new TypeToken<Gallery>(){}.getType();

                if (response.body() != null) {
                    gallery = gson.fromJson(response.body().string(), dataType);
                }

            } else {
                // Something failed, maybe the gallery does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }

        return gallery;
    }

    /**
     * Retrieves a list of galleries based on a specified region.
     *
     * @param region The region to be used as a search parameter.
     * @return A list of Gallery objects representing galleries in the specified region.
     */
    public static List<Gallery> getGalleriesByRegion(String region) {
        List<Gallery> listGalleries = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT +"/galleries/searchByRegion?region=" + region)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of galleries
                Type listType = new TypeToken<ArrayList<Gallery>>() {}.getType();

                if (response.body() != null) {
                    listGalleries = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe the galleries do not exist for the specified region
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listGalleries;
    }

    /**
     * Retrieves a list of galleries based on a specified name.
     *
     * @param name The name to be used as a search parameter.
     * @return A list of Gallery objects representing galleries with the specified name.
     */
    public static List<Gallery> getGalleryByName(String name) {
        List<Gallery> listGalleries = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add the query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:" + PORT + "/galleries/search")).newBuilder();
        urlBuilder.addQueryParameter("name", name);
        String url = urlBuilder.build().toString();

        Request getRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of galleries
                Type listType = new TypeToken<ArrayList<Gallery>>() {}.getType();

                if (response.body() != null) {
                    listGalleries = gson.fromJson(response.body().string(), listType);
                }

            } else {
                // Something failed, maybe the gallery does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listGalleries;
    }
}
