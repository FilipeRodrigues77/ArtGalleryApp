
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
public class MainGetAllGalleries {
    public static void main(String[] args) {

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:8000/galleries")  // Update the URL to the endpoint for galleries
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of galleries
                Type listType = new TypeToken<ArrayList<Gallery>>(){}.getType();

                List<Gallery> listGalleries = null;
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
    }

}
