package com.big.nerd.ranch.ciminalIntent.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.big.nerd.ranch.ciminalIntent.R;

public class DeleteAlertFragment extends DialogFragment
{

    public static DeleteAlertFragment newInstance()
    {
        DeleteAlertFragment deleteFragment = new DeleteAlertFragment();
        return deleteFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_alert_title)
                .setMessage(R.string.delete_alert_message)
                .setPositiveButton(android.R.string.ok, new DeleteListener())
                .setNegativeButton("Cancel", null)
                .create();
    }

    /**
     * Captures what ever was set in the TimePicker view object and packs it into a Calendar object to send off.
     * onTimeChangedListener is not used with the view object mTimePicker because the user could change the time but
     * choose not to submit it, therefore updating the list would not be necessary.
     */
    class DeleteListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i)
        {
            sendResult(Activity.RESULT_OK);
        }
    }

    private void sendResult(int resultCode)
    {
        if (getTargetFragment() == null)
            return;

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode, null);
    }
}