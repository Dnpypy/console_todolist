package time;

import java.time.LocalDate;

public interface Calendar {

    void printCalendarOnMonth(int year, int month, LocalDate currDay);
    String onCenter(String s, int length, char ch);
    String onCenter(String s, int length);
    String[] getNameMonths();
    String[] getNameWeeks();
}
