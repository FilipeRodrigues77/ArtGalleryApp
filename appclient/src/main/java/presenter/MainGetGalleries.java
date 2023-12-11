package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;
import domain.Gallery;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Class used to retrieve data about galleries from the database and store it in a list of Gallery object.
 */
public class MainGetGalleries {

    public static List<Gallery> getAllGalleries (String[] args) {

        List<Gallery> listGalleries = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:4567/galleries")  // Update the URL to the endpoint for galleries
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
                        System.out.println(gallery);
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

    public static Gallery getGalleryById(int galleryId ) {

        Gallery gallery = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:4567/galleries/"+galleryId)
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

}
