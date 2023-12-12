package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class UpdateReferenceImageInArtist {

    public static void main(String[] args) {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // We need the list of URL
        List<Artist> listArtists = MainGetArtists.getAllArtists();

        for (int i = 0; i < 40; i++) {
            int artistNum = i + 1;
            String path = "Images/Artist/ArtistSquare/square" + artistNum + ".jpg";

            // Sample data for the update
            Artist updatedArtist = listArtists.get(i);
            updatedArtist.setReferenceImage(path);

            int artistIdToUpdate = artistNum;

            // Serialize the updated artist
            String jsonRequestBody = gson.toJson(updatedArtist);

            // Build the request
            Request putRequest = new Request.Builder()
                    .url("http://localhost:9000/artists/" + artistIdToUpdate)
                    .put(RequestBody.create(MediaType.parse("application/json"), jsonRequestBody))
                    .build();

            Response response = null;
            try {
                response = httpClient.newCall(putRequest).execute();
                System.out.println("Response code: " + response.code() + "\n");

                if (response.code() == 200) {
                    // Handle successful update response
                    System.out.println("Artist updated successfully");
                } else {

                    System.out.println(response.body().string());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null && response.body() != null) {
                    response.body().close();
                }
            }
        }
    }

}