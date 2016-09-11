package com.big.nerd.ranch.criminalintent;



import android.text.format.DateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 */
public class Crime
{
    //UUID is a 'Universally Unique ID' which will be used for each crime.
    private UUID mId;
    private String mTitle;
    private String mDateFormat;
    private boolean mSolved;

    public Crime()
    {
        mId = UUID.randomUUID();
        mDateFormat = DateFormat.format("EEEE, MMM dd, yyyy", new Date()).toString();
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

    public String getDate()
    {
//        return mDate;
        return mDateFormat;
    }

    public void setDate(Date date)
    {
//        mDate = date;
    }

    public boolean isSolved()
    {
        return mSolved;
    }

    public void setSolved(boolean solved)
    {
        mSolved = solved;
    }
}