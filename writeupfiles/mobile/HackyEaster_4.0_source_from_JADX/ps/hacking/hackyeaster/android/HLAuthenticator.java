package ps.hacking.hackyeaster.android;

import android.os.AsyncTask;
import android.webkit.WebView;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.SM;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HLAuthenticator extends AsyncTask<Object, Void, String> {
    private static final String HL_PARAMS_LOGIN = "return=%s&eventid=&uk=&userEmail=%s&userPassword=%s&login=Login";
    private static final String HL_URL = "https://www.hacking-lab.com";
    private static final String HL_URL_EVENT_REGISTRATION = "https://www.hacking-lab.com/eventregister/?eventid=1205&uk=";
    private static final String HL_URL_LOGIN = "https://www.hacking-lab.com/user/login/";
    private static final String HL_URL_LOGIN_2 = "https://www.hacking-lab.com/user/login/index.html";
    private static final String PATTERN_NAME = "<i class=\"fa fa-lock\"></i>\\s*([\\w.+\\-]+)\\s*</a>";
    private static final String PATTERN_NATION = "/country_flags/([a-zA-z]{2,3}).png\" alt=";
    protected Activity activity;

    /* renamed from: ps.hacking.hackyeaster.android.HLAuthenticator.1 */
    class C00831 implements Runnable {
        final /* synthetic */ String val$fname;
        final /* synthetic */ String val$fnation;
        final /* synthetic */ int val$frc;
        final /* synthetic */ WebView val$fview;

        C00831(WebView webView, int i, String str, String str2) {
            this.val$fview = webView;
            this.val$frc = i;
            this.val$fname = str;
            this.val$fnation = str2;
        }

        public void run() {
            this.val$fview.loadUrl("javascript:authFeedback('{\"rc\": " + this.val$frc + ", \"name\": \"" + this.val$fname + "\", \"nation\": \"" + this.val$fnation + "\"}');");
        }
    }

    public HLAuthenticator(Activity activity) {
        this.activity = activity;
    }

    public String[] authenticate(String email, String pass) {
        String cookie = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            if (client.execute(new HttpGet(new URI(HL_URL_LOGIN))).getStatusLine().getStatusCode() != 200) {
                return null;
            }
            List<Cookie> cookies = client.getCookieStore().getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("HLSSL".equals(c.getName())) {
                        cookie = c.getValue();
                        break;
                    }
                }
            }
            if (cookie == null || cookie.equals(BuildConfig.FLAVOR)) {
                return null;
            }
            HttpPost post = new HttpPost();
            post.setURI(new URI(HL_URL_LOGIN_2));
            String emailEncoded = URLEncoder.encode(email, Hex.DEFAULT_CHARSET_NAME);
            String passEncoded = URLEncoder.encode(pass, Hex.DEFAULT_CHARSET_NAME);
            post.setEntity(new StringEntity(String.format(HL_PARAMS_LOGIN, new Object[]{"user%2fmyprofile%2feditprofile.html", emailEncoded, passEncoded, Hex.DEFAULT_CHARSET_NAME})));
            post.addHeader(HTTP.CONTENT_TYPE, URLEncodedUtils.CONTENT_TYPE);
            post.addHeader(HTTP.CONN_DIRECTIVE, "keep-alive");
            post.addHeader(SM.COOKIE, "HLSSL=" + cookie);
            HttpResponse resp = client.execute((HttpUriRequest) post);
            if (resp.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            String name = BuildConfig.FLAVOR;
            String nation = BuildConfig.FLAVOR;
            String httpPage = EntityUtils.toString(resp.getEntity());
            Matcher m = Pattern.compile(PATTERN_NAME).matcher(httpPage);
            if (m.find() && m.groupCount() == 1) {
                name = m.group(1);
            }
            m = Pattern.compile(PATTERN_NATION).matcher(httpPage);
            if (m.find() && m.groupCount() == 1) {
                nation = m.group(1);
                if (nation != null) {
                    nation = nation.toLowerCase();
                }
            }
            if (!(BuildConfig.FLAVOR.equals(name) || BuildConfig.FLAVOR.equals(nation))) {
                try {
                    resp = client.execute(new HttpGet(new URI(HL_URL_EVENT_REGISTRATION)));
                } catch (Exception e) {
                }
            }
            return new String[]{name, nation};
        } catch (Exception e2) {
            return null;
        }
    }

    protected String doInBackground(Object... params) {
        String email = params[0];
        String password = params[1];
        WebView view = params[2];
        int rc = 1;
        String name = BuildConfig.FLAVOR;
        String nation = BuildConfig.FLAVOR;
        String[] result = authenticate(email, password);
        if (result != null && result.length == 2) {
            rc = 0;
            name = result[0];
            nation = result[1];
        }
        WebView fview = view;
        this.activity.runOnUiThread(new C00831(fview, rc, name, nation));
        return null;
    }
}
