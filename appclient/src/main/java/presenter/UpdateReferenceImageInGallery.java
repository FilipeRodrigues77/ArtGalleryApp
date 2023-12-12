package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Gallery;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class UpdateReferenceImageInGallery {

    public static void main(String[] args) {
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // We need the list of URLs
        List<Gallery> listGalleries = MainGetGalleries.getAllGalleries();

        for (int i = 0; i < listGalleries.size(); i++) {
            int galleryNum = i + 1;
            String path = "Images/Gallery/Gallery" + galleryNum + ".jpg";

            // Sample data for the update
            Gallery updatedGallery = listGalleries.get(i);
            updatedGallery.setReferenceImage(path);

            // Serialize the updated gallery
            String jsonRequestBody = gson.toJson(updatedGallery);

            // Build the request
            Request putRequest = new Request.Builder()
                    .url("http://localhost:9000/galleries/" + galleryNum)
                    .put(RequestBody.create(MediaType.parse("application/json"), jsonRequestBody))
                    .build();

            Response response = null;
            try {
                response = httpClient.newCall(putRequest).execute();
                System.out.println("Response code: " + response.code() + "\n");

                if (response.code() == 200) {
                    // Handle successful update response
                    System.out.println("Gallery updated successfully");
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
