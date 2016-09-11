package com.big.nerd.ranch.criminalintent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This wires up the fragment: fragment_crime.xml which will be displayed on the main activity: activity_crime.xml. The
 * fragment will be placed inside the view through code instead of xml to make swapping in and out fragments possible.
 * This class also acts as teh controller that interacts with model (the crime) and view (the xml) objects.
 *
 * Presents the details of a crime and updates the details as the user changes them.
 */
public class CrimeFragment extends Fragment
{
    private static final String LOG_TAG = "CrimeFragment";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    /**
     * This is where the wiring that normally happens in onCreate(...) for Activity takes place
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentCrimeView = inflater.inflate(R.layout.fragment_crime, container, false);  //false for add the view to the parent. This will be done in code (in Activity) so false.

        mTitleField = (EditText) fragmentCrimeView.findViewById(R.id.crime_title);
        mDateButton = (Button) fragmentCrimeView.findViewById(R.id.crime_date);
        mSolvedCheckBox = (CheckBox) fragmentCrimeView.findViewById(R.id.crime_solved);

        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mTitleField.addTextChangedListener(new TextChangedListener());
        mSolvedCheckBox.setOnCheckedChangeListener(new CheckBoxChangedListener());

        return fragmentCrimeView;
    }

    class TextChangedListener implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            //blank for now
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            mCrime.setTitle(s.toString());
            Log.d(LOG_TAG, "Text changed state.");
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
            //TODO
        }
    }

    class CheckBoxChangedListener implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            //Set the Crime's solved property
            mCrime.setSolved(isChecked);
            Log.d(LOG_TAG, "check box changed state.");
        }
    }
}