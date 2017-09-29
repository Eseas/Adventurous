package com.adventurous.adventurous.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adventurous.adventurous.Entities.Point;
import com.adventurous.adventurous.R;

import java.util.UUID;

public class EndGameActivity extends AppCompatActivity {

    private Point[] mPoints;
    private Point mPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        TextView timeTextView = (TextView) findViewById(R.id.end_game_time_text_view);
        timeTextView.setText("Sveikiname baigus žaidimą!");

        mPoints = (Point[])getIntent().getSerializableExtra("POINTS");
        UUID id = (UUID) getIntent().getSerializableExtra("ID");

        for (Point p : mPoints){
            if( p.id.equals(id)){
                mPoint = p;
            }
        }

        Button startButton = (Button) findViewById(R.id.end_game_start_game);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pointsActivity = new Intent(EndGameActivity.this, PointsActivity.class);
                pointsActivity.putExtra("POINTS", mPoints);
                startActivity(pointsActivity);

                finish();
            }
        });
    }
}
