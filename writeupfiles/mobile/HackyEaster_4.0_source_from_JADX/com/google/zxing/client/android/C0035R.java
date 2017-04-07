package com.google.zxing.client.android;

import ps.hacking.hackyeaster.android.C0085R;

/* renamed from: com.google.zxing.client.android.R */
public final class C0035R {

    /* renamed from: com.google.zxing.client.android.R.attr */
    public static final class attr {
        public static final int zxing_framing_rect_height = 2130771969;
        public static final int zxing_framing_rect_width = 2130771968;
        public static final int zxing_possible_result_points = 2130771972;
        public static final int zxing_preview_scaling_strategy = 2130771971;
        public static final int zxing_result_view = 2130771973;
        public static final int zxing_scanner_layout = 2130771976;
        public static final int zxing_use_texture_view = 2130771970;
        public static final int zxing_viewfinder_laser = 2130771974;
        public static final int zxing_viewfinder_mask = 2130771975;
    }

    /* renamed from: com.google.zxing.client.android.R.color */
    public static final class color {
        public static final int zxing_custom_possible_result_points = 2131230731;
        public static final int zxing_custom_result_view = 2131230732;
        public static final int zxing_custom_viewfinder_laser = 2131230733;
        public static final int zxing_custom_viewfinder_mask = 2131230734;
        public static final int zxing_possible_result_points = 2131230735;
        public static final int zxing_result_view = 2131230736;
        public static final int zxing_status_text = 2131230737;
        public static final int zxing_transparent = 2131230738;
        public static final int zxing_viewfinder_laser = 2131230739;
        public static final int zxing_viewfinder_mask = 2131230740;
    }

    /* renamed from: com.google.zxing.client.android.R.id */
    public static final class id {
        public static final int centerCrop = 2131296270;
        public static final int fitCenter = 2131296271;
        public static final int fitXY = 2131296272;
        public static final int zxing_back_button = 2131296263;
        public static final int zxing_barcode_scanner = 2131296288;
        public static final int zxing_barcode_surface = 2131296285;
        public static final int zxing_camera_error = 2131296264;
        public static final int zxing_decode = 2131296265;
        public static final int zxing_decode_failed = 2131296266;
        public static final int zxing_decode_succeeded = 2131296267;
        public static final int zxing_possible_result_points = 2131296268;
        public static final int zxing_prewiew_size_ready = 2131296269;
        public static final int zxing_status_view = 2131296287;
        public static final int zxing_viewfinder_view = 2131296286;
    }

    /* renamed from: com.google.zxing.client.android.R.layout */
    public static final class layout {
        public static final int zxing_barcode_scanner = 2130903045;
        public static final int zxing_capture = 2130903046;
    }

    /* renamed from: com.google.zxing.client.android.R.raw */
    public static final class raw {
        public static final int zxing_beep = 2130968576;
    }

    /* renamed from: com.google.zxing.client.android.R.string */
    public static final class string {
        public static final int zxing_app_name = 2131034112;
        public static final int zxing_button_ok = 2131034113;
        public static final int zxing_msg_camera_framework_bug = 2131034114;
        public static final int zxing_msg_default_status = 2131034115;
    }

    /* renamed from: com.google.zxing.client.android.R.style */
    public static final class style {
        public static final int zxing_CaptureTheme = 2131099648;
    }

    /* renamed from: com.google.zxing.client.android.R.styleable */
    public static final class styleable {
        public static final int[] zxing_camera_preview;
        public static final int zxing_camera_preview_zxing_framing_rect_height = 1;
        public static final int zxing_camera_preview_zxing_framing_rect_width = 0;
        public static final int zxing_camera_preview_zxing_preview_scaling_strategy = 3;
        public static final int zxing_camera_preview_zxing_use_texture_view = 2;
        public static final int[] zxing_finder;
        public static final int zxing_finder_zxing_possible_result_points = 0;
        public static final int zxing_finder_zxing_result_view = 1;
        public static final int zxing_finder_zxing_viewfinder_laser = 2;
        public static final int zxing_finder_zxing_viewfinder_mask = 3;
        public static final int[] zxing_view;
        public static final int zxing_view_zxing_scanner_layout = 0;

        static {
            zxing_camera_preview = new int[]{C0085R.attr.zxing_framing_rect_width, C0085R.attr.zxing_framing_rect_height, C0085R.attr.zxing_use_texture_view, C0085R.attr.zxing_preview_scaling_strategy};
            zxing_finder = new int[]{C0085R.attr.zxing_possible_result_points, C0085R.attr.zxing_result_view, C0085R.attr.zxing_viewfinder_laser, C0085R.attr.zxing_viewfinder_mask};
            int[] iArr = new int[zxing_finder_zxing_result_view];
            iArr[zxing_finder_zxing_possible_result_points] = C0085R.attr.zxing_scanner_layout;
            zxing_view = iArr;
        }
    }
}
