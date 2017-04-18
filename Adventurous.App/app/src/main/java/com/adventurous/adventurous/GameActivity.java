package com.adventurous.adventurous;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class GameActivity extends AppCompatActivity implements IOnAnswerSelectionChangeListener {

    private Point mPoint;
    private TextView mHintTextView;
    private int mAnswerPosition = 0;
    private TextView mTimeUntilNextHintTextView;
    private int mLastHintPosition = 0;
    private final int MINUTES_UNTIL_NEXT_HINT = 1;
    private final int MINUTES_UNTIL_NEXT_ANSWER_ATTEMPT = 1;
    private Button mApproveButton;
    private Point[] mPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);

        mPoints = (Point[])getIntent().getSerializableExtra("POINTS");
        UUID id = (UUID) getIntent().getSerializableExtra("ID");

        for (Point p : mPoints){
            if( p.id.equals(id)){
                mPoint = p;
            }
        }


        mHintTextView = (TextView) findViewById(R.id.game_hint_text_view);
        mHintTextView.setText(mPoint.hints[mLastHintPosition]);
        mHintTextView.setMovementMethod(new ScrollingMovementMethod());

        mTimeUntilNextHintTextView =(TextView) findViewById(R.id.game_time_until_next_hint_text_view);

        FragmentManager fm = getFragmentManager();
        final AnswersFragment answersFragment = (AnswersFragment) fm.findFragmentById(R.id.game_answers_fragment);
        answersFragment.Answers = mPoint.answers;

        if(mLastHintPosition < mPoint.hints.length - 1){
            startHintTimerCountdown();
        }else{
            mTimeUntilNextHintTextView.setText("");
        }

        mApproveButton = (Button) findViewById(R.id.game_approve_button);

        mApproveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPoint.rigthAnswerId == mAnswerPosition) {

                    Intent endGameActivity = new Intent(GameActivity.this, EndGameActivity.class);
                    endGameActivity.putExtra("POINTS", mPoints);
                    endGameActivity.putExtra("ID", mPoint.id);
                    startActivity(endGameActivity);
                    finish();
                    return;

                } else {
                    Toast.makeText(GameActivity.this, "Nepataikei :( " + MINUTES_UNTIL_NEXT_ANSWER_ATTEMPT + " minutes negalėsi spėti", Toast.LENGTH_LONG).show();
                    answersFragment.disableLastAnswer();
                    mApproveButton.setEnabled(false);
                    startAnswerTimerCountdown();
                }
            }
        });
    }

    private void startAnswerTimerCountdown() {
        new CountDownTimer(1000 * 60 * MINUTES_UNTIL_NEXT_ANSWER_ATTEMPT, 1000) {

            public void onTick(long millisUntilFinished) {
                mApproveButton.setText(Format(millisUntilFinished / 1000));
            }

            public void onFinish() {
                mApproveButton.setEnabled(true);
                mApproveButton.setText("Patvirtinti");
            }

        }.start();
    }

    private void startHintTimerCountdown() {
        new CountDownTimer(1000 * 60 * MINUTES_UNTIL_NEXT_HINT, 1000) {

            public void onTick(long millisUntilFinished) {
                mTimeUntilNextHintTextView.setText("Iki pagalbos liko\n" + Format(millisUntilFinished / 1000));
            }

            public void onFinish() {
                mLastHintPosition++;
                mHintTextView.setText(mHintTextView.getText() + "\n"+ mPoint.hints[mLastHintPosition]);
                if(mLastHintPosition < mPoint.hints.length - 1){
                    startHintTimerCountdown();
                }else{
                    mTimeUntilNextHintTextView.setText("");
                }
            }

        }.start();
    }

    @Override
    public void OnAnswerSelect(int position) {
        mAnswerPosition = position;
    }

    public String Format(long time) {
        long hours = time / 3600;
        long minutes = time / 60 % 60;
        long seconds = time % 60;
        if(hours > 0){
            return String.format("%d:%02d:%02d", hours, minutes , seconds);
        }

        return String.format("%02d:%02d", minutes , seconds);
    }
}
