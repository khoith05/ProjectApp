package com.example.android.projectmanagement;

import android.content.Context;

import com.example.android.projectmanagement.database.TaskSQL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateHelper {
//    public static String androiFormat;
    public static final String myFormat="dd/MM/yyyy";
    public static Date StringToDate(String dt) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat(myFormat);
        Date date=format.parse(dt);
        return date;
    }
    public static String IntToDate(int day,int month,int year){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);
        Date date=calendar.getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        return dateFormat.format(date);
    }
//    public static Date StringToDate2(String dt) throws ParseException {
//        SimpleDateFormat formater=new SimpleDateFormat(androiFormat);
//        Date date=formater.parse(dt);
//
//        return date;
//    }
//    public static String DateToString(Date date){
//        return date.toString();
//    }
//    public static String DateConvert(String date) throws ParseException {
//        SimpleDateFormat formater=new SimpleDateFormat(androiFormat);
//        Date before=formater.parse(date);
//        SimpleDateFormat formater2=new SimpleDateFormat(myFormat);
//        String Adate=formater2.format(before);
//        return Adate;
//    }
    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }
    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }
    public static String getState(String start, String end){
        Date startSelect;
        Date endSelect;
        try {
            startSelect=DateHelper.StringToDate(start);
            endSelect=DateHelper.StringToDate(end);
        } catch (ParseException e) {
            return TaskSQL.notstart;
        }
        Date current= Calendar.getInstance().getTime();
        if(current.after(startSelect) && current.before(endSelect)){
            return TaskSQL.processing;
        }else if (current.before(startSelect)){
            return TaskSQL.notstart;
        }else if (current.after(endSelect)){
            return TaskSQL.late;
        }
        return TaskSQL.notstart;
    }
    public static String getCurrent(){
        Date date=Calendar.getInstance().getTime();
        SimpleDateFormat format=new SimpleDateFormat(myFormat);
        return format.format(date);
    }

}
