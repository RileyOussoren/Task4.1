package com.rileyoussoren.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button stopButton;
    EditText setEntry;
    EditText workoutEntry;
    EditText restEntry;
    TextView workoutTimer;
    TextView restTimer;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        setEntry = findViewById(R.id.setsEntry);
        workoutEntry = findViewById(R.id.workoutEntry);
        restEntry = findViewById(R.id.restEntry);
        workoutTimer = findViewById(R.id.workoutTimer);
        restTimer = findViewById(R.id.restTimer);
        progress = findViewById(R.id.progressBar);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String setsValue = setEntry.getText().toString();
                int sets = Integer.parseInt(setsValue);

                String workoutValue = workoutEntry.getText().toString();
                long workout = Long.parseLong(workoutValue);

                String restValue = restEntry.getText().toString();
                long rest = Long.parseLong(restValue);

                //int workoutInterval = 100/(Integer.parseInt(workoutValue));
                //int restInterval = 100/(Integer.parseInt(restValue));





                for(long i = 0; i < sets; i++){


                    Runnable r = new Runnable() {
                        @Override
                        public void run(){

                            CountDownTimer cdtw = new CountDownTimer((workout * 1000), 1000) {

                                int workoutInterval = 100 / (Integer.parseInt(workoutValue));
                                int workoutProgress = 0;
                                int restInterval = 100 / (Integer.parseInt(restValue));
                                int restProgress = 0;

                                public void onTick(long millisUntilFinished) {
                                    workoutTimer.setText("" + millisUntilFinished / 1000);
                                    workoutProgress = workoutProgress + workoutInterval;
                                    progress.setProgress(workoutProgress);
                                }

                                public void onFinish() {
                                    workoutTimer.setText("0");

                                    CountDownTimer cdtr = new CountDownTimer((rest * 1000), 1000) {
                                        public void onTick(long millisUntilFinishedRest) {
                                            restTimer.setText("" + millisUntilFinishedRest / 1000);
                                            restProgress = restProgress + restInterval;
                                            progress.setProgress(restProgress);
                                        }

                                        public void onFinish() {
                                            restTimer.setText("0");
                                        }
                                    }.start();
                                }


                            }.start();

                            stopButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    cdtw.cancel();
                                    workoutTimer.setText(0);
                                }
                            });

                        }
                    };


                    Handler h = new Handler();
                    h.postDelayed(r, (((workout*1000)+(rest*1000)+100)*i));




                }


            }
        });





    }
}