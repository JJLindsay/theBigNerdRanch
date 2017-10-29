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
 * Stores a list of crimes in a singleton.
 * TODO: Remove creating list using a loop
 */
public class CrimeLab
{
    //Singleton constructor
    private static CrimeLab sCrimeLab;
    private static List<Crime> sCrimes;
    private static int sCrimeRemovedFlag;

    public static CrimeLab getInstance(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        sCrimes = new ArrayList<Crime>();  //arraylist makes it possible to remove without leaving pockets for crimePagerActivity
/*        for (int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i%2 == 0);  //every other one
            sCrimes.add(crime);
        }*/
    }

    /**
     * Its best to use an interface like List since you can change your implementation
     * without changing the method.
     * @return
     */
    public List<Crime> getCrimes()
    {
        return sCrimes;
    }

    /**
     * Find a crime given the UUID
     * @param id
     * @return
     */
    public static Crime getCrime(UUID id){
        for (Crime crime: sCrimes)
        {
            if (crime.getId().equals(id))
                return crime;
        }
        return null;
    }

    public void addCrime(Crime crime)
    {
        sCrimes.add(crime);
    }

    public void removeCrime(Crime crime)
    {
        sCrimeRemovedFlag = 1;
        sCrimes.remove(crime);
    }

    public static boolean isCrimeRemoved()
    {
        return 1 == sCrimeRemovedFlag;
    }

    public static void resetCrimeRemovedFlag()
    {
        sCrimeRemovedFlag =0;
    }
}