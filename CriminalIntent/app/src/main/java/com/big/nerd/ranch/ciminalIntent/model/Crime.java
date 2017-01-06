package com.big.nerd.ranch.ciminalIntent.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This defines what a crime is.
 */
public class Crime
{
    //UUID is a 'Universally Unique ID' which will be used for each crime.
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;

    public Crime()
    {
        mDateFormat = null;  //for formatting the date and time
        mId = UUID.randomUUID();
        mDate = new Date();  //for displaying the date
        mCalendar = Calendar.getInstance();  //for displaying the time

        mDate.setTime(System.currentTimeMillis());
        mCalendar.setTime(mDate);
    }

    public UUID getId()
    {
        return mId;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }

    public String getFormattedDate()
    {
        mDateFormat = new SimpleDateFormat("EEEE, MMMMM dd, yyyy");
        return mDateFormat.format(mDate.getTime());
    }

    public Date getDate()
    {
        return mDate;
    }

    public void setDate(Date newDate)
    {
        mDate = newDate; //new dates should have a time included
        mCalendar.setTime(mDate);
    }

    public boolean isSolved()
    {
        return mSolved;
    }

    public void setSolved(boolean solved)
    {
        mSolved = solved;
    }

    public Calendar getCalendar()
    {
        return mCalendar;
    }

    public void setCalendar(Calendar calendar)
    {
        mDate = calendar.getTime(); //new dates should have a time included
        mCalendar = calendar;
    }

    public String getFormattedTime()
    {
        mDateFormat = new SimpleDateFormat("HH:mm aaa");
        return mDateFormat.format(mDate.getTime());
    }
}