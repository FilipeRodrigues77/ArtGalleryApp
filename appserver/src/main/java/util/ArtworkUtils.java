package util;

public class ArtworkUtils {

    public static String mergeArtistAndArtwork(String artistName, String artworkTitle) {
        // Replace spaces with hyphens and concatenate
        String mergedString = (artistName + "-" + artworkTitle).replace(" ", "-");
        return mergedString.toLowerCase(); // Convert the result to lowercase
    }

}
