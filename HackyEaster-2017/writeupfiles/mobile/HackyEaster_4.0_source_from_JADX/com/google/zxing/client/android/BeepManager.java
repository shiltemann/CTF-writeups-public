package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Vibrator;
import android.util.Log;
import java.io.Closeable;
import java.io.IOException;

public final class BeepManager implements OnCompletionListener, OnErrorListener, Closeable {
    private static final float BEEP_VOLUME = 0.1f;
    private static final String TAG;
    private static final long VIBRATE_DURATION = 200;
    private final Activity activity;
    private boolean beepEnabled;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private boolean vibrateEnabled;

    static {
        TAG = BeepManager.class.getSimpleName();
    }

    public BeepManager(Activity activity) {
        this.beepEnabled = true;
        this.vibrateEnabled = false;
        this.activity = activity;
        this.mediaPlayer = null;
        updatePrefs();
    }

    public boolean isBeepEnabled() {
        return this.beepEnabled;
    }

    public void setBeepEnabled(boolean beepEnabled) {
        this.beepEnabled = beepEnabled;
    }

    public boolean isVibrateEnabled() {
        return this.vibrateEnabled;
    }

    public void setVibrateEnabled(boolean vibrateEnabled) {
        this.vibrateEnabled = vibrateEnabled;
    }

    public synchronized void updatePrefs() {
        this.playBeep = shouldBeep(this.beepEnabled, this.activity);
        if (this.playBeep && this.mediaPlayer == null) {
            this.activity.setVolumeControlStream(3);
            this.mediaPlayer = buildMediaPlayer(this.activity);
        }
    }

    public synchronized void playBeepSoundAndVibrate() {
        if (this.playBeep && this.mediaPlayer != null) {
            this.mediaPlayer.start();
        }
        if (this.vibrateEnabled) {
            ((Vibrator) this.activity.getSystemService("vibrator")).vibrate(VIBRATE_DURATION);
        }
    }

    private static boolean shouldBeep(boolean beep, Context activity) {
        boolean shouldPlayBeep = beep;
        if (!shouldPlayBeep || ((AudioManager) activity.getSystemService("audio")).getRingerMode() == 2) {
            return shouldPlayBeep;
        }
        return false;
    }

    private MediaPlayer buildMediaPlayer(Context activity) {
        AssetFileDescriptor file;
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(3);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        try {
            file = activity.getResources().openRawResourceFd(C0035R.raw.zxing_beep);
            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            file.close();
            mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            mediaPlayer.release();
            return null;
        } catch (Throwable th) {
            file.close();
        }
    }

    public void onCompletion(MediaPlayer mp) {
        mp.seekTo(0);
    }

    public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == 100) {
            this.activity.finish();
        } else {
            mp.release();
            this.mediaPlayer = null;
            updatePrefs();
        }
        return true;
    }

    public synchronized void close() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }
}
