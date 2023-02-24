package com.championclub_balirmath.com.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import com.championclub_balirmath.com.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.bell_1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }
}
