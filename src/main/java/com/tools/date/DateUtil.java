package com.tools.date;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtil {
	
	/**
	 * 将时间转化为   yyyy-MM-dd HH:mm:ss 字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return(sdf.format(date));
	}

	/**
	 * 将时间字符串转化为  yyyy-MM-dd HH:mm:ss 时间
	 * @param pstrString
	 * @return
	 */
	public static Date stringToDate(String pstrString){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date toDate = null;
		try {
			toDate = sdf.parse(pstrString);
		} catch (Exception e) {
			log.error("时间转换工具->转化yyyy-MM-dd HH:mm:ss格式失败，带转换数据{}", pstrString, e);
		}
		
		return toDate;
	}
	
	/**
	 * 将时间字符串转化为  yyyy-MM-dd 时间
	 * @param pstrString
	 * @return
	 */
	public static Date shortStringToDate(String pstrString){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date toDate = null;
		try {
			toDate = sdf.parse(pstrString);
		} catch (Exception e) {
			log.error("时间转换工具->转化yyyy-MM-dd格式失败，带转换数据{}", pstrString, e);
		}
		
		return toDate;
	}

	/**
	 * 日期转时间--yyyy-MM
	 * @param pstrString
	 * @return
	 */
	public static Date shortStringToDateByYear(String pstrString){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Date toDate = null;
		try {
			toDate = sdf.parse(pstrString);
		} catch (Exception e) {
			log.error("时间转换工具->转化yyyy-MM格式失败，带转换数据{}", pstrString, e);
		}

		return toDate;
	}

	/**
	 * Date转LocalDate
	 * @param date
	 * @return
	 */
	public static LocalDate dateToLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate toLocalDate = instant.atZone(zoneId).toLocalDate();
		return toLocalDate;
	}
	
	/**
	 * LocalDate转Date
	 * @param localDate
	 * @return
	 */
	public static Date localDateToDate(LocalDate localDate){
		ZoneId zoneid = ZoneId.systemDefault();
		ZonedDateTime zdt = localDate.atStartOfDay(zoneid);
		Date date = Date.from(zdt.toInstant());
		return date;
	}
	
	 /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        long nanoOfSecond = (date.getTime() % 1000) * 1000000;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(date.getTime() / 1000, (int) nanoOfSecond, ZoneOffset.of("+8"));
 
        return localDateTime;
    }
	
    /**
     * 得到N天后的日期
     *
     * @param theDate 某日期
     *                格式 yyyy-MM-dd
     * @param nDayNum N天
     * @return String N天后的日期
     */
    public static String afterNDaysDate(String theDate, Integer nDayNum) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusDays(nDayNum)
                .format(dateTimeFormatter);
    }
    
    /**
     * 得到N天后的日期
     * @param localDate
     * @param nDayNum
     * @return
     */
    public static Date afterNDaysDate(LocalDate localDate, Integer nDayNum){
    	return localDateToDate(localDate.plusDays(nDayNum));
    }
    
    /**
     * 得到N天后的日期
     * @param nDayNum
     * @return
     */
    public static Date afterNDaysDate(Date date, Integer nDayNum){
    	return localDateToDate(dateToLocalDate(date).plusDays(nDayNum));
    }

    /**
     * 计算两个时间LocalDateTime相差的月数，不考虑日期前后，返回结果>=0
     *
     * @param before
     * @param after
     * @return
     */
    public static int getAbsDiffMonth(LocalDate before, LocalDate after) {
        return Math.abs(Period.between(before, after).getMonths());
    }

    /**
     * 根据时间获取当月有多少天数
     *
     * @param date
     * @return
     */
    public static int getActualMaximum(Date date) {
        return dateToLocalDateTime(date).getMonth().length(dateToLocalDate(date).isLeapYear());
    }

    /**
     * 判断指定时间是否在时间范围内
     * @param time
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isTimeInRange(Date time, Date startTime, Date endTime) {
        LocalDateTime now = dateToLocalDateTime(time);
        LocalDateTime start = dateToLocalDateTime(startTime);
        LocalDateTime end = dateToLocalDateTime(endTime);
        return (start.isBefore(now) && end.isAfter(now)) || start.isEqual(now) || end.isEqual(now);
    }

    /**
     * 计算两个时间LocalDate相差的天数，不考虑日期前后，返回结果>=0
     *
     * @param before
     * @param after
     * @return
     */
    public static int getAbsTimeDiffDay(LocalDate before, LocalDate after) {
        return Math.abs(Period.between(before, after).getDays());
    }
    
    /**
     * 计算两个时间LocalDate相差的年数，不考虑日期前后，返回结果>=0
     * @param before
     * @param after
     * @return
     */
    public static int getAbsDiffYear(LocalDate before, LocalDate after) {
        return Math.abs(Period.between(before, after).getYears());
    }

    /**
     * 获取指定时间年份
     * @param date
     * @return
     */
    public static int getYear(Date date){
    	LocalDate localDate = dateToLocalDate(date);
    	return localDate.getYear();
    }
    
    /**
     * 特定指定年第一天
     *
     * @param date
     * @return
     */
    public static Date getYearToStart(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        LocalDate newLocalDate = LocalDate.of(localDate.getYear(), 1, 1);
        return localDateToDate(newLocalDate);
    }
    
    /**
     * 得到指定年最后一天
     * @return
     */
    public static Date getYearToEnd(Date date){
    	LocalDate localDate = dateToLocalDate(date);
    	LocalDate newLocalDate = LocalDate.of(localDate.getYear(), 1, 1);
    	localDate = newLocalDate.minusDays(1);
    	return localDateToDate(localDate);
    }
    
    /**
     * 获取指定时间当月的开始时间
     * @param date
     * @return
     */
    public static Date getMonthToStart(Date date){
    	LocalDate localdate = dateToLocalDate(date);
    	LocalDate month = LocalDate.of(localdate.getYear(), localdate.getMonth(), 1);
    	return localDateToDate(month);
    }
    
    /**
     * 获取指定时间当月的结束时间
     * @param date
     * @return
     */
    public static Date getMonthToEnd(Date date){
    	int lastDay = getActualMaximum(date);
        LocalDate localDate = dateToLocalDate(date);
        LocalDate month = LocalDate.of(localDate.getYear(), localDate.getMonth(), lastDay);
        return localDateToDate(month);
    }
    
    /**
     * 获取指定时间当天的开始时间
     * @param date
     * @return
     */
    public static Date getDateToStart(Date date){
    	LocalDateTime dateTime = LocalDateTime.of(dateToLocalDate(date), LocalTime.MIN);
    	ZoneId zone = ZoneId.systemDefault();
    	Instant instant = dateTime.atZone(zone).toInstant();
    	return Date.from(instant);
    }
    
    /**
     * 获取指定时间当天的结束时间
     * @param date
     * @return
     */
    public static Date getDateToEnd(Date date){
    	LocalDateTime dateTime = LocalDateTime.of(dateToLocalDate(date), LocalTime.MAX);
    	ZoneId zone = ZoneId.systemDefault();
    	Instant instant = dateTime.atZone(zone).toInstant();
    	return Date.from(instant);
    }

}
