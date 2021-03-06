package net.jaywolf.geoquiz;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/* The QuizActivity class is a CONTROLLER object. Controller objects contain the "application
 * logic." They are designed to respond to various events triggered by view objects and to manage
 * the flow of data from model objects and the view layer. Controller objects are typically a
 * subclass of Activity, Fragment, or Service classes.
 * - Android Programming: The Big Nerd Ranch Guide (Phillips, Stewart, Hardy, and Marsicano) */

public class QuizActivity extends AppCompatActivity {
// QuizActivity manages all layout elements and interactions for the specific activity.
// AppCompatActivity provides compatibility support for older versions of Android.

    // define tag (label) for activity's log
    public static final String TAG = "QuizActivity";

    // define persistent storage variable
    private static final String KEY_INDEX = "index";

    // define member (instance) variables (these are the widgets we will be interacting with);
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;
    // 'm' prefix on member variables = std Android naming convention;
    // used for Android Studio's auto-generation feature

    // Create and populate Question array with question text and correct answer
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    // counter for Question array
    private int mCurrentIndex = 0;

    // function to update mQuestionTextView variable (reduces duplicated code)
    private void updateQuestion() {
        // get the resource ID of the text in the Question array element at whatever the
        // current index is and store it as the question variable
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        // set the text stored in the question variable as the text in the text view widget
        mQuestionTextView.setText(question);
    }

    // answer checker for Question array
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResID = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResID = R.string.correct_toast;
        } else {
            messageResID = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
        /* Toasts = pop-up messages that appear on the screen for a defined time and don't require
         * any interaction; method of the Toast class;
         * Format: makeText(Context context, int resID, int duration);
         * - Context = instance of Activity; Activity = subclass of Context;
         * - used to find text string's resource ID;
         * - Context in this case is QuizActivity, and "this" refers to View.OnClickListener
         *   in the anonymous inner class we are defining */
    }

    // function to display the next question in the mQuestionBank array
    private void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
    }

    @Override
    // @Override asks the compiler to ensure that the class actually has the method that we are
    // attempting to override; throws an error if the method does not exist.
    protected void onCreate(Bundle savedInstanceState) {
        /* onCreate(Bundle) method called when instance of activity subclass created;
         * Prepares specifics of the user interface:
         * - inflates widgets and puts them on the screen (via setContentView(R.layout.resourceID)
         *     inflation = each widget in layout instantiated as defined by its attributes
         * - gets references to inflated widgets
         * - sets listeners on widgets to handle user interaction
         * - connects to external model data */
        super.onCreate(savedInstanceState);
        // must call superclass of onCreate() (and each other lifecycle stage) before doing anything
        // else b/c we are overriding the superclass' implementation with our own

        // add log message to the debugging log
        Log.d(TAG, "onCreate(Bundle) called");
        /* Log.d is an implementation of `public static int d(String tag, String msg)`
         * d = "debug" level of logging; can be filtered out
         * e = errors
         * w = warnings
         * i = informational messages
         * v = verbose logging (for development purposes only!) */

        /* View (layout) objects know how to draw themselves on the screen and how to respond to
         * user input, like touches. Essentially, if you can see it on the screen, it is probably a
         * view. The Android views are highly customizable, or you can create custom view classes.
         * aka the widgets that are inflated from the activities. All view objects in an app =
         * the view layer.
         * - Android Programming, The Big Nerd Ranch Guide (Phillips, Stewart, Hardy, and Marsicano)*/
        setContentView(R.layout.activity_quiz);
        /* setContentView() = Activity method;
         * An activity needs a user interface (layout) to manage;
         * Specify layout to inflate by passing layout's resource ID
         *   (aka get resource ID of the activity for the user interface to manage);
         * Inflates a layout and puts it on the screen */

        // Add the text view for the Question array
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        // gets the resource ID for the for the question text view widget

        // make mQuestionTextView clickable
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        // check saved instance state for value from a previous instance of the activity
        // if it exists, assign it to mCurrentIndex
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        // get question text from the QuestionBank array to display
        updateQuestion();

        // Get the resource ID to the inflated True button widget
        mTrueButton = (Button) findViewById(R.id.true_button);
        // Decide what to do when the [True] button is pressed
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        /* Listeners respond to events in an activity and implement a listener interface for the
         * event; implemented as an anonymous inner class so the implementation of the listener's
         * method is easily seen;
         * This listener reacts when the [True] button is pressed;
         * Notice parentheses aren't closed in the setOnClickListener method call b/c we are
         * creating a new, nameless class of OnClickListener and passing its entire implementation
         * to the listener; OnClickListener class only has one method, onClick(View); can be empty
         * b/c compiler only requires method is implemented but doesn't care how */

        // functionality for [False] button
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        // functionality for [Next] button
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        // functionality for [Previous] button
        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    // write current index value (mCurrentIndex) to persistent storage
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    // logging methods for each lifecycle activity
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
