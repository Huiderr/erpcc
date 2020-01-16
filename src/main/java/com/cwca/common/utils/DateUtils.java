package com.cwca.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 对日期的处理
 *
 * @author wongs
 */
@Slf4j
public class DateUtils {

    private static String previousDateTime;

    public static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_DEFAULT_M = "yyyy-MM-dd HH:mm:ss.SSS";
    public static String DATE_FORMAT_TYPE_ONE = "yyyyMMddHHmmss";
    public static String DATE_FORMAT_TYPE_TWO = "yyyy-MM-dd";
    public static String DATE_FORMAT_TYPE_THREE = "yyyyMMdd";
    public static String DATE_FORMAT_TYPE_FOUR = "HHmmssSSS";

    public synchronized static String getUniqueDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_TYPE_ONE);
        String dateTime = sdf.format(new Date());
        while (dateTime.equals(previousDateTime)) {
            dateTime = sdf.format(new Date());
        }
        previousDateTime = dateTime;
        return dateTime;
    }

    /**
     * long转换为时间
     */
    public static String longToDate(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
        Date date = new Date(millSec);
        return sdf.format(date);

    }

    /**
     * long转换为时间(毫秒)
     */
    public static String longToDateWithM(Long millSec) {
        String time = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT_M);
            Date date = new Date(millSec);
            time = sdf.format(date);
        } catch (Exception e) {
            return null;
        }
        return time;
    }

    /**
     * 根据时间格式取当前时间
     */
    public static String getDateTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDateTime(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date getToDate(Date dDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dDate);
        return strToDate(dateString, "yyyy-MM-dd");
    }

    public static Date getToDatetime(Date dDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(dDate);
        return strToDate(dateString, format);
    }

    /**
     * 判断传入的日期是否大于当前系统时间额定的时间
     *
     * @param rating:额定时间
     */
    public static boolean isAfter(String timeString, int rating) {
        try {
            Date date = new SimpleDateFormat(DATE_FORMAT_DEFAULT).parse(timeString);
            Date now = new SimpleDateFormat(DATE_FORMAT_DEFAULT)
                    .parse(new SimpleDateFormat(DATE_FORMAT_DEFAULT).format(new Date()));
            long min = date.getTime();
            long nowmin = now.getTime();
            long count = rating * 60 * 1000;
            if (nowmin - min > count){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断传入的日期是否小于当前系统时间额定的时间
     */
    public static boolean isBefore(String timeString, int rating) {
        try {
            Date date = new SimpleDateFormat(DATE_FORMAT_DEFAULT).parse(timeString);
            Date now = new SimpleDateFormat(DATE_FORMAT_DEFAULT).parse(new SimpleDateFormat(DATE_FORMAT_DEFAULT).format(new Date()));
            long min = date.getTime();
            long nowmin = now.getTime();
            long count = rating * 60 * 1000;
            if (nowmin - min < count){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将字符转换为指定的日期格式输出
     */
    public static Date strToDate(String s, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date1;
        try {
            Date theDate = formatter.parse(s);
            Date date = theDate;
            return date;
        } catch (Exception ex) {
            date1 = null;
        }
        return date1;
    }

    /**
     * 添加天/时/分
     */
    public static Date addMinute(long iNbTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getToDatetime(new Date(),DATE_FORMAT_DEFAULT));
        cal.add(Calendar.MINUTE, (int) iNbTime);
        Date date = cal.getTime();
        return date;
    }
    public static Date addMinute(String sDate, long iNbTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, DATE_FORMAT_DEFAULT));
        cal.add(Calendar.MINUTE, (int) iNbTime);
        Date date = cal.getTime();
        return date;
    }

    public static Date addHour(String sDate, long iNbTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, DATE_FORMAT_DEFAULT));
        cal.add(Calendar.HOUR_OF_DAY, (int) iNbTime);
        return cal.getTime();
    }

    public static Date addDay(String sDate, long iNbDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, DATE_FORMAT_TYPE_TWO));
        cal.add(Calendar.DAY_OF_MONTH, (int) iNbDay);
        return cal.getTime();
    }

    public static Date addWeek(String sDate, long iNbWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, DATE_FORMAT_DEFAULT));
        cal.add(Calendar.WEEK_OF_YEAR, (int) iNbWeek);
        return cal.getTime();
    }

    public static Date addMonth(String sDate, int iNbMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, DATE_FORMAT_DEFAULT));
        int month = cal.get(Calendar.MONTH);
        month += iNbMonth;
        int year = month / 12;
        month %= 12;
        cal.set(Calendar.MONTH, month);
        if (year != 0) {
            int oldYear = cal.get(Calendar.YEAR);
            cal.set(Calendar.YEAR, year + oldYear);
        }
        return cal.getTime();
    }

    public static Date addYear(Date dDate, int iNbYear) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        int oldYear = cal.get(1);
        cal.set(1, iNbYear + oldYear);
        return cal.getTime();
    }


    public static String getWeeks() {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // SimpleDateFormat 是一个以与语言环境相关的方式来格式化和分析日期的具体类。它允许进行格式化（日期 -> 文本）、分析（文本
        // -> 日期）和规范化。
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdfInput.parse(sdfInput.format(date));
        } catch (ParseException ex) {

        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// get 和 set
        // 的字段数字，指示一个星期中的某天。
        System.out.println("dayOfWeek:" + dayOfWeek);
        return dayNames[dayOfWeek - 1];
    }

    public static int getMonthOfDay(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMinuteOfHour(Date timeStamp) {
        if (timeStamp != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timeStamp);
            return calendar.get(Calendar.MINUTE);
        }
        return 0;
    }

    public static int getHourOfDay(Date timeStamp) {
        if (timeStamp != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timeStamp);
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        return 0;

    }

    /**
     * 生成随即数
     */
    public static String genRandomNum(int pwd_len) {
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        return genRandNum(pwd_len, str);
    }

    public static String genRandNum(int pwd_len) {
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        return genRandNum(pwd_len, str);
    }

    private static String genRandNum(int pwd_len, char[] str) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     * 生成随机字母
     */
    public static String genRandomChar() {
        final int maxNum = 25;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z'};

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 12) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString().toUpperCase();
    }

    /**
     * 得到当前年
     */
    public static String getNowYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 得到当前季度 1 第一季度  2 第二季度 3 第三季度 4 第四季度
     */
    public static int getSeason() {
        int season = 0;
        int month = Calendar.getInstance().get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 计算两个日期之间相差的天数
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    public static long translateDateToSecond(Date date) {
        if (date != null) {
            return date.getTime() / 1000;
        }
        return 0;
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    public static String encode(String src, String enCoding) {
        String target = null;
        if (src == null)
            return null;
        try {
            target = URLEncoder.encode(src, enCoding);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return target;
    }

    public static int getYearOfDate(Date date) {
        int year = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            year = calendar.get(Calendar.YEAR);
        } catch (Exception e) {
            //ignore
        }
        return year;
    }

    public static int getMonthOfDate(Date date) {
        int month = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            month = calendar.get(Calendar.MONTH);
        } catch (Exception e) {
            //ignore
        }
        return month;
    }

    public static int getDayOfDate(Date date) {
        int day = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            //ignore
        }
        return day;
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.getDateTime(DateUtils.addMinute(-10), DateUtils.DATE_FORMAT_DEFAULT));
    }
}



