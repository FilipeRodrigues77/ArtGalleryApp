package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import domain.Artwork;
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
 * The MainGetArtworks class is responsible for retrieving information about artworks
 * by making various types of GET requests to a specified endpoint using OkHttpClient.
 * It includes methods for getting all artworks, getting artworks by artist ID, category,
 * medium, name, date range, price range, gallery ID, and exhibition ID.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainGetArtworks {

    static final String PORT = "8010";

    // public static void main(String[] args) {}

    /**
     * Retrieves a list of all artworks from the server.
     *
     * @return A list of Artwork objects representing all artworks.
     */
    public static List<Artwork> getAllArtworks(){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/artworks")
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

    /**
     * Retrieves a list of artworks by a specific artist ID from the server.
     *
     * @param idArtist The unique identifier of the artist.
     * @return A list of Artwork objects representing artworks by the specified artist.
     */
    public static List<Artwork> getArtworksByArtistId (int idArtist){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT +"/artworks/searchByArtist?idArtist="+ idArtist)
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

    /**
     * Retrieves a list of artworks by a specific category from the server.
     *
     * @param category The category to be used as a search parameter.
     * @return A list of Artwork objects representing artworks in the specified category.
     */
    public static List<Artwork> getArtworksByCategory (String category) {

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        String url = "http://localhost:" + PORT + "/artworks/searchByCategory?category=" + category;

        Request getRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artworks
                Type listType = new TypeToken<ArrayList<Artwork>>() {}.getType();

                if (response.body() != null) {
                    listArtworks = gson.fromJson(response.body().string(), listType);
                }

            } else {
                // Something failed, maybe the artwork does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listArtworks;
    }

    /**
     * Retrieves a list of artworks by a specific medium from the server.
     *
     * @param medium The medium to be used as a search parameter.
     * @return A list of Artwork objects representing artworks with the specified medium.
     */
    public static List<Artwork> getArtworksByMedium (String medium){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/artworks/searchMedium?medium="+medium)
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

            } else {
                // Something failed, maybe the artwork does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listArtworks;

    }

    /**
     * Retrieves a list of artworks by a specific name from the server.
     *
     * @param name The name to be used as a search parameter.
     * @return A list of Artwork objects representing artworks with the specified name.
     */
    public static List<Artwork> getArtworkByName (String name) {
        List<Artwork> listArtworks = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        //Build the url and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:" + PORT +"/artworks/searchName")).newBuilder();
        urlBuilder.addQueryParameter("name", name);
        String url = urlBuilder.build().toString();

        Request getRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artworks
                Type listType = new TypeToken<ArrayList<Artwork>>() {}.getType();

                if (response.body() != null) {
                    listArtworks = gson.fromJson(response.body().string(), listType);
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

    /**
     * Retrieves a list of artworks within a specified date range from the server.
     *
     * @param startDate The start date of the range.
     * @param endDate   The end date of the range.
     * @return A list of Artwork objects representing artworks within the specified date range.
     */
    public static List<Artwork> getArtworksByDate (String startDate, String endDate) {

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        String url = "http://localhost:" + PORT +"/artworks/searchByDateRange?startDate=" + startDate + "&endDate=" + endDate;

        Request getRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artworks
                Type listType = new TypeToken<ArrayList<Artwork>>() {}.getType();

                if (response.body() != null) {
                    listArtworks = gson.fromJson(response.body().string(), listType);
                }

            } else {
                // Something failed, maybe the artwork does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listArtworks;
    }

    /**
     * Retrieves a list of artworks within a specified price range from the server.
     *
     * @param minPrice The minimum price of the range.
     * @param maxPrice The maximum price of the range.
     * @return A list of Artwork objects representing artworks within the specified price range.
     */
    public static List<Artwork> getArtworksByPrice (double minPrice, double maxPrice){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT +"/artworks/searchPriceRange?min="+minPrice+"&max="+maxPrice)
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

            } else {
                // Something failed, maybe the artwork does not exist
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listArtworks;

    }

    /**
     * Retrieves a list of artworks by a specific gallery ID from the server.
     *
     * @param idGallery The unique identifier of the gallery.
     * @return A list of Artwork objects representing artworks in the specified gallery.
     */
    public static List<Artwork> getArtworksByGalleryId(int idGallery) {
        List<Artwork> listArtworks = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT +"/artworks/searchByGallery?idGallery=" + idGallery)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artworks
                Type listType = new TypeToken<ArrayList<Artwork>>() {}.getType();

                if (response.body() != null) {
                    listArtworks = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe the artworks do not exist for the specified gallery
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listArtworks;
    }

    /**
     * Retrieves a list of artworks by a specific exhibition ID from the server.
     *
     * @param idExhibition The unique identifier of the exhibition.
     * @return A list of Artwork objects representing artworks in the specified exhibition.
     */
    public static List<Artwork> getArtworksByExhibitionId(int idExhibition) {
        List<Artwork> listArtworks = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + PORT + "/artworks/searchByExhibition?idExhibition=" + idExhibition)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code() + "\n");

            if (response.code() == 200) {
                // Deserialize a list of artworks
                Type listType = new TypeToken<ArrayList<Artwork>>() {}.getType();

                if (response.body() != null) {
                    listArtworks = gson.fromJson(response.body().string(), listType);
                }
            } else {
                // Something failed, maybe the artworks do not exist for the specified exhibition
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
