package com.big.nerd.ranch.ciminalIntent.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.Calendar;
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
    public static final String ALTERED_CRIME = "com.big.nerd.ranch.ALTERED_CRIME";
    public static final String DELETE_CRIME = "com.big.nerd.ranch.DELETE_CRIME";

    private static final String LOG_TAG = CrimeFragment.class.getSimpleName();
    private static final String ARG_CRIME_ID = "Crime_ID";
    private static final String DIALOG_DATE = "Dialog_Date";
    private static final String DIALOG_TIME = "Dialog_Time";
    private static final String DIALOG_DELETE = "Dialog_Delete";
    private static final int REQUEST_DATE = 0xA2;
    private static final int REQUEST_TIME = 0xA3;
    private static final int REQUEST_DELETE = 0xA4;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mTimeButton;
    private Button mDelete;

    /**
     * Call this when ready to display a detailed Crime.
     * This approach (over getIntent extras) allows CrimeFragment to not depend on its
     * parent activity for the extras. This preserves encapsulation! Using the parent would mean that
     * CrimeFragment AS WRITTEN could not be used with just any activity.
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
        mTimeButton = (Button) fragmentCrimeView.findViewById(R.id.crime_time);
        mSolvedCheckBox = (CheckBox) fragmentCrimeView.findViewById(R.id.crime_solved);
        mDelete = (Button) fragmentCrimeView.findViewById(R.id.crime_delete);

        mTitleField.setText(mCrime.getTitle());
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        updateDateTimeUI();

        mDateButton.setOnClickListener(new DateClickedListener());
        mTimeButton.setOnClickListener(new TimeClickedListener());
        mTitleField.addTextChangedListener(new TextChangedListener());
        mSolvedCheckBox.setOnCheckedChangeListener(new CheckBoxChangedListener());
        mDelete.setOnClickListener(new DeleteClickedListener());

        return fragmentCrimeView;
    }

    public class TextChangedListener implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            //TODO
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            mCrime.setTitle(s.toString());
            reportPropertyChanged();
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
            //TODO
        }
    }

    public class CheckBoxChangedListener implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            //Set the Crime's solved property
            mCrime.setSolved(isChecked);
            reportPropertyChanged();
        }
    }

    public class DateClickedListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            FragmentManager fragmentManager = getFragmentManager();
            DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getCalendar());
            datePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
            datePickerFragment.show(fragmentManager, DIALOG_DATE);  //id for fm to organize its fragments
        }
    }

    public class TimeClickedListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            FragmentManager fragmentManager = getFragmentManager();
            TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mCrime.getCalendar());
            timePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
            timePickerFragment.show(fragmentManager, DIALOG_TIME); //id for fm to organize its fragments
        }
    }

    public class DeleteClickedListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            FragmentManager fragmentManager = getFragmentManager();
            DeleteAlertFragment deleteAlertFragment = DeleteAlertFragment.newInstance();
            deleteAlertFragment.setTargetFragment(CrimeFragment.this, REQUEST_DELETE);
            deleteAlertFragment.show(fragmentManager, DIALOG_DELETE);  //id for fm to organize its fragments
        }
    }

    private void updateDateTimeUI()
    {
        mDateButton.setText(mCrime.getFormattedDate());
        mTimeButton.setText(mCrime.getFormattedTime());
    }

    /**
     * DateFragment will call back results here
     * @param requestCode
     * @param resultCode
     * @param carrierData  An intent just for carrying data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent carrierData)
    {
        if (resultCode != Activity.RESULT_OK)
            return;

        Calendar calendar;
        if (requestCode == REQUEST_TIME)
        {
            calendar = (Calendar) carrierData.getSerializableExtra(TimePickerFragment.EXTRA_CALENDAR);
            mCrime.setCalendar(calendar);
            updateDateTimeUI();
            reportPropertyChanged();
        }
        else if (requestCode == REQUEST_DATE)
        {
            calendar = (Calendar) carrierData.getSerializableExtra(DatePickerFragment.EXTRA_CALENDAR);
            mCrime.setCalendar(calendar);
            updateDateTimeUI();
            reportPropertyChanged();
        }
        else if (requestCode == REQUEST_DELETE)
        {
            reportDeleteCrime();
        }
    }

    /**
     * Reports back if a change has taken place on this fragment
     */
    private void reportPropertyChanged()
    {
        Intent carrierIntent = new Intent();  //an intent just for carrying extras
        carrierIntent.putExtra(ALTERED_CRIME, mCrime.getId());
        getActivity().setResult(Activity.RESULT_OK, carrierIntent);
    }

    /**
     * Reports back if a delete crime request has occurred on this fragment
     *
     * NOTE!
     * Another option is to record the crime to delete after disabling the buttons and delete the crime only when
     * updateUI is called but what happens if the user closes the app after deleting and does not go to updateUI???
     */
    private void reportDeleteCrime()
    {
        final CrimeListFragment fCrimeListFragment = new CrimeListFragment();

        Intent carrierIntent = new Intent(getActivity(), CrimeListActivity.class);  //an intent just for carrying extras
        carrierIntent.putExtra(DELETE_CRIME, mCrime.getId());

        mTitleField.setEnabled(false);
        mDateButton.setEnabled(false);
        mTimeButton.setEnabled(false);
        mSolvedCheckBox.setEnabled(false);
        mDelete.setEnabled(false);

        fCrimeListFragment.onActivityResult(0, Activity.RESULT_OK, carrierIntent);  //NOTE: This is DIFFERENT from
        startActivity(carrierIntent);
    }

    @Override
    public void onPause()
    {
        Log.d(LOG_TAG, "onPause() called");

        CrimeListFragment fCrimeListFragment = new CrimeListFragment();
        Intent carrierIntent = new Intent(getActivity(), CrimeListActivity.class);  //an intent just for carrying extras
        carrierIntent.putExtra(DELETE_CRIME, mCrime.getId());

        if (mTitleField.getText().toString() == null || mTitleField.getText().toString().trim().isEmpty())
            fCrimeListFragment.onActivityResult(0, Activity.RESULT_OK, carrierIntent);

        super.onPause();
    }
}