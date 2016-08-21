package com.big.nerd.ranch.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * The Main Activity
 * This wires up the opening activity: activity_crime.xml and starts the class that will manage the fragment: fragment_crime.xml.
 */
public class CrimeActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);  //to retrieve the crimeFragment ask by container view id

        if (fragment == null)  //if fragment has never been opened or if it has, the app has since been fully closed
        {
            fragment = new CrimeFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
