package com.fdgproject.firedge.notevent;

/**
 * Created by Firedge on 05/10/2014.
 */
public class Event implements Comparable<Event> {
    private int day, month, year;
    private String text;
    private int hour, minute;
    static String[] monthList;

    public Event(int day, int month, int year, String text, int hour, int minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.text = text;
        this.hour = hour;
        this.minute = minute;
    }

    public static void setMonthList(String[] list){
        monthList = list;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonthNumber() {
        return month+1;
    }
    public String getMonth(){
        return monthList[month];
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDate(){
        return getDay()+"/"+getMonth()+"/"+getYear();
    }

    public String getTime(){
        String minute = Integer.toString(getMinute());
        if(minute.length()==1)
            return getHour()+":0"+getMinute();
        return getHour()+":"+getMinute();
    }

    @Override
    public int compareTo(Event o) {
        if(this.getYear()<o.getYear())
            return -1;
        else if(this.getYear()==o.getYear()){
            if(this.getMonthNumber()<o.getMonthNumber())
                return -1;
            else if(this.getMonthNumber()==o.getMonthNumber()){
                if(this.getDay()<o.getDay())
                    return -1;
                else if(this.getDay()==o.getDay()){
                    if(this.getHour()<o.getHour())
                        return -1;
                    else if(this.getHour()==o.getHour()){
                        if(this.getMinute()<o.getMinute())
                            return -1;
                        else
                            return 0;
                    }
                    return 1;
                }
                return 1;
            }
            return 1;
        }
        return 1;
    }
}
