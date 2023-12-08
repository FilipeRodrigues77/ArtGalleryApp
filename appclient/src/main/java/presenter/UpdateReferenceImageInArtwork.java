package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artwork;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class UpdateReferenceImageInArtwork {

    public static void main(String[] args) {
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // We need the list of URL
        List<Artwork> listArtwork = MainGetAllArtworks.getAllArtworks();

        for (int i = 0; i < 85; i++) {
            int imageNum = i + 1;
            String path = "Images/Artwork/Square/square" + imageNum + ".jpg";

            // Sample data for the update
            Artwork updatedArtwork = listArtwork.get(i);

            updatedArtwork.setReferenceImage(path);
            int artworkIdToUpdate = imageNum;

            // Serialize the updated artwork
            String jsonRequestBody = gson.toJson(updatedArtwork);

            // Build the request
            Request putRequest = new Request.Builder()
                    .url("http://localhost:8000/artworks/" + artworkIdToUpdate)
                    .put(RequestBody.create(MediaType.parse("application/json"), jsonRequestBody))
                    .build();

            Response response = null;
            try {
                response = httpClient.newCall(putRequest).execute();
                System.out.println("Response code: " + response.code() + "\n");

                if (response.code() == 200) {
                    // Handle successful update response
                    System.out.println("Artwork updated successfully");
                } else {
                    // Handle failure response
                    if (response.body() != null) {
                        System.out.println(response.body().string());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null && response.body() != null) {
                    response.body().close(); // Close the response body
                }
            }
        }
    }

}
