package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import domain.Artwork;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

public class MainGetAllArtworks {
    public static void main(String[] args) {

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:8000/artworks")
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code()+"\n");


            if (response.code() == 200) {
                // Deserialize a list of artworks
                Type listType = new TypeToken<ArrayList<Artwork>>(){}.getType();

                List<Artwork> listArtworks = null;
                if (response.body() != null) {
                    listArtworks = gson.fromJson(response.body().string(), listType);
                }
                if (listArtworks != null) {
                    for (Artwork artwork : listArtworks) {
                        System.out.println(artwork);
                    }
                }
            } else {
                // Something failed, maybe the artwork does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Artwork> getAllArtworks(){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:8000/artworks")
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code()+"\n");


            if (response.code() == 200) {
                // Deserialize a list of artworks
                Type listType = new TypeToken<ArrayList<Artwork>>(){}.getType();

                if (response.body() != null) {
                    listArtworks = gson.fromJson(response.body().string(), listType);
                }
                if (listArtworks != null) {

                }
            } else {
                // Something failed, maybe the artwork does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listArtworks;

    }

}
