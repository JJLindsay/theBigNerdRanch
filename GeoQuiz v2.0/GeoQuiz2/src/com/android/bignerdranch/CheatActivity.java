package com.android.bignerdranch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by J.Lindsay on 28.07.2016.
 */
public class CheatActivity extends Activity
{
    private static final String INTENT_EXTRA_KEY_CORRECT_ANSWER = "com.android.bignerdranch.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.android.bignerdranch.geoquiz.answer_shown";
    private static String BUNDLE_KEY_CHEATER = "cheater";
    private boolean mAnswerIsTrue;
    private boolean mAnswerShown;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null)
            if (savedInstanceState.getBoolean(BUNDLE_KEY_CHEATER, false))
                setAnswer(true);

        mAnswerIsTrue = getIntent().getBooleanExtra(INTENT_EXTRA_KEY_CORRECT_ANSWER, false);  //get this extra or the default value (listed here as false) if null

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);

        mShowAnswer.setOnClickListener(new ShowAnswerBtnListener());
    }

    //Creates intents specific to this activity, even Context could be removed and made fixed like CheatActivity.class
    public static Intent newIntent(Context nextActivityPackage, boolean correctAnswer)
    {
        //the next Activity package and the java class appended with .class
        Intent intent = new Intent(nextActivityPackage, CheatActivity.class);
        intent.putExtra(INTENT_EXTRA_KEY_CORRECT_ANSWER, correctAnswer);

        return intent;
    }

    class ShowAnswerBtnListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            //if the correct answer is true, show text "true" which happens to be the words on the true/false buttons
            if (mAnswerIsTrue)
                mAnswerTextView.setText(R.string.true_button);
            else
                mAnswerTextView.setText(R.string.false_button);

            setAnswer(true);
        }
    }

    private void setAnswer(boolean isAnswerShown)
    {
        mAnswerShown = isAnswerShown;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        //if the answer is never shown Activity.RESULT_CANCELLED is returned by default automatically
        setResult(Activity.RESULT_OK, intent);  //there are two standard responses: result_ok and result_cancelled. Intent optional
    }

    /**
     * Unpack the intent extras and report the results
     *
     * @param intent
     * @return
     */
    public static boolean wasAnswerShown(Intent intent)
    {
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);  //get extra but if null get default
    }

    /**
     * This is where anything that needs saving is placed.
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean(BUNDLE_KEY_CHEATER, mAnswerShown);
    }
}