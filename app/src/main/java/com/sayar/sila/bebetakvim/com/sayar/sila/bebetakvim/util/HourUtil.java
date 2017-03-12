package com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util;

/**
 * Created by zisan on 4.10.2016.
 */
public class HourUtil {

    public static String getHM(int selectedHour, int selectedMinute ) {
        String hm = selectedHour + ":" + selectedMinute;
        if(selectedHour<10 && selectedMinute<10)
            hm = "0"+selectedHour + ":0" + selectedMinute;
        else if(selectedHour<10)
            hm = "0"+selectedHour + ":" + selectedMinute;
        else if(selectedMinute<10)
            hm = selectedHour + ":0" + selectedMinute;
        return hm;
    }

    public static String minToHour(String min){
        String res;
        int hour;
        int mintmp;
        int minute = Integer.parseInt(min);
        mintmp=minute%60;
        hour = (minute - mintmp)/60;
        if(hour==0)
            res = ""+mintmp+" dk";
        else if(mintmp==0)
            res = hour+" saat";
        else
            res=hour+" saat "+mintmp+" dk";
        return res;
    }
}
