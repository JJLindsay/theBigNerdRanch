package com.big.nerd.ranch.ciminalIntent.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.big.nerd.ranch.ciminalIntent.R;
import com.big.nerd.ranch.ciminalIntent.model.Crime;
import com.big.nerd.ranch.ciminalIntent.model.CrimeLab;

import java.util.List;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 * <p/>
 * Display a list of crimes to the user with RecyclerView
 */
public class CrimeListFragment extends Fragment
{
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //defines which xml is the View
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        //find the recyclerView in the View. RecyclerView is simply a list of empty recyclable rows.
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //set the appropriate layout manager for the RecyclerView
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    /**
     * ViewHolder will now maintain a reference to a single view/row (our case is a row).
     * CrimeHolder() wires up the  views/components/widgets (they all mean the same)
     * bindCrime() sets values for the  views/components/widgets (they all mean the same)
     */
    private class CrimeHolder extends RecyclerView.ViewHolder
    {
        //        public TextView mTitleTextView;
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
//            mTitleTextView = (TextView)itemView;
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        /**
         * Sets values for the ViewHolder components
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

        mAdapter = new CrimeAdapter(crimes);  //passes in a list of crime objects to CrimeAdapter
        //set the adapter for RecyclerView to use
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Creates viewHolders (called CrimeHolders here) and binds them to data from the model layer
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
         * ViewHolder deals with all the components that will be in a given row in RecyclerView
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            //retrieves the xml that will be a template of the contents of a given row
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        /**
         * This calls bindCrime to set the values for each of the views/components/widgets (they all mean the same)
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position)
        {
            Crime crime = mCrimes.get(position);
//            holder.mTitleTextView.setText(crime.getTitle());
            holder.bindCrime(crime);
        }


        @Override
        public int getItemCount()
        {
            return mCrimes.size();
        }
    }
}
