package com.big.nerd.ranch.ciminalIntent.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.big.nerd.ranch.ciminalIntent.R;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This class allows any class that inherits it, to add their Fragment to an empty layout
 */
public abstract class SingleFragmentActivity extends FragmentActivity
{
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //sets an empty layout to the view
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //if there is a fragment in this view, use it. Otherwise, ask for a fragment.
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);  //to retrieve the crimeFragment ask by container view id

        if (fragment == null)  //if fragment has not been created
        {
            fragment = createFragment();  //get a fragment from a child class
            //add the fragment to the empty layout in the view.
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}