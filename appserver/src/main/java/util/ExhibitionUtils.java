package util;

import java.time.LocalDate;

public class ExhibitionUtils {

    public static boolean isEndDateBeforeStartDate(LocalDate startDate, LocalDate endDate) {
        return endDate.isBefore(startDate);
    }

    public static boolean isEndDateBeforeToday(LocalDate endDate){
        LocalDate today = LocalDate.now();
        return endDate.isBefore(today);
    }
}
