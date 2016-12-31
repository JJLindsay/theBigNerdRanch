package com.big.nerd.ranch.ciminalIntent.model;

import android.text.format.DateFormat;

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
    private String mDateFormat;
    private Date mDate;
    private boolean mSolved;

    public Crime()
    {
        mDateFormat = null;
        mId = UUID.randomUUID();
        mDate = new Date();
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
        mDateFormat = DateFormat.format("EEEE, MMM dd, yyyy", mDate).toString();
        return mDateFormat;
    }

    public Date getDate()
    {
        return mDate;
    }

    public void setDate(Date newDate)
    {
        mDate = newDate;
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