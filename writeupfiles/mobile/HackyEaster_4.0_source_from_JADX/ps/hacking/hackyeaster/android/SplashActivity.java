package ps.hacking.hackyeaster.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    /* renamed from: ps.hacking.hackyeaster.android.SplashActivity.1 */
    class C00861 implements Runnable {
        C00861() {
        }

        public void run() {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, Activity.class));
            SplashActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0085R.layout.splash);
        new Handler().postDelayed(new C00861(), 1000);
    }
}
