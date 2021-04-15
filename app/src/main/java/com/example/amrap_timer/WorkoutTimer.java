package com.example.amrap_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WorkoutTimer extends AppCompatActivity {

    //required variables for the program
    private Button start_Pause_Button;
    private Button stopButton;
    private TextView totalTimerText;
    private TextView cycleTimerText;
    private TextView cycleCounterText;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownCycleTimer;
    private boolean runningTimer = false;
    private boolean cycleSwitch = false;
    private int totalCycles = 0;
    private long totalTimerAmount = 0;
    private long timeIntervalOneInMilliseconds = 0;
    private long timeIntervalTwoInMilliseconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_timer);

        //assigning visual objects to the variables to be used in the code
        start_Pause_Button = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        totalTimerText = findViewById(R.id.main_Timer);
        cycleTimerText = findViewById(R.id.cycleTimer);
        cycleCounterText = findViewById(R.id.cycleCounter);





        timeIntervalOneInMilliseconds = getIntent().getLongExtra("timeIntervalOne",0);
        timeIntervalTwoInMilliseconds = getIntent().getLongExtra("timeIntervalTwo",0);
        totalCycles = getIntent().getIntExtra("TotalCycles", 0);
        totalTimerAmount = getIntent().getLongExtra("TotalTime", 0);

        //set up TimerText with the correct duration
        updateTimer();


        start_Pause_Button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                startPause();
            }
        });


    }
    public void startPause()
    {
        if(runningTimer)
        {
            PauseTimer();
        }else
            StartTimer();
    }
    public void switchInterval()
    {
        if(cycleSwitch)
        {
            cycleSwitch = false;
        }else
            cycleSwitch = true;

    }


    public void StartTimer()
    {
        runningTimer = true;
        countDownTimer =new CountDownTimer(totalTimerAmount, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                totalTimerAmount = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {


            }
        }.start();
        start_Pause_Button.setText("Pause");
        runningTimer = true;

    }

    public void PauseTimer()
    {
        runningTimer = false;
        start_Pause_Button.setText("Start");
        countDownTimer.cancel();
    }

    //to update the textview
    public void updateTimer()
    {
        int minutes =  (int) totalTimerAmount / 60000;
        int seconds = (int) totalTimerAmount % 60000/1000;
        String timeLeftString = " ";
        timeLeftString = "" + minutes + ":";
        if(seconds<10) timeLeftString+="0";
        timeLeftString+=seconds;

        totalTimerText.setText(timeLeftString);

    }



}


