package main.data;

import main.BirthCalenderTransform.LunarUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyDate extends LunarUtil{
    public int isSolar;

    private MyDate(){};

    private MyDate(String year, String month, String date) {
        this.mYear = Integer.parseInt(year);
        this.mMonth = Integer.parseInt(month);
        this.mDay = Integer.parseInt(date);
        this.isSolar = 0;
    }
    // 构造阳历日期
    public static MyDate getTodaySolar() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] time = simpleDateFormat.format(calendar.getTime()).split("-");

        return new MyDate(time[0], time[1], time[2]);
    }

    // 构造阴历日期
    public static MyDate getTodayNotSolar() {
        MyDate myDate = new MyDate();

        myDate.InitLunar(Calendar.getInstance());
        myDate.isSolar = 1;

        return myDate;
    }

    @Override
    public String toString() {
        String string =  this.mYear + "年" + this.mMonth + "月" + this.mDay + "日";
        return this.isSolar == 0 ? "阳历" + string: "阴历" + string;
    }
}
