package com.gmself.studio.mg.basemodule.utils.dateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by guomeng on 4/20.
 */

public class DateTool {


    public static Date getSystemCurrentDate(){
        return new Date(System.currentTimeMillis());
    }

    public static String getStringDate(Date date, DateStyles dateStyle){
        SimpleDateFormat format = new SimpleDateFormat(dateStyle.getValue());               //
        return format.format(date);
    }

    /**
     * 获取星期几
     * @return 1 = 周一，2= 周二 。。。。 7 = 周日
     * */
    public static int getDayOfWeek_number(Date date){
        Calendar c = dateToCalendar(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期几
     * @return 一 = 周一，二= 周二 。。。。 日 = 周日
     * */
    public static String getDayOfWeek_str(Date date){
        Calendar c = dateToCalendar(date);
        int i = c.get(Calendar.DAY_OF_WEEK);
        String str = "几";
        switch (i){
            case 1:
                str = "日";
                break;
            case 2:
                str = "一";
                break;
            case 3:
                str = "二";
                break;
            case 4:
                str = "三";
                break;
            case 5:
                str = "四";
                break;
            case 6:
                str = "五";
                break;
            case 7:
                str = "六";
                break;
        }
        return str;
    }

    public static Calendar dateToCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
