package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Artist;
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
 * Class used to retrieve data about artists from the database and store it in a list of Artist object.
 */
public class MainGetArtists {

    public static List<Artist> getAllArtists (){

        List<Artist> listArtist = null;
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


                if (response.body() != null) {
                    listArtist = gson.fromJson(response.body().string(), listType);
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
        return listArtist;
    }

    public static Artist getArtistById (int artistId ) {

        Artist artist = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:8000/artists/"+artistId)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code()+"\n");
            // System.out.println("Response content type: " + response.header("content-type"));

            if(response.code() == 200) {
                // Deserialize a list of clients

                Type dataType = new TypeToken<Artist>(){}.getType();

                if (response.body() != null) {
                    artist = gson.fromJson(response.body().string(), dataType);
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

        return artist;
    }

    public static List<Artist> getArtistByName (String name) {
        List<Artist> listArtists = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:8000/artists/searchName")).newBuilder();
        urlBuilder.addQueryParameter("name", name);
        String url = urlBuilder.build().toString();

        Request getRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artists
                Type listType = new TypeToken<ArrayList<Artist>>() {}.getType();

                if (response.body() != null) {
                    listArtists = gson.fromJson(response.body().string(), listType);
                }

            } else {
                // Something failed, maybe the artist does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listArtists;
    }

    public static List<Artist> getArtistByNationality (String nationality) {
        List<Artist> listArtists = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:8000/artists/searchNationality")).newBuilder();
        urlBuilder.addQueryParameter("nationality", nationality);
        String url = urlBuilder.build().toString();

        Request getRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artists
                Type listType = new TypeToken<ArrayList<Artist>>() {}.getType();

                if (response.body() != null) {
                    listArtists = gson.fromJson(response.body().string(), listType);
                }

            } else {
                // Something failed, maybe the artist does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listArtists;
    }

    public static List<Artist> getArtistByBirthdate (String birthdate) {
        List<Artist> listArtists = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:8000/artists/searchBirthdate")).newBuilder();
        urlBuilder.addQueryParameter("birthdate", birthdate);
        String url = urlBuilder.build().toString();

        Request getRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artists
                Type listType = new TypeToken<ArrayList<Artist>>() {}.getType();

                if (response.body() != null) {
                    listArtists = gson.fromJson(response.body().string(), listType);
                }

            } else {
                // Something failed, maybe the artist does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listArtists;
    }

    public static List<Artist> getArtistByDeathdate (String deathdate) {
        List<Artist> listArtists = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:8000/artists/searchDeathdate")).newBuilder();
        urlBuilder.addQueryParameter("deathdate", deathdate);
        String url = urlBuilder.build().toString();

        Request getRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artists
                Type listType = new TypeToken<ArrayList<Artist>>() {}.getType();

                if (response.body() != null) {
                    listArtists = gson.fromJson(response.body().string(), listType);
                }

            } else {
                // Something failed, maybe the artist does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listArtists;
    }


}