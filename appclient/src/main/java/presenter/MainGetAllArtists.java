package presenter;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Artist;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to retrieve data about artists from the database and store it in a list of Artist object.
 */
public class MainGetAllArtists {
    public static void main(String[] args) {

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:8000/artists")
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code()+"\n");
            // System.out.println("Response content type: " + response.header("content-type"));

            if(response.code() == 200) {
                // Deserialize a list of clients
                Type listType = new TypeToken<ArrayList<Artist>>(){}.getType();

                List<Artist> listArtist = null;
                if (response.body() != null) {
                    listArtist = gson.fromJson(response.body().string(), listType);
                }
                if (listArtist != null) {
                    for (Artist artist : listArtist) {
                        System.out.println(artist);
                    }
                }
            } else {
                // Something failed, maybe the artist does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }
}
