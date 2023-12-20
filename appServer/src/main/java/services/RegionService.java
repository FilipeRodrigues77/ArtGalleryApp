package services;

import domain.Nationality;
import domain.Region;

import java.util.List;

public interface RegionService {

    /**
     * Retrieves all regions from storage.
     *
     * @return a list (possibly empty) containing all regions
     * @throws ServiceException if some exception occurs in the underlying storage
     */
    List<Region> getRegions() throws ServiceException;
}
