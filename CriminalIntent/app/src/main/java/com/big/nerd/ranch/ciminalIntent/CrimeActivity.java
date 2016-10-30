package com.big.nerd.ranch.ciminalIntent;

import android.support.v4.app.Fragment;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * The Main Activity
 * This class inherits onCreate(...) and if a fragment has not been created,
 * onCreate() will call createFragment() to place set your fragment of choice in the generic fragment xml.
 *
 * Sets a fragment in the empty xml.
 *
 * I need an activity with space for a fragment. My fragment is a detailed crime fragment.
 */
public class CrimeActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new CrimeFragment();
    }
}