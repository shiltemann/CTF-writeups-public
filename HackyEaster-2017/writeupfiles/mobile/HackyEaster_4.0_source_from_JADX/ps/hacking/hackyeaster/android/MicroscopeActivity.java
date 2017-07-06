package ps.hacking.hackyeaster.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MicroscopeActivity extends Activity {

    /* renamed from: ps.hacking.hackyeaster.android.MicroscopeActivity.1 */
    class C00841 extends WebViewClient {
        C00841() {
        }

        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            handler.proceed("he2017", "egggghunthackinglab");
        }

        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            view.loadDataWithBaseURL(null, "<html>&#128048;</html>", "text/html", "utf-8", null);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        setContentView(C0085R.layout.activity_microscope);
        WebView webview = (WebView) findViewById(C0085R.id.microscopeWebView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new C00841());
        webview.loadUrl("https://hackyeaster.hacking-lab.com/hackyeaster/challenge09_su6z47IoTT7.html".replace('6', '5'));
    }
}
