package services;

import domain.Artwork;
import java.util.List;

public interface ArtworkService {
    /**
     * Retrieves all artworks from the database.
     *
     * @return a list (possibly empty) containing all artworks
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getAllArtworks() throws ServiceException;

    /**
     * Retrieves an artwork with the specified id from storage.
     *
     * @param id the artwork's id
     * @return the artwork; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Artwork getArtworkByID(int id) throws ServiceException;

    /**
     * Retrieves artworks with the specified title from storage.
     *
     * @param name the title of the artworks
     * @return List of artworks; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtworkByName(String name) throws ServiceException;

    /**
     * Retrieves artworks based on the specified medium from storage.
     *
     * @param medium the medium to filter artworks
     * @return List of artworks; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtworksByMedium(String medium) throws ServiceException;

    /**
     * Retrieves artworks based on the specified category from storage.
     *
     * @param category the category to filter artworks
     * @return List of artworks; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtworksByCategory(String category) throws ServiceException;

    /**
     * Retrieves artworks based on the specified date from storage.
     *
     * @param date the date to filter artworks
     * @return List of artworks; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtworksByDate(String date) throws ServiceException;

    /**
     * Retrieves artworks based on the specified artist ID from storage.
     *
     * @param artistId the ID of the artist to filter artworks
     * @return List of artworks; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtworksByArtist(int artistId) throws ServiceException;

    /**
     * Retrieves artworks based on the specified exhibition ID from storage.
     *
     * @param exhibitionId the ID of the exhibition to filter artworks
     * @return List of artworks; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtworksByExhibition(int exhibitionId) throws ServiceException;

    /**
     * Retrieves artists based on the specified gallery ID from storage.
     *
     * @param galleryId the ID of the gallery to filter artists
     * @return List of artists; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtistsByGallery(int galleryId) throws ServiceException;

    /**
     * Retrieves artworks based on the specified price range from storage.
     *
     * @param minPrice the minimum price of the range
     * @param maxPrice the maximum price of the range
     * @return List of artworks; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtworksByPriceRange(double minPrice, double maxPrice) throws ServiceException;

    /**
     * Retrieves artworks based on the specified date range from storage.
     *
     * @param startDate the start date of the range (in String format)
     * @param endDate   the end date of the range (in String format)
     * @return List of artworks; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artwork> getArtworksByDateRange(String startDate, String endDate) throws ServiceException;


    /**
     * Creates an artwork and persists its information in storage.
     * If Artwork id attribute is ignored by this method if it is set.
     *
     * @return an instance of the created artwork with assigned Id.
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Artwork createArtwork(Artwork newArtwork) throws ServiceException;

    /**
     * Updates the information of the specified artwork and persists its information in storage.
     * The artwork must have its Id set.
     *
     * @return an instance of the updated artwork; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Artwork updateArtwork(Artwork existingArtwork) throws ServiceException;

    /**
     * Deletes an artwork, given its Id.
     *
     * @param id the artwork id
     * @return the deleted artwork; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Artwork deleteArtwork(int id) throws ServiceException;
}

