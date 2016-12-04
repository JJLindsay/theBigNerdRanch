package com.big.nerd.ranch.ciminalIntent.controller;

import android.support.v4.app.Fragment;
import com.big.nerd.ranch.ciminalIntent.common.SingleFragmentActivity;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * The Main Activity
 * Sets a fragment in the empty xml.
 *
 * I need an activity with space for a Fragment. My fragment is a list of crimes
 */
public class CrimeListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new CrimeListFragment();
    }
}
