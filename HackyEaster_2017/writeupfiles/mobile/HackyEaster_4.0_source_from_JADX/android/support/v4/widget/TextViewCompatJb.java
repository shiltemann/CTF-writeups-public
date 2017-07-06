package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

@TargetApi(16)
@RequiresApi(16)
class TextViewCompatJb {
    TextViewCompatJb() {
    }

    static int getMaxLines(TextView textView) {
        return textView.getMaxLines();
    }

    static int getMinLines(TextView textView) {
        return textView.getMinLines();
    }
}
