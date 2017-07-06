package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityNodeProviderCompat {
    public static final int HOST_VIEW_ID = -1;
    private static final AccessibilityNodeProviderImpl IMPL;
    private final Object mProvider;

    interface AccessibilityNodeProviderImpl {
        Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat);
    }

    static class AccessibilityNodeProviderStubImpl implements AccessibilityNodeProviderImpl {
        AccessibilityNodeProviderStubImpl() {
        }

        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
            return null;
        }
    }

    private static class AccessibilityNodeProviderJellyBeanImpl extends AccessibilityNodeProviderStubImpl {

        /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat.AccessibilityNodeProviderJellyBeanImpl.1 */
        class C01151 implements AccessibilityNodeInfoBridge {
            final /* synthetic */ AccessibilityNodeProviderCompat val$compat;

            C01151(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
                this.val$compat = accessibilityNodeProviderCompat;
            }

            public boolean performAction(int virtualViewId, int action, Bundle arguments) {
                return this.val$compat.performAction(virtualViewId, action, arguments);
            }

            public List<Object> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
                List<AccessibilityNodeInfoCompat> compatInfos = this.val$compat.findAccessibilityNodeInfosByText(text, virtualViewId);
                if (compatInfos == null) {
                    return null;
                }
                List<Object> infos = new ArrayList();
                int infoCount = compatInfos.size();
                for (int i = 0; i < infoCount; i++) {
                    infos.add(((AccessibilityNodeInfoCompat) compatInfos.get(i)).getInfo());
                }
                return infos;
            }

            public Object createAccessibilityNodeInfo(int virtualViewId) {
                AccessibilityNodeInfoCompat compatInfo = this.val$compat.createAccessibilityNodeInfo(virtualViewId);
                if (compatInfo == null) {
                    return null;
                }
                return compatInfo.getInfo();
            }
        }

        AccessibilityNodeProviderJellyBeanImpl() {
        }

        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
            return AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new C01151(compat));
        }
    }

    private static class AccessibilityNodeProviderKitKatImpl extends AccessibilityNodeProviderStubImpl {

        /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat.AccessibilityNodeProviderKitKatImpl.1 */
        class C01161 implements AccessibilityNodeInfoBridge {
            final /* synthetic */ AccessibilityNodeProviderCompat val$compat;

            C01161(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
                this.val$compat = accessibilityNodeProviderCompat;
            }

            public boolean performAction(int virtualViewId, int action, Bundle arguments) {
                return this.val$compat.performAction(virtualViewId, action, arguments);
            }

            public List<Object> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
                List<AccessibilityNodeInfoCompat> compatInfos = this.val$compat.findAccessibilityNodeInfosByText(text, virtualViewId);
                if (compatInfos == null) {
                    return null;
                }
                List<Object> infos = new ArrayList();
                int infoCount = compatInfos.size();
                for (int i = 0; i < infoCount; i++) {
                    infos.add(((AccessibilityNodeInfoCompat) compatInfos.get(i)).getInfo());
                }
                return infos;
            }

            public Object createAccessibilityNodeInfo(int virtualViewId) {
                AccessibilityNodeInfoCompat compatInfo = this.val$compat.createAccessibilityNodeInfo(virtualViewId);
                if (compatInfo == null) {
                    return null;
                }
                return compatInfo.getInfo();
            }

            public Object findFocus(int focus) {
                AccessibilityNodeInfoCompat compatInfo = this.val$compat.findFocus(focus);
                if (compatInfo == null) {
                    return null;
                }
                return compatInfo.getInfo();
            }
        }

        AccessibilityNodeProviderKitKatImpl() {
        }

        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
            return AccessibilityNodeProviderCompatKitKat.newAccessibilityNodeProviderBridge(new C01161(compat));
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeProviderKitKatImpl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityNodeProviderJellyBeanImpl();
        } else {
            IMPL = new AccessibilityNodeProviderStubImpl();
        }
    }

    public AccessibilityNodeProviderCompat() {
        this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
    }

    public AccessibilityNodeProviderCompat(Object provider) {
        this.mProvider = provider;
    }

    public Object getProvider() {
        return this.mProvider;
    }

    @Nullable
    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
        return null;
    }

    public boolean performAction(int virtualViewId, int action, Bundle arguments) {
        return false;
    }

    @Nullable
    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
        return null;
    }

    @Nullable
    public AccessibilityNodeInfoCompat findFocus(int focus) {
        return null;
    }
}
