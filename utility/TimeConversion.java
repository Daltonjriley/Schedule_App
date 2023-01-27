package utility;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConversion {
    public ZonedDateTime toEST(LocalDateTime localDateTime){
        ZonedDateTime zonedTime = localDateTime.atZone(ZoneId.of("US/Eastern"));
        return zonedTime;
    }
}
