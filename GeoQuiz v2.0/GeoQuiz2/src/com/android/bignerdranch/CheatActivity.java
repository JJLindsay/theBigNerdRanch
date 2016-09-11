package com.android.bignerdranch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This is a sub-Activity triggered by the Main Activity: QuizActivity.java
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
        setContentView(R.layout.activity_cheat);  //sets the view you will use and reference in this class

        if (savedInstanceState != null)
            if (savedInstanceState.getBoolean(BUNDLE_KEY_CHEATER, false))
                setAnswer(true);

        mAnswerIsTrue = getIntent().getBooleanExtra(INTENT_EXTRA_KEY_CORRECT_ANSWER, false);  //get this extra or the default value (listed here as false) if null

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);

        mShowAnswer.setOnClickListener(new ShowAnswerBtnListener());
    }

    //Creates intents specific to this activity. nextActivityPackage could be static in this case
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                int cx = mShowAnswer.getWidth() / 2;
                int cy = mShowAnswer.getHeight() / 2;
                float radius = mShowAnswer.getWidth();
                Animator animator = ViewAnimationUtils.createCircularReveal(mAnswerTextView, cx, cy, radius, 0);
                animator.addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        mAnswerTextView.setVisibility(View.VISIBLE);
                        mShowAnswer.setVisibility(View.VISIBLE);
                    }
                });
                animator.start();
            }
            else
            {
                mAnswerTextView.setVisibility(View.VISIBLE);
                mShowAnswer.setVisibility(View.VISIBLE);
            }
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