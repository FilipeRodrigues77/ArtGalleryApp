package services;

import domain.Gallery;

import java.util.List;

/**
 * Interface for accessing and managing galleries in the storage.
 */
public interface GalleryService {

    /**
     * Retrieves all galleries from storage.
     *
     * @return a list (possibly empty) containing all galleries
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Gallery> getAllGalleries() throws ServiceException;

    /**
     * Retrieves a gallery with the specified id from storage.
     *
     * @param id the gallery's id
     * @return the gallery; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Gallery getGalleryByID(int id) throws ServiceException;

    /**
     * Retrieves galleries with the specified name from storage.
     *
     * @param name the name of the galleries
     * @return List of galleries; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Gallery> getGalleryByName(String name) throws ServiceException;

    /**
     * Creates a gallery and persists its information in storage.
     * If Gallery id attribute is ignored by this method if it is set.
     *
     * @return an instance of the created gallery with assigned Id.
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Gallery createGallery(Gallery newGallery) throws ServiceException;

    /**
     * Updates the information of the specified gallery and persists its information in storage.
     * The gallery must have its Id set.
     *
     * @return an instance of the updated gallery; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Gallery updateGallery(Gallery existingGallery) throws ServiceException;

    /**
     * Deletes a gallery, given its Id.
     *
     * @param id the gallery id
     * @return the deleted gallery; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Gallery deleteGallery(int id) throws ServiceException;
}
