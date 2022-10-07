package time.impls;

import time.Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public class CalendarImpl implements Calendar {

    public static final String ANSI_GREEN = "\u001B[32m";
    // ===== очищение цвета =====
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001B[31m";


    @Override
    public void printCalendarOnMonth(int year, int month, LocalDate currDay) {
        if (currDay == null || month < 1 || month > 12)
            return;

        LocalDate d = LocalDate.of(year, month, 1);
        // Выводим название месяца и год
        String[] nameMonths = getNameMonths();
        System.out.println(onCenter(nameMonths[d.getMonthValue() - 1], 28));
        // Выводим названия дней недели
        String[] nameWeek = getNameWeeks();
        for (int i = 0; i < nameWeek.length; i++) {
            System.out.print(ANSI_PURPLE + onCenter(nameWeek[i], 4));
        }
        System.out.print(ANSI_RESET);
        // Формируем отступ для первой строки
        int indent = 0;
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        LocalDate d2 = d.withDayOfMonth(1);
        DayOfWeek currDayOfWeek = d2.getDayOfWeek();
        while (firstDayOfWeek != currDayOfWeek) {
            indent++;
            d2 = d2.minusDays(1);
            currDayOfWeek = d2.getDayOfWeek();
        }
        if (indent != 0) System.out.println();
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        // Выводим числа месяца
        for (int i = 1, j = d.lengthOfMonth() + 1; i < j; i++) {

            // Если текущий день, то помечаем его символом *
            if (d.withDayOfMonth(i).equals(currDay)) {
                System.out.print(ANSI_PURPLE);
                //System.out.print(ANSI_RESET);
            }

            // Если текущий день недели равен первому дню,
            // то вставляем символ перевода строки
            if (d.withDayOfMonth(i).getDayOfWeek() == firstDayOfWeek) System.out.println();
            // Выводим число
            System.out.printf("%3d", i);

            // Если текущий день, то помечаем его символом *
            if (d.withDayOfMonth(i).equals(currDay)) {
                System.out.print(ANSI_PURPLE+ "*" +ANSI_RESET);
                //System.out.print(ANSI_RESET);
            } else System.out.print(" ");


        }
        System.out.println();
    }

    @Override
    public String onCenter(String s, int length, char ch) {
        if (s == null) return "";
        int sLength = s.length();
        if (length <= 0 || sLength == 0) return "";
        if (sLength == length) return s;
        if (sLength > length) return s.substring(0, length);
        int start = (length - sLength) / 2;
        int end = length - start - sLength;
        char[] arrStart = new char[start];
        char[] arrEnd = new char[end];
        Arrays.fill(arrStart, ch);
        Arrays.fill(arrEnd, ch);
        return String.valueOf(arrStart) + s +
                String.valueOf(arrEnd);
    }

    @Override
    public String onCenter(String s, int length) {
        return onCenter(s, length, ' ');
    }

    @Override
    public String[] getNameMonths() {
        return new String[] {"Январь", "Февраль", "Март",
                "Апрель", "Май", "Июнь", "Июль", "Август",
                "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    }

    @Override
    public String[] getNameWeeks() {
        return new String[] {"Пн", "Вт", "Ср", "Чт",
                "Пт", "Сб", "Вс"};
    }
}
