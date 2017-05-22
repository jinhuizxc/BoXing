package com.example.administrator.boxing;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView frequency, phase, amplifier;
    private Button bt_bo;
    SinWave sw = null;

    static boolean isPlaySound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sw = new SinWave(this);
        frequency = (TextView) findViewById(R.id.frequency);
        phase = (TextView) findViewById(R.id.phase);
        amplifier = (TextView) findViewById(R.id.amplifier);

        bt_bo = (Button) findViewById(R.id.bt_bo);
        bt_bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sw.Set(Float.parseFloat(amplifier.getText().toString()),
                        Float.parseFloat(frequency.getText().toString()),
                        Float.parseFloat(phase.getText().toString()));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        frequency.setText(Float.toString(sw.GetFrequency()));
        phase.setText(Float.toString(sw.GetPhase()));
        amplifier.setText(Float.toString(sw.GetAmplifier()));

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isPlaySound = false;
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }



}