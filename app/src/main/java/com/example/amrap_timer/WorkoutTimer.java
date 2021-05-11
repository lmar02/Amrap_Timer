package com.example.amrap_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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
    private int totalCycles = 0;
    private int currentCycle= 0;
    private long totalTimerAmount = 0;
    private long timeIntervalOneInMilliseconds = 0;
    private long timeIntervalTwoInMilliseconds = 0;
    private long currentCycleTime= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_timer);

        //assigning visual objects to the variables to be used in the code
        start_Pause_Button = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        totalTimerText = findViewById(R.id.main_Timer);
        cycleTimerText = findViewById(R.id.cycleTimer);
        cycleCounterText = findViewById(R.id.cycleCounterText);

        //gets data from previous activity
        timeIntervalOneInMilliseconds = getIntent().getLongExtra("timeIntervalOne",0);
        timeIntervalTwoInMilliseconds = getIntent().getLongExtra("timeIntervalTwo",0);
        totalCycles = getIntent().getIntExtra("TotalCycles", 0);
        totalTimerAmount = getIntent().getLongExtra("TotalTime", 0);

        //set up TimerText with the correct duration
        updateTimer();
        updateCycleTimerText();

        //button to start timer
        start_Pause_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPause();
            }
        });

        //button to stop timer
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutTimer.this, MainActivity.class );
                startActivity(intent);
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

    public void StartTimer()
    {
        //set bool to true
        runningTimer = true;
        //start main timer
        startMainTimer();
        //start cycle timer
        startCycleTimer();
        //change text on button
        start_Pause_Button.setText("Pause");
    }

    public void PauseTimer()
    {
        runningTimer = false;
        start_Pause_Button.setText("Start");
        countDownTimer.cancel();
        countDownCycleTimer.cancel();
    }

    //to update the Total timer Textview
    //set up millisecond to second to text
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

    //to update the Total timer Textview
    //set up millisecond to second to text
    public void updateCycleTimerText()
    {
        int minutes = (int)currentCycleTime/60000;
        int seconds = (int)currentCycleTime % 60000/1000;
        String cycleTimeLeftString = " ";
        cycleTimeLeftString = ""+minutes+":";
        if(seconds<10) cycleTimeLeftString+="0";
        cycleTimeLeftString+=seconds;
        cycleTimerText.setText(cycleTimeLeftString);
    }
    public void updateCycleCount()
    {
        String cycleString = currentCycle + " / " + totalCycles;
        cycleCounterText.setText(cycleString);
    }

    //start main timer
    public void startMainTimer()
    {
        countDownTimer =new CountDownTimer(totalTimerAmount, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totalTimerAmount = millisUntilFinished;
                updateTimer();
            }
            @Override
            public void onFinish() {
                runningTimer = false;
            }
        }.start();
    }

    //start cycle timer
    public void startCycleTimer()
    {
        currentCycle = 1;

        if(currentCycle!=totalCycles && runningTimer == true)
        {
            if(currentCycle%2==0)
            {
                countDownCycleTimer =new CountDownTimer(timeIntervalTwoInMilliseconds, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        currentCycleTime = millisUntilFinished;
                        updateCycleTimerText();
                        updateCycleCount();
                    }
                    @Override
                    public void onFinish() {
                        currentCycle++;
                        updateCycleCount();
                        MediaPlayer beep = MediaPlayer.create(WorkoutTimer.this, R.raw.beep);
                        beep.start();
                        startCycleTimer();
                    }
                }.start();
            }
            else
                countDownCycleTimer =new CountDownTimer(timeIntervalOneInMilliseconds, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    currentCycleTime = millisUntilFinished;
                    updateCycleTimerText();
                    updateCycleCount();
                }

                @Override
                public void onFinish() {
                    currentCycle++;
                    updateCycleCount();
                    MediaPlayer beep = MediaPlayer.create(WorkoutTimer.this, R.raw.beep);
                    beep.start();
                    startCycleTimer();
                }
            }.start();
        }
    }
}


