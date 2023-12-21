package services;

import domain.Artist;
import java.util.List;

/**...
 * Specifies the CRUD operations for an Artist
 */
import java.util.List;

/**
 * Interface for accessing and managing artists in the storage.
 */
public interface ArtistService {
    /**
     * Retrieves all artists from storage.
     *
     * @return a list (possibly empty) containing all artists
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artist> getAllArtists() throws ServiceException;

    /**
     * Retrieves an artist with the specified id from storage.
     *
     * @param name the artist's id
     * @return Artist list; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artist> getArtistByName(String name) throws ServiceException;

    /**
     * Retrieves artists with the specified nationality from storage.
     *
     * @param nationality the nationality of the artists
     * @return List of artists; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artist> getArtistsByNationality(String nationality) throws ServiceException;

    /**
     * Retrieves artists based on the specified date (birthdate or deathdate) from storage.
     *
     * @param date the date to filter artists
     * @param isBirthdate true if filtering by birthdate, false if filtering by deathdate
     * @return List of artists; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Artist> getArtistsByDate(String date, boolean isBirthdate) throws ServiceException;


    /**
     * Retrieves an artist with the specified id from storage.
     *
     * @param id the artist's id
     * @return the artist; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Artist getArtistByID(int id) throws ServiceException;

    /**
     * Creates an artist and persists its information in storage.
     * If Artist.id attribute is ignored by this method, if it is set.
     *
     * @return an instance of the created artist with assigned Id
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Artist createArtist(Artist newArtist) throws ServiceException;

    /**
     * Updates the information of the specified artist and persists its information in storage.
     * The artist must have its Id set.
     *
     * @return an instance of the updated artist; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Artist updateArtist(Artist existingArtist) throws ServiceException;

    /**
     * Deletes an artist, given its Id.
     *
     * @param id the artist id
     * @return the deleted artist; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Artist deleteArtist(int id) throws ServiceException;
}
