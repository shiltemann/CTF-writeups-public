package org.apache.http.cookie;

import org.apache.http.annotation.Obsolete;

public interface SetCookie2 extends SetCookie {
    @Obsolete
    void setCommentURL(String str);

    @Obsolete
    void setDiscard(boolean z);

    @Obsolete
    void setPorts(int[] iArr);
}
