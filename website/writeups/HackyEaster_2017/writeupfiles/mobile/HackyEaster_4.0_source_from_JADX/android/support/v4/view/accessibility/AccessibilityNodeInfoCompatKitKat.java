package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(19)
@RequiresApi(19)
class AccessibilityNodeInfoCompatKitKat {
    private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
    private static final String TRAITS_KEY = "android.view.accessibility.AccessibilityNodeInfo.traits";
    private static final long TRAIT_HAS_IMAGE = 1;
    private static final byte TRAIT_UNSET = (byte) -1;

    static class CollectionInfo {
        CollectionInfo() {
        }

        static int getColumnCount(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) info).getColumnCount();
        }

        static int getRowCount(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) info).getRowCount();
        }

        static boolean isHierarchical(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) info).isHierarchical();
        }
    }

    static class CollectionItemInfo {
        CollectionItemInfo() {
        }

        static int getColumnIndex(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).getColumnIndex();
        }

        static int getColumnSpan(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).getColumnSpan();
        }

        static int getRowIndex(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).getRowIndex();
        }

        static int getRowSpan(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).getRowSpan();
        }

        static boolean isHeading(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).isHeading();
        }
    }

    static class RangeInfo {
        RangeInfo() {
        }

        static float getCurrent(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) info).getCurrent();
        }

        static float getMax(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) info).getMax();
        }

        static float getMin(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) info).getMin();
        }

        static int getType(Object info) {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) info).getType();
        }
    }

    AccessibilityNodeInfoCompatKitKat() {
    }

    static int getLiveRegion(Object info) {
        return ((AccessibilityNodeInfo) info).getLiveRegion();
    }

    static void setLiveRegion(Object info, int mode) {
        ((AccessibilityNodeInfo) info).setLiveRegion(mode);
    }

    static Object getCollectionInfo(Object info) {
        return ((AccessibilityNodeInfo) info).getCollectionInfo();
    }

    static Object getCollectionItemInfo(Object info) {
        return ((AccessibilityNodeInfo) info).getCollectionItemInfo();
    }

    public static void setCollectionInfo(Object info, Object collectionInfo) {
        ((AccessibilityNodeInfo) info).setCollectionInfo((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) collectionInfo);
    }

    public static void setCollectionItemInfo(Object info, Object collectionItemInfo) {
        ((AccessibilityNodeInfo) info).setCollectionItemInfo((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) collectionItemInfo);
    }

    static Object getRangeInfo(Object info) {
        return ((AccessibilityNodeInfo) info).getRangeInfo();
    }

    public static void setRangeInfo(Object info, Object rangeInfo) {
        ((AccessibilityNodeInfo) info).setRangeInfo((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) rangeInfo);
    }

    public static Object obtainCollectionInfo(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, hierarchical);
    }

    public static Object obtainCollectionInfo(int rowCount, int columnCount, boolean hierarchical) {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, hierarchical);
    }

    public static Object obtainCollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading);
    }

    public static void setContentInvalid(Object info, boolean contentInvalid) {
        ((AccessibilityNodeInfo) info).setContentInvalid(contentInvalid);
    }

    public static boolean isContentInvalid(Object info) {
        return ((AccessibilityNodeInfo) info).isContentInvalid();
    }

    public static boolean canOpenPopup(Object info) {
        return ((AccessibilityNodeInfo) info).canOpenPopup();
    }

    public static void setCanOpenPopup(Object info, boolean opensPopup) {
        ((AccessibilityNodeInfo) info).setCanOpenPopup(opensPopup);
    }

    public static Bundle getExtras(Object info) {
        return ((AccessibilityNodeInfo) info).getExtras();
    }

    private static long getTraits(Object info) {
        return getExtras(info).getLong(TRAITS_KEY, -1);
    }

    private static void setTrait(Object info, long trait) {
        Bundle extras = getExtras(info);
        extras.putLong(TRAITS_KEY, extras.getLong(TRAITS_KEY, 0) | trait);
    }

    public static int getInputType(Object info) {
        return ((AccessibilityNodeInfo) info).getInputType();
    }

    public static void setInputType(Object info, int inputType) {
        ((AccessibilityNodeInfo) info).setInputType(inputType);
    }

    public static boolean isDismissable(Object info) {
        return ((AccessibilityNodeInfo) info).isDismissable();
    }

    public static void setDismissable(Object info, boolean dismissable) {
        ((AccessibilityNodeInfo) info).setDismissable(dismissable);
    }

    public static boolean isMultiLine(Object info) {
        return ((AccessibilityNodeInfo) info).isMultiLine();
    }

    public static void setMultiLine(Object info, boolean multiLine) {
        ((AccessibilityNodeInfo) info).setMultiLine(multiLine);
    }

    public static CharSequence getRoleDescription(Object info) {
        return getExtras(info).getCharSequence(ROLE_DESCRIPTION_KEY);
    }

    public static void setRoleDescription(Object info, CharSequence roleDescription) {
        getExtras(info).putCharSequence(ROLE_DESCRIPTION_KEY, roleDescription);
    }

    public static Object obtainRangeInfo(int type, float min, float max, float current) {
        return android.view.accessibility.AccessibilityNodeInfo.RangeInfo.obtain(type, min, max, current);
    }
}
