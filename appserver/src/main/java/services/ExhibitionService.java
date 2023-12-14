package services;

import domain.Exhibition;

import java.util.List;

public interface ExhibitionService {

    /**
     * Retrieves all exhibitions from storage.
     *
     * @return a list (possibly empty) containing all exhibitions
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Exhibition> getAllExhibitions() throws ServiceException;

    /**
     * Retrieves an exhibition with the specified id from storage.
     *
     * @param id the exhibition's id
     * @return the exhibition; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Exhibition getExhibitionByID(int id) throws ServiceException;

    /**
     * Retrieves exhibitions with the specified name from storage.
     *
     * @param name the name of the exhibitions
     * @return List of exhibitions; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Exhibition> getExhibitionByName(String name) throws ServiceException;

    /**
     * Retrieves exhibitions with the specified status from storage.
     *
     * @param status the status of the exhibitions
     * @return List of exhibitions; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Exhibition> getExhibitionByStatus(String status) throws ServiceException;

    /**
     * Retrieves exhibitions with the specified start date from storage.
     *
     * @param startDate the start date of the exhibitions in 'yyyy-MM-dd' format
     * @return List of exhibitions; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Exhibition> getExhibitionByStartDate(String startDate) throws ServiceException;

    /**
     * Retrieves exhibitions with the specified end date from storage.
     *
     * @param endDate the end date of the exhibitions
     * @return List of exhibitions; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Exhibition> getExhibitionByEndDate(String endDate) throws ServiceException;


    /**
     * Retrieves exhibitions based on the specified gallery ID from storage.
     *
     * @param galleryId the ID of the gallery to filter exhibitions
     * @return List of exhibitions; an empty list if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Exhibition> getExhibitionsByGallery(int galleryId) throws ServiceException;

    /**
     * Creates an exhibition and persists its information in storage.
     * If Exhibition id attribute is ignored by this method if it is set.
     *
     * @return an instance of the created exhibition with assigned Id.
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Exhibition createExhibition(Exhibition newExhibition) throws ServiceException;

    /**
     * Updates the information of the specified exhibition and persists its information in storage.
     * The exhibition must have its Id set.
     *
     * @return an instance of the updated exhibition; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Exhibition updateExhibition(Exhibition existingExhibition) throws ServiceException;

    /**
     * Deletes an exhibition, given its Id.
     *
     * @param id the exhibition id
     * @return the deleted exhibition; <i>null</i> if not found
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    Exhibition deleteExhibition(int id) throws ServiceException;
}

