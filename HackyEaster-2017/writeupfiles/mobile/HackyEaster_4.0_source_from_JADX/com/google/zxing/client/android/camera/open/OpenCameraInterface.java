package com.google.zxing.client.android.camera.open;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;

public final class OpenCameraInterface {
    public static final int NO_REQUESTED_CAMERA = -1;
    private static final String TAG;

    static {
        TAG = OpenCameraInterface.class.getName();
    }

    private OpenCameraInterface() {
    }

    public static int getCameraId(int requestedId) {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            Log.w(TAG, "No cameras!");
            return NO_REQUESTED_CAMERA;
        }
        boolean explicitRequest;
        int cameraId = requestedId;
        if (cameraId >= 0) {
            explicitRequest = true;
        } else {
            explicitRequest = false;
        }
        if (!explicitRequest) {
            int index = 0;
            while (index < numCameras) {
                CameraInfo cameraInfo = new CameraInfo();
                Camera.getCameraInfo(index, cameraInfo);
                if (cameraInfo.facing == 0) {
                    break;
                }
                index++;
            }
            cameraId = index;
        }
        if (cameraId < numCameras) {
            return cameraId;
        }
        if (explicitRequest) {
            return NO_REQUESTED_CAMERA;
        }
        return 0;
    }

    public static Camera open(int requestedId) {
        int cameraId = getCameraId(requestedId);
        if (cameraId == NO_REQUESTED_CAMERA) {
            return null;
        }
        return Camera.open(cameraId);
    }
}
