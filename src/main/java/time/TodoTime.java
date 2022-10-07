package time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TodoTime {

    public static String currentTime(){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }
}
