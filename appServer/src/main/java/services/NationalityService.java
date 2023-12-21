package services;

import domain.Nationality;

import java.util.List;

/**
 * Interface for accessing nationalities in the storage.
 */
public interface NationalityService {

    /**
     * Retrieves all nationalities from storage.
     *
     * @return a list (possibly empty) containing all nationalities
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Nationality> getNationalities() throws ServiceException;

}
