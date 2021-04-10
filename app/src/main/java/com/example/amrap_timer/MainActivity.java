package com.example.amrap_timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    //required variables
    Button button30_15;
    Button button30_30;
    Button button1min_30;
    int timeIntervalOneInSeconds = 0;
    int timeIntervalTwoInSeconds = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link variables to layout

        button1min_30 = findViewById(R.id.button_1_30);
        button30_30 = findViewById(R.id.button_30_30);
        button30_15 = findViewById(R.id.button_30_15);

        //button30_15 press and action sequence
        button30_15.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // setting time intervals in seconds
                timeIntervalOneInSeconds = 30;
                timeIntervalTwoInSeconds = 15;
                //40 complete cycles = a 30 minutes workout or 80 alternating cycles of 30 sec and 15 sec
                //creating new intent for new activity that is being created
                Intent intent = new Intent(MainActivity.this,WorkoutTimer.class );
                // adding extra info for the next activty
                intent.putExtra("timeIntervalOne",timeIntervalOneInSeconds);
                intent.putExtra("TimeIntervalTwo", timeIntervalTwoInSeconds);


            }
        } );

        //button30_30 press and action sequence
        button30_30.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        //button1min_30 press and action sequence
        button1min_30.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });





    }
}