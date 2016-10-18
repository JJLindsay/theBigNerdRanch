package com.big.nerd.ranch.ciminalIntent.model;

import android.content.Context;

import java.util.ArrayList;
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
 * Store list of crimes in a singleton.
 * Temporary: Creating list using a loop
 */
public class CrimeLab
{
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    private CrimeLab(Context context){
        mCrimes = new ArrayList<Crime>();
        for (int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i%2 == 0);  //every other one
            mCrimes.add(crime);
        }
    }

    public static CrimeLab getInstance(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    /**
     * Its best to use an interface like List since you can change your implementation
     * without changing the method.
     * @return
     */
    public List<Crime> getCrimes()
    {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime: mCrimes)
        {
            if (crime.getId().equals(id))
                return crime;
        }
        return null;
    }
}