package bd.sm.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton, mFalseButton,mNextButton,mPreviousButton,mCheatButton;
    private TextView mQuestionTextView;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_bangladesh, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        // Set text question on first question on zero index
        mQuestionTextView = findViewById(R.id.question_text_view);
        /*int question=mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);*/
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mPreviousButton = findViewById(R.id.previous_button);
        mNextButton = findViewById(R.id.next_button);
        mCheatButton = findViewById(R.id.cheat_button);
        Log.d(TAG, "Current question index: " + mCurrentIndex);
        if (savedInstanceState !=null)
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);

        updateQuestion();

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    Toast.makeText(QuizActivity.this, "No Previous Question", Toast.LENGTH_SHORT).show();
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
                    updateQuestion();
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mCurrentIndex + 1) == mQuestionsBank.length) {
                    Toast.makeText(QuizActivity.this, "This is last question ", Toast.LENGTH_SHORT).show();
                } else {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                    mIsCheater = false;
                    updateQuestion();
                }
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue=mQuestionsBank[mCurrentIndex].isAnswerTrue();
                Intent intent=CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                //startActivity(intent);
                /*mCheatButton.setText(String.valueOf(answerIsTrue));
                mCheatButton.setBackgroundColor(Color.RED);*/
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });
    }

    private void updateQuestion() {
        // Log a message at "debug" log level
        Log.d(TAG, "Updating question text", new Exception());
        try {
            int question = mQuestionsBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
        }catch (ArrayIndexOutOfBoundsException ex){
            // Log a message at "error" log level, along with an exception stack trace
            Log.e(TAG, "Index was out of bounds", ex);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        //Log.i("Test", String.valueOf(mIsCheater));
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            //Log.i("test1", String.valueOf(mIsCheater));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(Bundle) called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume(Bundle) called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause(Bundle) called");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG,"onSaveInstanceState called");
        saveInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(Bundle) called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy(Bundle) called");
    }




}