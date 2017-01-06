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
import android.widget.TimePicker;
import com.big.nerd.ranch.ciminalIntent.R;

import java.util.Calendar;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This class inflates a support TimePicker in a new Fragment.
 * If the user enters a new time, this is sent back to CrimeFragment.
 */
public class TimePickerFragment extends DialogFragment
{
    public static final String EXTRA_CALENDAR = "com.big.nerd.ranch.criminalIntent.TimePickerFragment.CALENDER";

    private static final String ARG_CALENDAR = "CALENDAR";

    private TimePicker mTimePicker;
    private Calendar mCalendar;

    public static TimePickerFragment newInstance(Calendar calendar)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CALENDAR, calendar);

        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.setArguments(args);

        return timeFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mCalendar = (Calendar) getArguments().getSerializable(ARG_CALENDAR);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);  //inflate xml

        //TimePicker object to read and set the TimePicker view
        mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_picker);  //timePicker is in the View xml
        mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR));
        mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.time_picker_title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new TimePickerChangedListener())
                .create();
    }

    /**
     * Captures what ever was set in the TimePicker view object and packs it into a Calendar object to send off.
     * onTimeChangedListener is not used with the view object mTimePicker because the user could change the time but
     * choose not to submit it, therefore updating the list would not be necessary.
     */
    class TimePickerChangedListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i)
        {
            //preserve the untouched data and update everything else
            mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH),
                    mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());

            sendResult(Activity.RESULT_OK, mCalendar);
        }
    }

    private void sendResult(int resultCode, Calendar calendar)
    {
        if (getTargetFragment() == null)
            return;

        Intent carrierIntent = new Intent();  //an intent just for carrying extras
        carrierIntent.putExtra(EXTRA_CALENDAR, calendar);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode, carrierIntent);
    }
}
