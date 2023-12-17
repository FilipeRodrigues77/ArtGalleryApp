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
 * The MainGetArtists class is responsible for retrieving information about artists
 * by making various types of GET requests to a specified endpoint using OkHttpClient.
 * It includes methods for getting all artists, getting an artist by ID, searching for
 * artists by name, nationality, birthdate, and deathdate.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainGetArtists {

    static final String PORT = "8010";

    // public static void main(String[] args) {}

    /**
     * Retrieves a list of all artists from the server.
     *
     * @return A list of Artist objects representing all artists.
     */
    public static List<Artist> getAllArtists (){

        List<Artist> listArtist = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT +"/artists")
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

    /**
     * Retrieves a specific artist by ID from the server.
     *
     * @param artistId The unique identifier of the artist.
     * @return An Artist object representing the artist with the specified ID.
     */
    public static Artist getArtistById (int artistId ) {

        Artist artist = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT +"/artists/"+artistId)
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

    /**
     * Searches for artists by name on the server.
     *
     * @param name The name to be used as a search parameter.
     * @return A list of Artist objects matching the specified name.
     */
    public static List<Artist> getArtistByName (String name) {
        List<Artist> listArtists = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:" + PORT +"/artists/searchName")).newBuilder();
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

    /**
     * Searches for artists by nationality on the server.
     *
     * @param nationality The nationality to be used as a search parameter.
     * @return A list of Artist objects matching the specified nationality.
     */
    public static List<Artist> getArtistByNationality (String nationality) {
        List<Artist> listArtists = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:" + PORT +"/artists/searchNationality")).newBuilder();
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

    /**
     * Searches for artists by birthdate on the server.
     *
     * @param birthdate The birthdate to be used as a search parameter.
     * @return A list of Artist objects matching the specified birthdate.
     */
    public static List<Artist> getArtistByBirthdate (String birthdate) {
        List<Artist> listArtists = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:" + PORT +"artists/searchBirthdate")).newBuilder();
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

    /**
     * Searches for artists by deathdate on the server.
     *
     * @param deathdate The deathdate to be used as a search parameter.
     * @return A list of Artist objects matching the specified deathdate.
     */
    public static List<Artist> getArtistByDeathdate (String deathdate) {
        List<Artist> listArtists = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:" + PORT +"/artists/searchDeathdate")).newBuilder();
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