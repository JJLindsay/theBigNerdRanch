package com.big.nerd.ranch.ciminalIntent.controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.big.nerd.ranch.ciminalIntent.R;
import com.big.nerd.ranch.ciminalIntent.model.Crime;
import com.big.nerd.ranch.ciminalIntent.model.CrimeLab;

import java.util.UUID;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This wires up the fragment: fragment_crime.xml which will be displayed on the main activity: activity_fragment.xml. The
 * fragment will be placed inside the view through code instead of xml to make swapping in and out fragments possible.
 * This class also acts as the controller that interacts with model (the crime) and view (the xml) objects.
 *
 * Presents the details of a crime and updates the details as the user changes them.
 */
public class CrimeFragment extends Fragment
{
    private static final String LOG_TAG = CrimeFragment.class.getSimpleName();
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String CHANGED_CRIME = "com.big.nerd.ranch.CHANGED_CRIME";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    /**
     * In order to display the view, we need a ensure we have a crime object first.
     * This method is used for preliminary setup before we can work with the view.
     * NOTE: see newInstance for why not intent.getExtras(...)
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Create a crime object
        // Look in the shared parent: Fragment for the Bundle and get its args
        UUID crimeID = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getCrime(crimeID);
    }

    /**
     * Call this when ready to display a detailed Crime.
     * This approach (over getIntent extras) allows CrimeFragment to not depend on its
     * parent activity for the extras. This preserves encapsulation! Using the parent would mean that
     * CrimeFragment AS WRITTEN (!) could not be used with just any activity.
     *
     * @param crimeID The id of the crime to be displayed.
     * @return A Fragment object
     */
    public static CrimeFragment newInstance(UUID crimeID)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeID);

        // This will set the argument bundle to the parent: Fragment.
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);

        return crimeFragment;
    }

    /**
     * This is where the wiring that normally happens in onCreate(...) for Activity takes place
     *
     * @param inflater The layout to be inflated
     * @param container Holds the view which here is a single displayed crime
     * @param savedInstanceState holds any keys that have been saved from previous runs
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentCrimeView = inflater.inflate(R.layout.fragment_crime, container, false);  //false for add the view to the parent. This will be done in code (in Activity) so false.

        mTitleField = (EditText) fragmentCrimeView.findViewById(R.id.crime_title);
        mDateButton = (Button) fragmentCrimeView.findViewById(R.id.crime_date);
        mSolvedCheckBox = (CheckBox) fragmentCrimeView.findViewById(R.id.crime_solved);

        mTitleField.setText(mCrime.getTitle());
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mTitleField.addTextChangedListener(new TextChangedListener());
        mSolvedCheckBox.setOnCheckedChangeListener(new CheckBoxChangedListener());

        return fragmentCrimeView;
    }

    public class TextChangedListener implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            //TODO
            Log.d(LOG_TAG, "Before text has changed state.");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            mCrime.setTitle(s.toString());
            Log.d(LOG_TAG, "Text has changed state.");

            propertyChanged();
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
            //TODO
            Log.d(LOG_TAG, "After text has changed state.");
        }
    }

    public class CheckBoxChangedListener implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            //Set the Crime's solved property
            mCrime.setSolved(isChecked);
            Log.d(LOG_TAG, "check box changed state.");

            propertyChanged();
        }
    }

    /**
     * Reports back but does not start the activity
     */
    private void propertyChanged()
    {
        //if the answer is never shown Activity.RESULT_CANCELLED is returned by default automatically
        Intent intent = new Intent();
        intent.putExtra(CHANGED_CRIME, mCrime.getId());
        getActivity().setResult(Activity.RESULT_OK, intent);  //there are two standard responses: result_ok and result_cancelled. Intent optional
    }
}