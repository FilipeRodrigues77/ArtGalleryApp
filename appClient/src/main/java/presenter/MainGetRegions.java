package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Nationality;
import domain.Region;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MainGetRegions} class provides a method to retrieve a list of regions names
 * from a remote service.
 * This class uses the OkHttp library and Gson for deserialization to communicate with a web service
 * that exposes a list of regions. It then extracts the nationality names from the response
 * and returns them as a list of strings.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 *
 */
public class MainGetRegions {

    public static void main(String[] args) {
        System.out.println(getAllRegions());
    }
    static final String PORT = "8020";

    /**
     * Retrieves a list of region names from the remote service.
     *
     * @return a list (possibly empty) containing all region names
     * @implNote This method retrieves a list of regions then it extracts
     * the region names and returns them as a list of strings.
     */
    public static List<String> getAllRegions() {

        List<Region> listRegions = null;
        List<String> regionNames = new ArrayList<>();

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/regions")
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {

                Type listType = new TypeToken<ArrayList<Region>>(){}.getType();

                if (response.body() != null) {
                    listRegions = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe the regions do not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (listRegions != null) {
            for (Region region : listRegions) {
                regionNames.add(region.getRegion());
            }
        }
        return regionNames;
    }

}
