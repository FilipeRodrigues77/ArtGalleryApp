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

