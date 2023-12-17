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

    public static boolean isEndDateBeforeStartDate(LocalDate startDate, LocalDate endDate) {
        return endDate.isBefore(startDate);
    }

    public static boolean isEndDateBeforeToday(LocalDate endDate){
        LocalDate today = LocalDate.now();
        return endDate.isBefore(today);
    }
}
