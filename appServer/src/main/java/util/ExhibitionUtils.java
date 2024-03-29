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
     * Checks whether the specified end date is before the start date.
     *
     * @param startDate The start date of the exhibition.
     * @param endDate   The end date of the exhibition.
     * @return {@code true} if the end date is before the start date, {@code false} otherwise.
     */
    public static boolean isEndDateBeforeStartDate(LocalDate startDate, LocalDate endDate) {
        return endDate.isBefore(startDate);
    }


    /**
     * Checks whether the specified end date is before the current date.
     *
     * @param endDate The end date of the exhibition.
     * @return {@code true} if the end date is before the current date, {@code false} otherwise.
     */
    public static boolean isEndDateBeforeToday(LocalDate endDate){
        LocalDate today = LocalDate.now();
        return endDate.isBefore(today);
    }
}
