package main.utils;

import main.data.MyDate;
import main.data.User;
import main.mysqlExecute.BirthProcess;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DateComputeUtils {
    static public final MyDate myDate = MyDate.getTodayNotSolar();
    static public final MyDate myDate1 = MyDate.getTodaySolar();
    // 由于日期之间会有前后之分，那么日期的变化对于时间长短的比较是无意义,但是对于特定日期2.29,年份是有意义的
    private static int dayCompute(int year, int month, int day, int month1, int day1, int isSolar) {
        if (isSolar == 0 && year % 4 != 0 && (year + 1)%4 != 0 && month1 == 2 && day1 == 29 ) {
            return Integer.MAX_VALUE;
        } else {
            int num = month*30 + day - month1 * 30 -day1;
            return  num<0? -num: 365-num;
        }
    }

    // 返回距离给定日期最小的user的集合
    private static ArrayList<User> minDayNumBirth(int year, int month, int date, int isSolar, ArrayList<User> users) {

        AtomicInteger min = new AtomicInteger(365);
        ArrayList<User> userArrayList = new ArrayList<>();
        users.forEach((el) -> {
            int dayNum = dayCompute(year, month, date, el.month, el.date, isSolar);
            if (min.get() > dayNum) {
                min.set(dayNum);
                userArrayList.clear();
                userArrayList.add(el);
            } else if (min.get() == dayNum) {
                userArrayList.add(el);
            }
        });
        return userArrayList;
    }

    // 得到下一个生日的集合
    public static ArrayList<User> nextBirth(int year, int month, int date, int isSolar) throws SQLException {
        ArrayList<User> userArrayList = BirthProcess.getAllBirth(isSolar);
        return DateComputeUtils.minDayNumBirth(year, month, date, isSolar, userArrayList);
    }
}
