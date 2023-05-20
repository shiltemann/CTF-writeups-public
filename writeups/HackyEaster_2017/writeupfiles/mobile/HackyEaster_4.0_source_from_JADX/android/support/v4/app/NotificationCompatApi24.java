package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.MessagingStyle;
import android.app.Notification.MessagingStyle.Message;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(24)
@RequiresApi(24)
class NotificationCompatApi24 {
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";

    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {
        private android.app.Notification.Builder f29b;

        public Builder(Context context, Notification n, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, RemoteViews tickerView, int number, PendingIntent contentIntent, PendingIntent fullScreenIntent, Bitmap largeIcon, int progressMax, int progress, boolean progressIndeterminate, boolean showWhen, boolean useChronometer, int priority, CharSequence subText, boolean localOnly, String category, ArrayList<String> people, Bundle extras, int color, int visibility, Notification publicVersion, String groupKey, boolean groupSummary, String sortKey, CharSequence[] remoteInputHistory, RemoteViews contentView, RemoteViews bigContentView, RemoteViews headsUpContentView) {
            this.f29b = new android.app.Notification.Builder(context).setWhen(n.when).setShowWhen(showWhen).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(progressMax, progress, progressIndeterminate).setLocalOnly(localOnly).setExtras(extras).setGroup(groupKey).setGroupSummary(groupSummary).setSortKey(sortKey).setCategory(category).setColor(color).setVisibility(visibility).setPublicVersion(publicVersion).setRemoteInputHistory(remoteInputHistory);
            if (contentView != null) {
                this.f29b.setCustomContentView(contentView);
            }
            if (bigContentView != null) {
                this.f29b.setCustomBigContentView(bigContentView);
            }
            if (headsUpContentView != null) {
                this.f29b.setCustomHeadsUpContentView(headsUpContentView);
            }
            Iterator it = people.iterator();
            while (it.hasNext()) {
                this.f29b.addPerson((String) it.next());
            }
        }

        public void addAction(Action action) {
            Bundle actionExtras;
            android.app.Notification.Action.Builder actionBuilder = new android.app.Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
            if (action.getRemoteInputs() != null) {
                for (RemoteInput remoteInput : RemoteInputCompatApi20.fromCompat(action.getRemoteInputs())) {
                    actionBuilder.addRemoteInput(remoteInput);
                }
            }
            if (action.getExtras() != null) {
                actionExtras = new Bundle(action.getExtras());
            } else {
                actionExtras = new Bundle();
            }
            actionExtras.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
            actionBuilder.addExtras(actionExtras);
            actionBuilder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
            this.f29b.addAction(actionBuilder.build());
        }

        public android.app.Notification.Builder getBuilder() {
            return this.f29b;
        }

        public Notification build() {
            return this.f29b.build();
        }
    }

    NotificationCompatApi24() {
    }

    public static void addMessagingStyle(NotificationBuilderWithBuilderAccessor b, CharSequence userDisplayName, CharSequence conversationTitle, List<CharSequence> texts, List<Long> timestamps, List<CharSequence> senders, List<String> dataMimeTypes, List<Uri> dataUris) {
        MessagingStyle style = new MessagingStyle(userDisplayName).setConversationTitle(conversationTitle);
        for (int i = 0; i < texts.size(); i++) {
            Message message = new Message((CharSequence) texts.get(i), ((Long) timestamps.get(i)).longValue(), (CharSequence) senders.get(i));
            if (dataMimeTypes.get(i) != null) {
                message.setData((String) dataMimeTypes.get(i), (Uri) dataUris.get(i));
            }
            style.addMessage(message);
        }
        style.setBuilder(b.getBuilder());
    }
}
