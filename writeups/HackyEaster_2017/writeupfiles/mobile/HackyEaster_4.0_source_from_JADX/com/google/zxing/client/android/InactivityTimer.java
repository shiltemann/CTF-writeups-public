package com.google.zxing.client.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

public final class InactivityTimer {
    private static final long INACTIVITY_DELAY_MS = 300000;
    private static final String TAG;
    private Runnable callback;
    private final Context context;
    private Handler handler;
    private boolean onBattery;
    private final BroadcastReceiver powerStatusReceiver;
    private boolean registered;

    private final class PowerStatusReceiver extends BroadcastReceiver {

        /* renamed from: com.google.zxing.client.android.InactivityTimer.PowerStatusReceiver.1 */
        class C00341 implements Runnable {
            final /* synthetic */ boolean val$onBatteryNow;

            C00341(boolean z) {
                this.val$onBatteryNow = z;
            }

            public void run() {
                InactivityTimer.this.onBattery(this.val$onBatteryNow);
            }
        }

        private PowerStatusReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                InactivityTimer.this.handler.post(new C00341(intent.getIntExtra("plugged", -1) <= 0));
            }
        }
    }

    static {
        TAG = InactivityTimer.class.getSimpleName();
    }

    public InactivityTimer(Context context, Runnable callback) {
        this.registered = false;
        this.context = context;
        this.callback = callback;
        this.powerStatusReceiver = new PowerStatusReceiver();
        this.handler = new Handler();
    }

    public void activity() {
        cancelCallback();
        if (this.onBattery) {
            this.handler.postDelayed(this.callback, INACTIVITY_DELAY_MS);
        }
    }

    public void start() {
        registerReceiver();
        activity();
    }

    public void cancel() {
        cancelCallback();
        unregisterReceiver();
    }

    private void unregisterReceiver() {
        if (this.registered) {
            this.context.unregisterReceiver(this.powerStatusReceiver);
            this.registered = false;
        }
    }

    private void registerReceiver() {
        if (!this.registered) {
            this.context.registerReceiver(this.powerStatusReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            this.registered = true;
        }
    }

    private void cancelCallback() {
        this.handler.removeCallbacksAndMessages(null);
    }

    private void onBattery(boolean onBattery) {
        this.onBattery = onBattery;
        if (this.registered) {
            activity();
        }
    }
}
