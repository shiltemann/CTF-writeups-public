package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.InboxStyle;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v4.app.NotificationCompatBase.Action.Factory;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(16)
@RequiresApi(16)
class NotificationCompatJellybean {
    static final String EXTRA_ACTION_EXTRAS = "android.support.actionExtras";
    static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
    static final String EXTRA_GROUP_KEY = "android.support.groupKey";
    static final String EXTRA_GROUP_SUMMARY = "android.support.isGroupSummary";
    static final String EXTRA_LOCAL_ONLY = "android.support.localOnly";
    static final String EXTRA_REMOTE_INPUTS = "android.support.remoteInputs";
    static final String EXTRA_SORT_KEY = "android.support.sortKey";
    static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    private static final String KEY_ACTION_INTENT = "actionIntent";
    private static final String KEY_ALLOW_GENERATED_REPLIES = "allowGeneratedReplies";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_ICON = "icon";
    private static final String KEY_REMOTE_INPUTS = "remoteInputs";
    private static final String KEY_TITLE = "title";
    public static final String TAG = "NotificationCompat";
    private static Class<?> sActionClass;
    private static Field sActionIconField;
    private static Field sActionIntentField;
    private static Field sActionTitleField;
    private static boolean sActionsAccessFailed;
    private static Field sActionsField;
    private static final Object sActionsLock;
    private static Field sExtrasField;
    private static boolean sExtrasFieldAccessFailed;
    private static final Object sExtrasLock;

    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {
        private android.app.Notification.Builder f31b;
        private List<Bundle> mActionExtrasList;
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private final Bundle mExtras;

        public Builder(Context context, Notification n, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, RemoteViews tickerView, int number, PendingIntent contentIntent, PendingIntent fullScreenIntent, Bitmap largeIcon, int progressMax, int progress, boolean progressIndeterminate, boolean useChronometer, int priority, CharSequence subText, boolean localOnly, Bundle extras, String groupKey, boolean groupSummary, String sortKey, RemoteViews contentView, RemoteViews bigContentView) {
            this.mActionExtrasList = new ArrayList();
            this.f31b = new android.app.Notification.Builder(context).setWhen(n.when).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(progressMax, progress, progressIndeterminate);
            this.mExtras = new Bundle();
            if (extras != null) {
                this.mExtras.putAll(extras);
            }
            if (localOnly) {
                this.mExtras.putBoolean(NotificationCompatJellybean.EXTRA_LOCAL_ONLY, true);
            }
            if (groupKey != null) {
                this.mExtras.putString(NotificationCompatJellybean.EXTRA_GROUP_KEY, groupKey);
                if (groupSummary) {
                    this.mExtras.putBoolean(NotificationCompatJellybean.EXTRA_GROUP_SUMMARY, true);
                } else {
                    this.mExtras.putBoolean(NotificationCompatJellybean.EXTRA_USE_SIDE_CHANNEL, true);
                }
            }
            if (sortKey != null) {
                this.mExtras.putString(NotificationCompatJellybean.EXTRA_SORT_KEY, sortKey);
            }
            this.mContentView = contentView;
            this.mBigContentView = bigContentView;
        }

        public void addAction(Action action) {
            this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.f31b, action));
        }

        public android.app.Notification.Builder getBuilder() {
            return this.f31b;
        }

        public Notification build() {
            Notification notif = this.f31b.build();
            Bundle extras = NotificationCompatJellybean.getExtras(notif);
            Bundle mergeBundle = new Bundle(this.mExtras);
            for (String key : this.mExtras.keySet()) {
                if (extras.containsKey(key)) {
                    mergeBundle.remove(key);
                }
            }
            extras.putAll(mergeBundle);
            SparseArray<Bundle> actionExtrasMap = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if (actionExtrasMap != null) {
                NotificationCompatJellybean.getExtras(notif).putSparseParcelableArray(NotificationCompatJellybean.EXTRA_ACTION_EXTRAS, actionExtrasMap);
            }
            if (this.mContentView != null) {
                notif.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notif.bigContentView = this.mBigContentView;
            }
            return notif;
        }
    }

    NotificationCompatJellybean() {
    }

    static {
        sExtrasLock = new Object();
        sActionsLock = new Object();
    }

    public static void addBigTextStyle(NotificationBuilderWithBuilderAccessor b, CharSequence bigContentTitle, boolean useSummary, CharSequence summaryText, CharSequence bigText) {
        BigTextStyle style = new BigTextStyle(b.getBuilder()).setBigContentTitle(bigContentTitle).bigText(bigText);
        if (useSummary) {
            style.setSummaryText(summaryText);
        }
    }

    public static void addBigPictureStyle(NotificationBuilderWithBuilderAccessor b, CharSequence bigContentTitle, boolean useSummary, CharSequence summaryText, Bitmap bigPicture, Bitmap bigLargeIcon, boolean bigLargeIconSet) {
        BigPictureStyle style = new BigPictureStyle(b.getBuilder()).setBigContentTitle(bigContentTitle).bigPicture(bigPicture);
        if (bigLargeIconSet) {
            style.bigLargeIcon(bigLargeIcon);
        }
        if (useSummary) {
            style.setSummaryText(summaryText);
        }
    }

    public static void addInboxStyle(NotificationBuilderWithBuilderAccessor b, CharSequence bigContentTitle, boolean useSummary, CharSequence summaryText, ArrayList<CharSequence> texts) {
        InboxStyle style = new InboxStyle(b.getBuilder()).setBigContentTitle(bigContentTitle);
        if (useSummary) {
            style.setSummaryText(summaryText);
        }
        Iterator it = texts.iterator();
        while (it.hasNext()) {
            style.addLine((CharSequence) it.next());
        }
    }

    public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> actionExtrasList) {
        SparseArray<Bundle> actionExtrasMap = null;
        int count = actionExtrasList.size();
        for (int i = 0; i < count; i++) {
            Bundle actionExtras = (Bundle) actionExtrasList.get(i);
            if (actionExtras != null) {
                if (actionExtrasMap == null) {
                    actionExtrasMap = new SparseArray();
                }
                actionExtrasMap.put(i, actionExtras);
            }
        }
        return actionExtrasMap;
    }

    public static Bundle getExtras(Notification notif) {
        synchronized (sExtrasLock) {
            if (sExtrasFieldAccessFailed) {
                return null;
            }
            try {
                if (sExtrasField == null) {
                    Field extrasField = Notification.class.getDeclaredField(KEY_EXTRAS);
                    if (Bundle.class.isAssignableFrom(extrasField.getType())) {
                        extrasField.setAccessible(true);
                        sExtrasField = extrasField;
                    } else {
                        Log.e(TAG, "Notification.extras field is not of type Bundle");
                        sExtrasFieldAccessFailed = true;
                        return null;
                    }
                }
                Bundle bundle = (Bundle) sExtrasField.get(notif);
                if (bundle == null) {
                    bundle = new Bundle();
                    sExtrasField.set(notif, bundle);
                }
                return bundle;
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to access notification extras", e);
                sExtrasFieldAccessFailed = true;
                return null;
            } catch (NoSuchFieldException e2) {
                Log.e(TAG, "Unable to access notification extras", e2);
                sExtrasFieldAccessFailed = true;
                return null;
            }
        }
    }

    public static Action readAction(Factory factory, RemoteInput.Factory remoteInputFactory, int icon, CharSequence title, PendingIntent actionIntent, Bundle extras) {
        RemoteInput[] remoteInputs = null;
        boolean allowGeneratedReplies = false;
        if (extras != null) {
            remoteInputs = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(extras, EXTRA_REMOTE_INPUTS), remoteInputFactory);
            allowGeneratedReplies = extras.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES);
        }
        return factory.build(icon, title, actionIntent, extras, remoteInputs, allowGeneratedReplies);
    }

    public static Bundle writeActionAndGetExtras(android.app.Notification.Builder builder, Action action) {
        builder.addAction(action.getIcon(), action.getTitle(), action.getActionIntent());
        Bundle actionExtras = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            actionExtras.putParcelableArray(EXTRA_REMOTE_INPUTS, RemoteInputCompatJellybean.toBundleArray(action.getRemoteInputs()));
        }
        actionExtras.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies());
        return actionExtras;
    }

    public static int getActionCount(Notification notif) {
        int length;
        synchronized (sActionsLock) {
            Object[] actionObjects = getActionObjectsLocked(notif);
            length = actionObjects != null ? actionObjects.length : 0;
        }
        return length;
    }

    public static Action getAction(Notification notif, int actionIndex, Factory factory, RemoteInput.Factory remoteInputFactory) {
        Action readAction;
        synchronized (sActionsLock) {
            try {
                Object actionObject = getActionObjectsLocked(notif)[actionIndex];
                Bundle actionExtras = null;
                Bundle extras = getExtras(notif);
                if (extras != null) {
                    SparseArray<Bundle> actionExtrasMap = extras.getSparseParcelableArray(EXTRA_ACTION_EXTRAS);
                    if (actionExtrasMap != null) {
                        actionExtras = (Bundle) actionExtrasMap.get(actionIndex);
                    }
                }
                readAction = readAction(factory, remoteInputFactory, sActionIconField.getInt(actionObject), (CharSequence) sActionTitleField.get(actionObject), (PendingIntent) sActionIntentField.get(actionObject), actionExtras);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to access notification actions", e);
                sActionsAccessFailed = true;
                return null;
            }
        }
        return readAction;
    }

    private static Object[] getActionObjectsLocked(Notification notif) {
        synchronized (sActionsLock) {
            if (ensureActionReflectionReadyLocked()) {
                try {
                    Object[] objArr = (Object[]) sActionsField.get(notif);
                    return objArr;
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "Unable to access notification actions", e);
                    sActionsAccessFailed = true;
                    return null;
                }
            }
            return null;
        }
    }

    private static boolean ensureActionReflectionReadyLocked() {
        boolean z = true;
        if (sActionsAccessFailed) {
            return false;
        }
        try {
            if (sActionsField == null) {
                sActionClass = Class.forName("android.app.Notification$Action");
                sActionIconField = sActionClass.getDeclaredField(KEY_ICON);
                sActionTitleField = sActionClass.getDeclaredField(KEY_TITLE);
                sActionIntentField = sActionClass.getDeclaredField(KEY_ACTION_INTENT);
                sActionsField = Notification.class.getDeclaredField("actions");
                sActionsField.setAccessible(true);
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Unable to access notification actions", e);
            sActionsAccessFailed = true;
        } catch (NoSuchFieldException e2) {
            Log.e(TAG, "Unable to access notification actions", e2);
            sActionsAccessFailed = true;
        }
        if (sActionsAccessFailed) {
            z = false;
        }
        return z;
    }

    public static Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> parcelables, Factory actionFactory, RemoteInput.Factory remoteInputFactory) {
        if (parcelables == null) {
            return null;
        }
        Action[] actions = actionFactory.newArray(parcelables.size());
        for (int i = 0; i < actions.length; i++) {
            actions[i] = getActionFromBundle((Bundle) parcelables.get(i), actionFactory, remoteInputFactory);
        }
        return actions;
    }

    private static Action getActionFromBundle(Bundle bundle, Factory actionFactory, RemoteInput.Factory remoteInputFactory) {
        return actionFactory.build(bundle.getInt(KEY_ICON), bundle.getCharSequence(KEY_TITLE), (PendingIntent) bundle.getParcelable(KEY_ACTION_INTENT), bundle.getBundle(KEY_EXTRAS), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(bundle, KEY_REMOTE_INPUTS), remoteInputFactory), bundle.getBoolean(KEY_ALLOW_GENERATED_REPLIES));
    }

    public static ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actions) {
        if (actions == null) {
            return null;
        }
        ArrayList<Parcelable> parcelables = new ArrayList(actions.length);
        for (Action action : actions) {
            parcelables.add(getBundleForAction(action));
        }
        return parcelables;
    }

    private static Bundle getBundleForAction(Action action) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ICON, action.getIcon());
        bundle.putCharSequence(KEY_TITLE, action.getTitle());
        bundle.putParcelable(KEY_ACTION_INTENT, action.getActionIntent());
        bundle.putBundle(KEY_EXTRAS, action.getExtras());
        bundle.putParcelableArray(KEY_REMOTE_INPUTS, RemoteInputCompatJellybean.toBundleArray(action.getRemoteInputs()));
        return bundle;
    }

    public static boolean getLocalOnly(Notification notif) {
        return getExtras(notif).getBoolean(EXTRA_LOCAL_ONLY);
    }

    public static String getGroup(Notification n) {
        return getExtras(n).getString(EXTRA_GROUP_KEY);
    }

    public static boolean isGroupSummary(Notification n) {
        return getExtras(n).getBoolean(EXTRA_GROUP_SUMMARY);
    }

    public static String getSortKey(Notification n) {
        return getExtras(n).getString(EXTRA_SORT_KEY);
    }
}
