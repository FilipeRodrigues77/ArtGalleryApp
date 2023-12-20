package util;

/**
 * The {@code ArtworkUtils} class provides utility methods for working with artwork-related
 * information. It contains a static method, {@code mergeArtistAndArtwork}, that combines
 * an artist's name and artwork title, replaces spaces with hyphens, and returns the merged
 * string in lowercase.
 * <p>
 * This class is designed to facilitate the manipulation of strings related to artists and
 * artworks in the Iuvenis Art application.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version 1.0
 */
public class ArtworkUtils {

    /**
     * Combines an artist's name and artwork title, replaces spaces with hyphens, and
     * returns the merged string in lowercase.
     *
     * @param artistName    The name of the artist.
     * @param artworkTitle  The title of the artwork.
     * @return              The merged string with spaces replaced by hyphens and
     *                      converted to lowercase.
     */
    public static String mergeArtistAndArtwork(String artistName, String artworkTitle) {
        // Replace spaces with hyphens and concatenate
        String mergedString = (artistName + "-" + artworkTitle).replace(" ", "-");
        return mergedString.toLowerCase(); // Convert the result to lowercase
    }

}
