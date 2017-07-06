package ps.hacking.hackyeaster.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.http.protocol.HTTP;

public class SndActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        setContentView(C0085R.layout.activity_snd);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if ("android.intent.action.SEND".equals(action) && type != null && HTTP.PLAIN_TEXT_TYPE.equals(type)) {
            String text = intent.getStringExtra("android.intent.extra.TEXT");
            if (text != null && "c95259de1fd719814daef8f1dc4bd64f9d885ff0".equals(sha1(text.toLowerCase()))) {
                ((TextView) findViewById(C0085R.id.sndTextView)).setText("Thank you!!");
                ImageView image = (ImageView) findViewById(C0085R.id.sndImageView);
                byte[] decodedString = Base64.decode(new StringBuilder(getString(C0085R.string.f16e) + "ROBVi").reverse().toString(), 0);
                image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
            }
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
}
