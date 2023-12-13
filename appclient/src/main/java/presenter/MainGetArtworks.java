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

public class MainGetArtworks {

    static String port = "8010";

    public static void main(String[] args) {
        System.out.println();
    }

    public static List<Artwork> getAllArtworks(){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artworks")
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

    public static List<Artwork> getArtworksByArtistId (int idArtist){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port +"/artworks/searchByArtist?idArtist="+ idArtist)
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

    public static List<Artwork> getArtworksByCategory (String category) {

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        String url = "http://localhost:" + port + "/artworks/searchByCategory?category=" + category;

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

    public static List<Artwork> getArtworksByMedium (String medium){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artworks/searchMedium?medium="+medium)
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

    public static List<Artwork> getArtworkByName (String name) {
        List<Artwork> listArtworks = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        //Build the url and add query parameter
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://localhost:" + port +"/artworks/searchName")).newBuilder();
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

    public static List<Artwork> getArtworksByDate (String startDate, String endDate) {

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        String url = "http://localhost:" + port +"/artworks/searchByDateRange?startDate=" + startDate + "&endDate=" + endDate;

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

    public static List<Artwork> getArtworksByPrice (double minPrice, double maxPrice){

        List<Artwork> listArtworks = null;
        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port +"h/artworks/searchPriceRange?min="+minPrice+"&max="+maxPrice)
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

    public static List<Artwork> getArtworksByGalleryId(int idGallery) {
        List<Artwork> listArtworks = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port +"h/artworks/searchByGallery?idGallery=" + idGallery)
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

    public static List<Artwork> getArtworksByExhibitionId(int idExhibition) {
        List<Artwork> listArtworks = null;

        // DESERIALIZATION
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        Request getRequest = new Request.Builder()
                .url("http://localhost:" + port + "/artworks/searchByExhibition?idExhibition=" + idExhibition)
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
