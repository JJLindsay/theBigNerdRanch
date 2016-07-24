package android.bignerdranch.com;

import android.app.Activity;
import android.os.Bundle;
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
    private Button mTrueButton;  //android naming convention for member variable
    private Button mFalseButton;  //android naming convention
    private Button mNextButton;
    private Button mBackButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;

    //array of sting id numbers and the corresponding answer
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //initialize
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);  //this references the resource Id we forced created with @+id/ in the xml
        mFalseButton = (Button) findViewById(R.id.false_button); //this references the resource Id we forced and named in the xml
        mNextButton = (Button) findViewById(R.id.next_button);
        mBackButton = (Button) findViewById(R.id.back_button);

        //set onClickListeners for all buttons
        mFalseButton.setOnClickListener(new FalseButtonListener());  //option 1 using an inner class: useful if the methods within are long
        mQuestionTextView.setOnClickListener(new NextQuestionListener());
        mNextButton.setOnClickListener(new NextQuestionListener());
        mBackButton.setOnClickListener(new PreviousQuestionListener());

        mTrueButton.setOnClickListener(new View.OnClickListener()
        {  //option 2 using an anonymous inner class
            @Override
            public void onClick(View view)
            {
                checkAnswer(true);
            }
        });

        //call update if the user answers the question right or wrong
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
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateDisplayQuestion();
        }
    }

    class PreviousQuestionListener implements View.OnClickListener{

        @Override
        public void onClick(View v)
        {
            //Limits the NEXT index within 0 to length of the questionBank by using mod.
            if (mCurrentIndex != 0)
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;

            updateDisplayQuestion();
        }
    }

    private void updateDisplayQuestion()
    {
        //the resourceId or literal text can be passed to setText(..)
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue)
        {
            messageResId = R.string.correct_toast;
        } else
            messageResId = R.string.incorrect_toast;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}