package com.dreamdigitizers.drugmanagement.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

public class SoundUtils {
    private static MediaPlayer mediaPlayer;

    public static void playCameraShutterSound(Context pContext) {
        AudioManager audioManager = (AudioManager)pContext.getSystemService(Context.AUDIO_SERVICE);
        audioManager.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
    }

    public static void playAlertSound(Context pContext) {
        try {
            Uri uri = SoundUtils.getAlarmUri();

            final AudioManager audioManager = (AudioManager)pContext.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                if(SoundUtils.mediaPlayer == null) {
                    SoundUtils.mediaPlayer = new MediaPlayer();
                }
                SoundUtils.mediaPlayer.setDataSource(pContext, uri);
                SoundUtils.mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                SoundUtils.mediaPlayer.prepare();
                SoundUtils.mediaPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if(SoundUtils.mediaPlayer != null) {
            SoundUtils.mediaPlayer.stop();
        }
    }

    public static void reset() {
        if(SoundUtils.mediaPlayer != null) {
            SoundUtils.mediaPlayer.reset();
        }
    }

    public static void release() {
        if(SoundUtils.mediaPlayer != null) {
            SoundUtils.mediaPlayer.release();
            SoundUtils.mediaPlayer = null;
        }
    }

    private static Uri getAlarmUri() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (uri == null) {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (uri == null) {
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }

        return uri;
    }
}
