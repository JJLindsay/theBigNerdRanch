package com.big.nerd.ranch.ciminalIntent.controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
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
 * <p/>
 * CLASSES: CrimeListFragment, CrimeHolder, CrimeAdapter
 *
 * Display a list of crimes to the user with RecyclerView
 */
public class CrimeListFragment extends Fragment
{
    private static final String LOG_TAG = CrimeListFragment.class.getSimpleName();
    private static final int REQUEST_CRIME_ID = 0xA1;  //this should be known only by the recipient and it is how communication is handled.
    private static final String CHANGED_CRIME = "com.big.nerd.ranch.CRIME_ID";
    private static UUID mChangedCrimeID = null;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //NOTE: Here view and RecyclerView are pointing to the same file.

        //defines which xml is the View
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        //find the recyclerView in the View xml. RecyclerView is simply a list of empty recyclable rows.
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //set the appropriate layout manager for the RecyclerView
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    /**
     * UpdateUI is here because onStart() is not always called. E.G.: if the new activity is transparent,
     * this activity is only in onPause() and wont go to onStart() on return.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }


    /**
     * The adapter is a link between the Model and the view.
     *
     * Creates holders for each view (called CrimeHolders here) and makes the request to bind the Model data to the view layer.
     * A crimeAdapter exists for ever object in the Model list.
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>
    {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes)
        {
            mCrimes = crimes;
        }

        /**
         * This is called by the system.
         * <p/>
         * Sets the View template that will be used with the Model and sends it to the ViewHolder
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            //retrieves the xml that will be a template of the contents of a given row
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        /**
         * Gets a Crime object and calls bindCrime(...) to set the appropriate views/components/widgets to the crime obj
         *
         * @param crimeHolder
         * @param position
         */
        @Override
        public void onBindViewHolder(CrimeHolder crimeHolder, int position)
        {
            Crime crime = mCrimes.get(position);
            crimeHolder.bindCrime(crime);
        }

        @Override
        public int getItemCount()
        {
            return mCrimes.size();
        }
    }


    /**
     * ViewHolder' s primary purpose is to set values for the view once its given a crime object.
     * This is where a single view is configured and listed. CrimeHolder also opens the next activity onClick.
     *
     * Each view has a holder, therefore it makes sense to implement onclick as this level
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        /**
         * Wires up the ViewHolder components
         *
         * @param itemView
         */
        public CrimeHolder(View itemView)
        {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);

            itemView.setOnClickListener(this);
        }

        /**
         * Sets values for the view components (all held in the ViewHolder)
         *
         * @param crime
         */
        public void bindCrime(Crime crime)
        {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View view)
        {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();

            // From the list, pass in the selected view's crimeID.
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId()); // getActivity is possible since this is all within FragmentActivity
            startActivity(intent);
        }
    }

    /**
     * Get the list of crimes and pass them to the CrimeAdapter.
     * For the CrimeRecyclerView, set the adapter (which provides views on demand) and when it is changed,
     * all existing views are recycled back to the pool.
     */
    private void updateUI()
    {
        //CrimeLab holds a list of crimes
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());  //pass in the context to the CrimeLab instance constructor
        List<Crime> crimes = crimeLab.getCrimes();

        // if updateUI was called by onCreate do 1, if onResume(), then refresh the list
        if (mAdapter == null)
        {
            Log.d(LOG_TAG, "updateUI() mAdapter is null.");
            mAdapter = new CrimeAdapter(crimes);  //passes in a list of crime objects to CrimeAdapter
            //set the adapter for RecyclerView to use
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else if (mChangedCrimeID != null)
        {
            Log.d(LOG_TAG, "The following crime has changed: " + CrimeLab.getCrime(mChangedCrimeID).getTitle());
            mAdapter.notifyItemChanged(crimes.indexOf(CrimeLab.getCrime(mChangedCrimeID)));  //notifies all observes that one view has changed
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * This method is called by the OS's ActivityManager once the class called (usually) via
     * startActivityForResult(intent,request_code) calls setResult(request_code, intent)
     *
     * @param callBackCode  This is the code used on the intent call that would identify it.
     * @param resultCode This is a generic settable status update of OK or Cancelled
     * @param intent holds the extras
     */
    @Override
    public void onActivityResult(int callBackCode, int resultCode, Intent intent)
    {
        Log.d(LOG_TAG, "onActivityResult() called");

        if (resultCode != Activity.RESULT_OK)  //did it return ok or cancelled?
            return;

        if (intent == null)
            return;
        else if (intent.hasExtra(CrimeFragment.ALTERED_CRIME))
        {
            mChangedCrimeID = (UUID) intent.getSerializableExtra(CrimeFragment.ALTERED_CRIME);
        }

    }
}