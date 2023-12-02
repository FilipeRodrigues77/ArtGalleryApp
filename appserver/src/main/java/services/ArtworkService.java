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

