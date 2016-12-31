package com.big.nerd.ranch.ciminalIntent.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import com.big.nerd.ranch.ciminalIntent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This class inflates a support DatePicker in a new Fragment.
 * If the user enters a new date, this is sent back to CrimeFragment.
 */
public class DatePickerFragment extends DialogFragment
{
    public static final String EXTRA_DATE = "com.big.nerd.ranch.criminalIntent.DATE";

    private static final String ARG_DATE = "DATE";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);  //inflate xml

        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_date_picker);  //datePicker is in the View xml
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DateChangedListener())
                .create();
    }

    /**
     * Captures what ever was set in the DatePicker and packs it into a Date to send off.
     * onDateChangedListener is not used with mDatePicker because the user could change the date but
     * choose not to submit it, therefore updating the list would be necessary.
     */
    class DateChangedListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i)
        {
            int year = mDatePicker.getYear();
            int month = mDatePicker.getMonth();
            int day = mDatePicker.getDayOfMonth();

            Date date = new GregorianCalendar(year, month, day).getTime();

            sendResult(Activity.RESULT_OK, date);
        }
    }

    private void sendResult(int resultCode, Date date)
    {
        if (getTargetFragment() == null)
            return;

        Intent carrierIntent = new Intent();  //an intent just for carrying extras
        carrierIntent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode, carrierIntent);
    }
}