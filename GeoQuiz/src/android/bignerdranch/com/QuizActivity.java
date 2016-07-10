package android.bignerdranch.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends Activity
{
    //adding the @+id to force the creation of a Resource Id a widgets (in this case a button)
    // isn't necessary unless you need access the widget in a class, like now...
    private Button mTrueButton;  //android naming convention
    private Button mFalseButton;  //android naming convention

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button)findViewById(R.id.true_button);  //this references the resource Id we forced and named in the xml
        mFalseButton = (Button)findViewById(R.id.false_button); //this references the resource Id we forced and named in the xml

        mFalseButton.setOnClickListener(new FalseButtonListener());  //option 1 using an inner class: useful if the methods within are long
        mTrueButton.setOnClickListener(new View.OnClickListener(){  //option 2 using an anonymous inner class
            @Override
            public void onClick(View view){
                Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        });

    }

    class FalseButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();

        }
    }
}
