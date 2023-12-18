package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Nationality;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code MainGetNationalities} class provides a method to retrieve a list of nationality names
 * from a remote service.
 *
 * This class uses the OkHttp library and Gson for deserialization to communicate with a web service
 * that exposes a list of nationalities. It then extracts the nationality names from the response
 * and returns them as a list of strings.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 */
public class MainGetNationalities {

    static final String PORT = "8020";

   //  public static void main(String[] args) {}

/**
 * Retrieves a list of nationality names from the remote service.
 *
 * @return a list (possibly empty) containing all nationality names
 * @implNote This method retrieves a list of nationalities then it extracts
 * the nationality names and returns them as a list of strings.
 */
    public static List<String> getAllNationalities() {

        List<Nationality> listNationalities = null;
        List<String> nationalityNames = new ArrayList<>();

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/nationalities")  // Update the endpoint for nationalities
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of nationalities
                Type listType = new TypeToken<ArrayList<Nationality>>(){}.getType();

                if (response.body() != null) {
                    listNationalities = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe the nationalities do not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }

        if (listNationalities != null) {
            for (Nationality str : listNationalities){
                nationalityNames.add(str.getNationality());
            }
        }
        return nationalityNames;
    }

}
