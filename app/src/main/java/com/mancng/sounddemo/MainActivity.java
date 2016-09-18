package com.mancng.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    SeekBar volumeBar;
    SeekBar mediaBar;

    public void playMusic (View view) {
        mediaPlayer.start();
    }

    public void pauseMusic (View view) {
        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mediaPlayer = MediaPlayer.create(this, R.raw.infinity);

        //Establish the max volume from the device
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Get max volume from the device. STREAM_MUSIC is the music media volume)
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //Get current volume
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeBar = (SeekBar) findViewById(R.id.barVolume);
        //Associate the max volume from the device to the seekbar
        volumeBar.setMax(maxVolume);
        //Set current value to the seekbar
        volumeBar.setProgress(curVolume);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("SeekBar value", Integer.toString(progress));
                audioManager.setStreamVolume(audioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mediaBar = (SeekBar) findViewById(R.id.seekBar);
        mediaBar.setMax(mediaPlayer.getDuration());


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mediaBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0,1000) ;

        mediaBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("mediaBar value", Integer.toString(progress));

                if(mediaPlayer !=null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void muteMusic (View view) {

        audioManager.setStreamVolume(audioManager.STREAM_MUSIC, 0, 0);
        volumeBar.setProgress(0);
    }

    public void loudMusic (View view) {

        audioManager.setStreamVolume(audioManager.STREAM_MUSIC,100, 0);
        volumeBar.setProgress(100);
    }
}

