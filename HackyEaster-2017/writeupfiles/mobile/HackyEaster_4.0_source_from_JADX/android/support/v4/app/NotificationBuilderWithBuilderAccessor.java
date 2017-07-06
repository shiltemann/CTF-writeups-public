package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@TargetApi(11)
@RequiresApi(11)
@RestrictTo({Scope.LIBRARY_GROUP})
public interface NotificationBuilderWithBuilderAccessor {
    Notification build();

    Builder getBuilder();
}
