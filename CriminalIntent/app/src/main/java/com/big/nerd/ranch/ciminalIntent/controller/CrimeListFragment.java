package com.big.nerd.ranch.ciminalIntent.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.CheckBox;
import android.widget.TextView;
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
 * Finds the next occurrence of the currently selected text = ctrl + F3
 *
 * CLASSES: CrimeListFragment, CrimeHolder, CrimeAdapter
 *
 * Display a list of crimes to the user with RecyclerView
 */
public class CrimeListFragment extends Fragment
{
    private static final String LOG_TAG = CrimeListFragment.class.getSimpleName();
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private static UUID mChangedCrimeID = null;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private int mRemovedCrimeIndex;

    /**
     * This exists for the sole purpose of telling the FragmentManager this
     * fragment should receive the callback to create an options menu
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null)
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
    }

    //NOTE: Here view and RecyclerView are pointing to the same file.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //defines which xml is the View
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        //find the recyclerView in the View xml. RecyclerView is simply a list of empty recyclable rows.
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //set the appropriate layout manager for the RecyclerView
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //saved state for subtitle
        if (savedInstanceState != null)
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE, false);

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
     * When the menu is needed, Android calls this method to inflate the menu item of choice.
     * @param menu The menu instance
     * @param inflater A menu inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);  //not required but its a good practice to ensure any superclass menu functionality still works
        inflater.inflate(R.menu.fragment_crime_list, menu);

        //Trigger a re-creation of the action items when the user presses show_subtitle
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible)
        {
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else
        {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    /**
     * Changes here are lost on rotation.
     * @param item
     * @return true indicates no further processing necessary.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Set subtitle of the toolbar
     */
    private void updateSubtitle()
    {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        @SuppressLint("StringFormatMatches") String subtitle = getResources().getQuantityString(R.plurals.subtitle_format, crimeCount, crimeCount);

        if (!mSubtitleVisible)
            subtitle = null;

        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
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
     * Each view has a holder, therefore it makes sense to implement onclick at this level
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
         * @param crime
         */
        public void bindCrime(Crime crime)
        {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getFormattedDate());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View view)
        {
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
            mAdapter = new CrimeAdapter(crimes);  //passes in a list of crime objects to CrimeAdapter
            //set the adapter for RecyclerView to use
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        if (mChangedCrimeID != null)
        {
            if (CrimeLab.getCrime(mChangedCrimeID) != null)
            {
                Log.d(LOG_TAG, "The following crime has changed: " + CrimeLab.getCrime(mChangedCrimeID).getTitle());
                mAdapter.notifyItemChanged(crimes.indexOf(CrimeLab.getCrime(mChangedCrimeID)));  //notifies all observes that one view has changed
            }
            else {
                Log.d(LOG_TAG, "The following crime has been deleted: " + mChangedCrimeID);
                mAdapter.notifyItemRemoved(mRemovedCrimeIndex);
            }
        }
        mAdapter.notifyDataSetChanged();
        //toolbar subtitle
        updateSubtitle();
    }

    /**
     * This is called explicitly by another Fragment. Usually this would be done by the ActivityManager via
     * startActivityForResult(intent,request_code) and setResult(request_code, intent) before returning. However,
     * for Fragment-to-Fragment communication, its ok to call on this class explicitly.
     *
     * @param requestCode  This is the code used on the intent call that would identify it.
     * @param resultCode This is a generic settable status update of OK or Cancelled
     * @param carrierIntent holds the extras
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent carrierIntent)
    {
        Log.d(LOG_TAG, "onActivityResult() called");

        if (resultCode != Activity.RESULT_OK)
            return;

        if (carrierIntent != null)
        {
            if (carrierIntent.hasExtra(CrimeFragment.ALTERED_CRIME))
                mChangedCrimeID = (UUID) carrierIntent.getSerializableExtra(CrimeFragment.ALTERED_CRIME);
            else if (carrierIntent.hasExtra(CrimeFragment.DELETE_CRIME))
            {
                mChangedCrimeID = (UUID) carrierIntent.getSerializableExtra(CrimeFragment.DELETE_CRIME);
                CrimeLab crimeLab = CrimeLab.getInstance(getActivity());  //pass in the context to the CrimeLab instance constructor
                mRemovedCrimeIndex = crimeLab.getCrimes().indexOf(CrimeLab.getCrime(mChangedCrimeID));
                crimeLab.removeCrime(CrimeLab.getCrime(mChangedCrimeID));
            }
        }
    }

    /**
     * Save the state of the subtitles on rotation.
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }
}