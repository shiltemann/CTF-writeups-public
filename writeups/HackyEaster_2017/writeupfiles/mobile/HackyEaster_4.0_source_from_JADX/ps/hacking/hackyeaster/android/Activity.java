package ps.hacking.hackyeaster.android;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.zxing.client.android.Intents.Scan;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.http.HttpHost;
import org.apache.http.protocol.HTTP;

public class Activity extends android.app.Activity implements SensorEventListener {
    private static final String ACTIVITY_MICROSCOPE = "ps.hacking.client.android.MICROSCOPE";
    private static final String SCORES_TEXT = "I'm a %s now in @HackyEaster, with a score of %s!";
    private static final String SCORES_URL = "https://hackyeaster.hacking-lab.com/hackyeaster/eggs.html?name=%s";
    private static final String SHARE_TEXT = "\nCheck out the Hacky Easter competition!\nhttps://hackyeaster.hacking-lab.com\n\n";
    private static final String SHARE_URL = "https://twitter.com/intent/tweet?text=%s&url=%s";
    private static final Uri TWITTER_URL_APP;
    private static final Uri TWITTER_URL_BROWSER;
    private static final String URL_ALERT = "ps://alert?";
    private static final String URL_AUTH = "ps://auth?";
    private static final String URL_MICROSCOPE = "ps://microscope";
    private static final String URL_MYSTATUS = "ps://mystatus?";
    private static final String URL_SCANNER = "ps://scan";
    private static final String URL_SENSORS = "ps://sensors";
    private static final String URL_SHARE = "ps://share";
    private static final String URL_STORE = "ps://store?";
    private static final String URL_TWEAKED = "ps://tweaked";
    private static final String URL_TWITTER = "ps://twitter";
    private static final Pattern eggPattern;
    protected WebView appView;
    private Sensor magnetometer;
    private boolean scannerRunning;
    private SensorManager sensorManager;

    /* renamed from: ps.hacking.hackyeaster.android.Activity.1 */
    class C00811 implements OnKeyListener {
        final /* synthetic */ Activity val$lActivity;

        C00811(Activity activity) {
            this.val$lActivity = activity;
        }

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() != 1) {
                return Activity.this.onKeyDown(keyCode, event);
            }
            if (keyCode == 4 && !Activity.this.scannerRunning) {
                String url = Activity.this.appView.getUrl();
                if (url != null && url.indexOf("/index.html") > 0) {
                    this.val$lActivity.finish();
                    return true;
                } else if (Activity.this.appView.canGoBack()) {
                    Activity.this.appView.goBack();
                }
            }
            return Activity.this.onKeyUp(keyCode, event);
        }
    }

    /* renamed from: ps.hacking.hackyeaster.android.Activity.2 */
    class C00822 extends WebViewClient {
        final /* synthetic */ Activity val$lCtx;

        C00822(Activity activity) {
            this.val$lCtx = activity;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Activity.URL_SCANNER.equals(url)) {
                Activity.this.startQrScanner();
                return true;
            } else if (url.startsWith(Activity.URL_ALERT)) {
                uri = Uri.parse(url);
                Activity.this.showAlert(uri.getQueryParameter("title"), uri.getQueryParameter("text"), this.val$lCtx);
                return true;
            } else if (url.startsWith(Activity.URL_STORE)) {
                uri = Uri.parse(url);
                int status = -1;
                if (UserData.store(this.val$lCtx, uri.getQueryParameter("name"), uri.getQueryParameter("ticket"))) {
                    status = 0;
                }
                view.loadUrl("javascript:storeFeedback('{\"status\": " + status + "}')");
                return true;
            } else if (url.startsWith(Activity.URL_AUTH)) {
                uri = Uri.parse(url);
                String email = uri.getQueryParameter(NotificationCompatApi24.CATEGORY_EMAIL);
                String pass = uri.getQueryParameter("pass");
                new HLAuthenticator(this.val$lCtx).execute(new Object[]{email, pass, view});
                return true;
            } else if (url.startsWith(Activity.URL_TWITTER)) {
                Activity.this.openTwitter();
                return true;
            } else if (url.startsWith(Activity.URL_MYSTATUS)) {
                Activity.this.shareStatusInTwitter(url, this.val$lCtx);
                return true;
            } else if (url.startsWith(Activity.URL_SHARE)) {
                Activity.this.shareUrl(this.val$lCtx);
                return true;
            } else if (url.startsWith(HttpHost.DEFAULT_SCHEME_NAME) || url.startsWith("mailto:")) {
                Activity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            } else if (url.equals(Activity.URL_SENSORS)) {
                Activity.this.startSensors();
                return true;
            } else if (url.equals(Activity.URL_TWEAKED)) {
                Activity.this.startTweakedTweet();
                return true;
            } else if (!url.equals(Activity.URL_MICROSCOPE)) {
                return false;
            } else {
                Activity.this.startMicroscope();
                return true;
            }
        }

        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:injectUserData('" + UserData.toJsonString() + "');");
        }
    }

    public Activity() {
        this.scannerRunning = false;
    }

    static {
        TWITTER_URL_APP = Uri.parse("twitter://user?screen_name=hackyeaster");
        TWITTER_URL_BROWSER = Uri.parse("https://mobile.twitter.com/hackyeaster");
        eggPattern = Pattern.compile("[0-9a-zA-Z]{20}");
    }

    public void onPause() {
        super.onPause();
        stopSensors();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity lActivity = this;
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String intentAction = intent.getAction();
            if (intent.hasCategory("android.intent.category.LAUNCHER") && intentAction != null && intentAction.equals("android.intent.action.MAIN")) {
                super.finish();
                return;
            }
        }
        UserData.load(this);
        setupUI();
        this.sensorManager = (SensorManager) getSystemService("sensor");
        setWebViewClient();
        this.appView.setOnKeyListener(new C00811(lActivity));
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void setupUI() {
        requestWindowFeature(1);
        setContentView(C0085R.layout.main);
        this.appView = (WebView) findViewById(C0085R.id.appView);
        this.appView.getSettings().setGeolocationEnabled(true);
        this.appView.getSettings().setJavaScriptEnabled(true);
        if (VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        this.appView.loadUrl("file:///android_asset/www/index.html");
        this.appView.setVisibility(0);
    }

    private void showAlert(String title, String text, Context ctx) {
        Builder alert = new Builder(ctx);
        alert.setCancelable(false);
        if (!(title == null || BuildConfig.FLAVOR.equals(title))) {
            alert.setTitle(title);
        }
        alert.setMessage(text);
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    private void startMicroscope() {
        Intent intent = new Intent(ACTIVITY_MICROSCOPE);
        try {
            intent.setClass(this, Class.forName("ps.hacking.hackyeaster.android.MicroscopeActivity"));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
        }
    }

    private void startTweakedTweet() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://twitter.com/intent/tweet?text=%23%EF%BC%A8a%EF%BD%83%EF%BD%8By%CE%95%EF%BD%81ste%EF%BD%92%E2%80%A9201%EF%BC%97%E2%80%A9%E2%85%B0%EF%BD%93%E2%80%80a%E2%80%84l%EF%BD%8F%EF%BD%94%E2%80%80%CE%BFf%E2%80%89%EF%BD%86un%EF%BC%81%E2%80%A8%23%D1%81tf%E2%80%88%23%EF%BD%88%EF%BD%81%CF%B2king-lab")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSensors() {
        this.magnetometer = this.sensorManager.getDefaultSensor(2);
        this.sensorManager.registerListener(this, this.magnetometer, 0);
    }

    private void stopSensors() {
        if (this.magnetometer != null) {
            this.sensorManager.unregisterListener(this, this.magnetometer);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 2) {
            float[] mag = event.values;
            double l = Math.sqrt((double) (((mag[0] * mag[0]) + (mag[1] * mag[1])) + (mag[2] * mag[2])));
            String k = BuildConfig.FLAVOR;
            if (l >= 1000.0d) {
                k = sha1("file:///android_asset/www/index.html");
            }
            this.appView.loadUrl("javascript:sensorFeedback('{\"k\": \"" + k + "\", \"l\": \"" + l + "\"}')");
            this.sensorManager.unregisterListener(this, this.magnetometer);
            this.magnetometer = null;
        }
    }

    public void setWebViewClient() {
        this.appView.setWebViewClient(new C00822(this));
    }

    private void shareUrl(Activity activity) {
        try {
            Intent i = new Intent("android.intent.action.SEND");
            i.setType(HTTP.PLAIN_TEXT_TYPE);
            i.putExtra("android.intent.extra.SUBJECT", "Hacky Easter");
            i.putExtra("android.intent.extra.TEXT", SHARE_TEXT);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareStatusInTwitter(String url, Activity activity) {
        try {
            Uri uri = Uri.parse(url);
            String level = uri.getQueryParameter("level");
            String score = uri.getQueryParameter("score");
            String msg = URLEncoder.encode(String.format(SCORES_TEXT, new Object[]{level, score}), Hex.DEFAULT_CHARSET_NAME);
            String scoreUrl = URLEncoder.encode(String.format(SCORES_URL, new Object[]{UserData.getUserName()}), Hex.DEFAULT_CHARSET_NAME);
            String str = "android.intent.action.VIEW";
            startActivity(new Intent(str, Uri.parse(String.format(SHARE_URL, new Object[]{msg, scoreUrl}))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sha1(String s) {
        try {
            MessageDigest crypt = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1);
            crypt.update(s.getBytes(Hex.DEFAULT_CHARSET_NAME));
            return new BigInteger(1, crypt.digest()).toString(16);
        } catch (Exception e) {
            return BuildConfig.FLAVOR;
        }
    }

    private void openTwitter() {
        Intent intent;
        try {
            intent = new Intent("android.intent.action.VIEW", TWITTER_URL_APP);
            intent.setFlags(268435456);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            try {
                intent = new Intent("android.intent.action.VIEW", TWITTER_URL_BROWSER);
                intent.setFlags(268435456);
                startActivity(intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void startQrScanner() {
        try {
            this.scannerRunning = true;
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt(BuildConfig.FLAVOR);
            integrator.setOrientationLocked(true);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        this.scannerRunning = false;
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (resultCode == -1 && scanningResult != null) {
            String contents = scanningResult.getContents();
            if ("QR_CODE".equals(scanningResult.getFormatName()) && eggPattern.matcher(contents).matches()) {
                returnScanLine(0, contents);
                return;
            } else if (!(contents == null || BuildConfig.FLAVOR.equals(contents))) {
                returnScanLine(1, BuildConfig.FLAVOR);
                return;
            }
        }
        returnScanLine(2, BuildConfig.FLAVOR);
    }

    private void handleQrScannerResult(Intent intent) {
        String contents = intent.getStringExtra(Scan.RESULT);
        String format = intent.getStringExtra(Scan.RESULT_FORMAT);
    }

    public void returnScanLine(int returnCode, String scanLine) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"rc\":").append(returnCode);
        sb.append(", \"egg\": \"").append(StringUtil.escapeJsonValue(scanLine, true));
        sb.append("\"}");
        this.appView.loadUrl("javascript:scanResult('" + sb.toString() + "')");
    }
}
