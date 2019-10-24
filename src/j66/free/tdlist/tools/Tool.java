package j66.free.tdlist.tools;

import java.time.LocalDateTime;

abstract public class Tool {
    public static String formatDate(LocalDateTime l){
        return l.getYear()+"-"+l.getMonthValue()+"-"+l.getDayOfMonth()+" "+l.getHour()+":"+l.getMinute()+":"+l.getSecond();
    }
}
