package com.android.bignerdranch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 */
public class QuizActivity extends Activity
{
    private static final String LOG_TAG = "QuizActivity";
    private static final String BUNDLE_KEY_QUESTION_INDEX = "index";
    private static final String BUNDLE_KEY_QUESTIONS_CHEATED = "questions cheated";
    private static final int REQUEST_CODE_CHEAT = 0;  //a unique number for every activity called which should report back.

    private Button mTrueButton;  //android naming convention for member variable
    private Button mFalseButton;  //android naming convention
    private Button mNextButton;
    private Button mBackButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private boolean mIsCheater;

    private int mQuestionIndex = 0;

    //array of sting id numbers and the corresponding answer
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };
    private int[] mQuestionsCheated = new int[mQuestionBank.length];

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate(Bundle) called");

        //Checks if there is a saved question position we should start with.
        if (savedInstanceState != null)
        {
            mQuestionIndex = savedInstanceState.getInt(BUNDLE_KEY_QUESTION_INDEX, 0);  //default value if null for some reason.
            mQuestionsCheated = savedInstanceState.getIntArray(BUNDLE_KEY_QUESTIONS_CHEATED);
        }

        setContentView(R.layout.activity_quiz);

        //initialize
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);  //this references the resource Id we forced created with @+id/ in the xml
        mFalseButton = (Button) findViewById(R.id.false_button); //this references the resource Id we forced and named in the xml
        mNextButton = (Button) findViewById(R.id.next_button);
        mBackButton = (Button) findViewById(R.id.back_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);

        //set onClickListeners for all buttons
        mFalseButton.setOnClickListener(new FalseButtonListener());  //option 1 using an inner class: useful if the methods within are long
        mQuestionTextView.setOnClickListener(new NextQuestionListener());
        mNextButton.setOnClickListener(new NextQuestionListener());
        mBackButton.setOnClickListener(new PreviousQuestionListener());
        mCheatButton.setOnClickListener(new CheatButtonListener());

        mTrueButton.setOnClickListener(new View.OnClickListener()
        {  //option 2 using an anonymous inner class
            @Override
            public void onClick(View view)
            {
                checkAnswer(true);
            }
        });

        //Display the first question
        updateDisplayQuestion();
    }

    class FalseButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            checkAnswer(false);
        }
    }

    class NextQuestionListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            //Limits the NEXT index within 0 to length of the questionBank by using mod.
            mQuestionIndex = (mQuestionIndex + 1) % mQuestionBank.length;
            //display the next question
            updateDisplayQuestion();
        }
    }

    class PreviousQuestionListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            //Limits the NEXT index within 0 to length of the questionBank by using mod.
            if (mQuestionIndex != 0)
            {
                mQuestionIndex = (mQuestionIndex - 1) % mQuestionBank.length;
            }
            //display the next question
            updateDisplayQuestion();
        }
    }

    class CheatButtonListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            boolean answerIsTrue = mQuestionBank[mQuestionIndex].isAnswerTrue();
            //encapsulates the implementation details of CheatActivity and extras
            Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
            //The power of this is, you may want to do certain actions depending on who is calling back.
            startActivityForResult(intent, REQUEST_CODE_CHEAT);  //any activity called this way will call back with the code sent identifying it, an Activity constant, and a new intent
        }
    }

    /**
     * Display the next question
     */
    private void updateDisplayQuestion()
    {
        //the resourceId or literal text can be passed to setText(..)
        int question = mQuestionBank[mQuestionIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    /**
     * Check if the user cheated before checking if they are right or wrong
     *
     * @param userPressedTrue
     */
    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mQuestionIndex].isAnswerTrue();
        int messageResId;

        if (mQuestionsCheated[mQuestionIndex] == 1)
            messageResId = R.string.judgment_toast;
        else
        {
            if (userPressedTrue == answerIsTrue)
            {
                messageResId = R.string.correct_toast;
            } else
                messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Set what must be saved in here.
     * This is called BEFORE onStop() and onDestroy() but AFTER onPause()
     * Bundles typically have key - value pairs (values are of primitive types and classes that
     * implement serializable interfaces). Custom classes are not advised.
     *
     * @param savedInstanceSate
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceSate)
    {
        super.onSaveInstanceState(savedInstanceSate);
        Log.i(LOG_TAG, "onSaveInstanceState called");
        savedInstanceSate.putInt(BUNDLE_KEY_QUESTION_INDEX, mQuestionIndex);
        savedInstanceSate.putIntArray(BUNDLE_KEY_QUESTIONS_CHEATED, mQuestionsCheated);
    }

    /**
     * This method is called by the OS's ActivityManager once the class called via startActivityForResult(intent,request_code) leaves the stack
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        Log.d(LOG_TAG, "onActivityResult() called");

        if (resultCode != Activity.RESULT_OK)  //did it return ok or cancelled?
            return;

        if (requestCode == REQUEST_CODE_CHEAT)  //is this the CheatActivity?
            if (intent == null)
                return;

        //let Cheat activity unpack the intent it packaged. Encapsulate the process.
        mIsCheater = CheatActivity.wasAnswerShown(intent);
        if (mIsCheater && mQuestionsCheated[mQuestionIndex] != 1)
            mQuestionsCheated[mQuestionIndex] = 1;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(LOG_TAG, "onStart() called");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(LOG_TAG, "onPause() called");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(LOG_TAG, "onResume() called");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(LOG_TAG, "onStop() called");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy() called");
    }
}