package com.big.nerd.ranch.ciminalIntent.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.big.nerd.ranch.ciminalIntent.common.SingleFragmentActivity;

import java.util.UUID;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This class inherits onCreate(...) and if a fragment has not been created,
 * onCreate() will call createFragment() to place set your fragment of choice in the generic fragment xml.
 *
 * Sets a fragment in the empty xml.
 *
 * I need an activity with space for a fragment. My fragment is a detailed crime fragment.
 *
 *
 * (!!!!!) ATTENTION(!!!!)
 *
 * THIS CLASS WAS REPLACED BY CrimePageActivity.java
 */
public class CrimeActivity extends SingleFragmentActivity
{
    private static final String EXTRA_CRIME_ID = "com.big.nerd.ranch.ciminalIntent.CRIME_ID";
    private static final String LOG_TAG = CrimeActivity.class.getSimpleName();


    // Tell crimeFragment which crime to display via crimeID in intent exta
    public static Intent getIntent(Context packageContext, UUID crimeID)
    {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        //return CrimeFragment.newInstance(crimeID);
        return null;
    }
}