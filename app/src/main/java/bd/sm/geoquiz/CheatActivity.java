package bd.sm.geoquiz;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE ="bd.sm.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN ="bd.sm.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;

    public static Intent newIntent(Context packageContext,Boolean answerIsTrue){
        Intent intent =new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        Log.i("test",EXTRA_ANSWER_IS_TRUE);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView=findViewById(R.id.answer_text_view);
        final Button showAnswerButton = findViewById(R.id.show_answer_button);

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else {
                    mAnswerTextView.setText(R.string.false_button);
                }
            }
        });
        setAnswerShownResult(true);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //int cx = showAnswerButton.getWidth() / 2;
            //int cy = showAnswerButton.getHeight() / 2;
            //float radius = showAnswerButton.getWidth();
            //Log.i("Test11", String.valueOf(cx));
            Animator anim = ViewAnimationUtils.createCircularReveal(showAnswerButton, 0, 0, 12, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    showAnswerButton.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            showAnswerButton.setVisibility(View.INVISIBLE);
        }*/
    }

    private void setAnswerShownResult(Boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
