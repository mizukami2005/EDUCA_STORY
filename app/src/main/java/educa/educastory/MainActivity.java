package educa.educastory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AnswerDialogFragment.OnChoiceListener {
    private static final String TAG = MainActivity.class.getName();
    private static final String KEY_MODE = "MODE";
    private static final String KEY_SCORE = "SCORE";
    private static final String KEY_ANSWER = "ANSWER";

    private int mMode;
    private int mScore;
    private int mAnswer;

    private ImageView image;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image_view);
        text = (TextView) findViewById(R.id.text_view);
        setTitle(R.string.lesson);

        text.setOnClickListener(new TextClickListener());

        if (savedInstanceState == null) {
            mMode = 0;
            mScore = 0;
            mAnswer = 0;
        } else {
            mMode = savedInstanceState.getInt(KEY_MODE, 0);
            mScore = savedInstanceState.getInt(KEY_SCORE, 0);
            mAnswer = savedInstanceState.getInt(KEY_ANSWER, 0);
        }

        changeMode();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt(KEY_MODE, 0);
        mScore = savedInstanceState.getInt(KEY_SCORE, 0);
        mAnswer = savedInstanceState.getInt(KEY_ANSWER, 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_MODE, mMode);
        outState.putInt(KEY_SCORE, mScore);
        outState.putInt(KEY_ANSWER, mAnswer);
    }

    private void changeMode() {
        switch (mMode) {
            case 0:
                image.setImageDrawable(null);
                text.setText(getString(R.string.first_narration));
                text.setLines(10);
                break;
            case 1:
                image.setImageResource(R.mipmap.question1);
                text.setText(getString(R.string.question1));
                text.setLines(5);
                break;
            case 2:
                AnswerDialogFragment.newInstance(R.string.answer1_1, R.string.answer1_2)
                        .show(getSupportFragmentManager(), "question");
                break;
            case 3:
                if (mAnswer == 1) {
                    image.setImageResource(R.mipmap.reaction1_1);
                    text.setText(getString(R.string.reaction1_1));
                } else {
                    image.setImageResource(R.mipmap.reaction1_2);
                    text.setText(getString(R.string.reaction1_2));
                }
                break;
            case 4:
                image.setImageResource(R.mipmap.question2);
                text.setText(getString(R.string.question2));
                break;
            case 5:
                AnswerDialogFragment.newInstance(R.string.answer2_1, R.string.answer2_2)
                        .show(getSupportFragmentManager(), "question");
                break;
            case 6:
                switch (mAnswer) {
                    case 1:
                        image.setImageResource(R.mipmap.reaction2_1);
                        text.setText(getString(R.string.reaction2_1));
                        break;
                    default:
                        image.setImageResource(R.mipmap.reaction2_2);
                        text.setText(getString(R.string.reaction2_2));
                        break;
                }
                break;
            case 7:
                image.setImageResource(R.mipmap.last_message);
                text.setText(getString(R.string.last_message));
                break;
            case 8:
                switch (mScore) {
                    case 0:
                        image.setImageResource(R.mipmap.narration_not_happy);
                        text.setText(getString(R.string.narration1));
                        break;
                    case 1:
                        image.setImageResource(R.mipmap.narration_happy);
                        text.setText(getString(R.string.narration2));
                        break;
                    case 2:
                        image.setImageResource(R.mipmap.narration_happy);
                        text.setText(getString(R.string.narration3));
                        break;
                    default:
                        image.setImageResource(R.mipmap.narration_so_happy);
                        text.setText(getString(R.string.narration4));
                        break;
                }
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    public void onChoice(int answer) {
        Log.d(TAG, "mode = " + mMode + ", answer = " + answer);
        switch (mMode) {
            case 2:
                mScore += (answer == 1 ? 1 : 0);
                break;
            case 5:
                mScore += (answer == 1 ? 0 : 2);
                break;
        }
        mAnswer = answer;
        mMode++;
        changeMode();
    }

    private class TextClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mMode++;
            changeMode();
        }
    }
}
