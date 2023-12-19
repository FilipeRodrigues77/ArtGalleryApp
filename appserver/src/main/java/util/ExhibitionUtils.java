package util;

import java.time.LocalDate;

/**
 * The {@code ExhibitionUtils} class provides utility methods for working with exhibition-related
 * information. It contains static methods to check whether an end date is before the start date
 * and whether an end date is before the current date.
 * <p>
 * This class is designed to facilitate common operations related to exhibitions in the Iuvenis
 * Art application.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version 1.0
 *
 */
public class ExhibitionUtils {

    /**
     * This method is used to verify if the start date is before the end date.
     * The method receives two parameters, start date and end date and compares then using the method
     * isBefore.
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isEndDateBeforeStartDate(LocalDate startDate, LocalDate endDate) {
        return endDate.isBefore(startDate);
    }

    /***
     * This method is used to check the end date, when valid, and verify if the end date is before today's date.
     * The result will be used to decide if the exhibition is closed or not.
     * @param endDate
     * @return
     */
    public static boolean isEndDateBeforeToday(LocalDate endDate){
        LocalDate today = LocalDate.now();
        return endDate.isBefore(today);
    }
}
