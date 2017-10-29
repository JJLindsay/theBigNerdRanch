package com.big.nerd.ranch.ciminalIntent.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.big.nerd.ranch.ciminalIntent.R;
import com.big.nerd.ranch.ciminalIntent.model.Crime;
import com.big.nerd.ranch.ciminalIntent.model.CrimeLab;

import java.util.List;
import java.util.UUID;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This class differs from CrimeActivity in that the user can now swipe to see the next crime.
 * Going back to the CrimeListFragment is no longer required to access the next crime.
 */
public class CrimePagerActivity extends AppCompatActivity
{
    //the ID of the crime that should be on the screen.
    private static final String EXTRA_CRIME_ID = "com.big.nerd.ranch.criminalIntent.CRIME_ID";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    protected static Intent newIntent(Context packageContext, UUID crimeID)
    {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.getInstance(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager)
        {
            /**
             * @param position The index in the list of the crime.
             * @return
             */
            @Override
            public Fragment getItem(int position)
            {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount()
            {
                if (CrimeLab.isCrimeRemoved())
                {
                    CrimeLab.resetCrimeRemovedFlag();
                    notifyDataSetChanged();
                }

                return mCrimes.size();
            }
        });
        //Set the Crime Fragment to display.
        mViewPager.setCurrentItem(mCrimes.indexOf(CrimeLab.getCrime(crimeID)));
    }
}