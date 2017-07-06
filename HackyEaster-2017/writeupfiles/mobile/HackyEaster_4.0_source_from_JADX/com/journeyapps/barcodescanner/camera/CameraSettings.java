package com.journeyapps.barcodescanner.camera;

public class CameraSettings {
    private boolean autoFocusEnabled;
    private boolean autoTorchEnabled;
    private boolean barcodeSceneModeEnabled;
    private boolean continuousFocusEnabled;
    private boolean exposureEnabled;
    private FocusMode focusMode;
    private boolean meteringEnabled;
    private int requestedCameraId;
    private boolean scanInverted;

    public enum FocusMode {
        AUTO,
        CONTINUOUS,
        INFINITY,
        MACRO
    }

    public CameraSettings() {
        this.requestedCameraId = -1;
        this.scanInverted = false;
        this.barcodeSceneModeEnabled = false;
        this.meteringEnabled = false;
        this.autoFocusEnabled = true;
        this.continuousFocusEnabled = false;
        this.exposureEnabled = false;
        this.autoTorchEnabled = false;
        this.focusMode = FocusMode.AUTO;
    }

    public int getRequestedCameraId() {
        return this.requestedCameraId;
    }

    public void setRequestedCameraId(int requestedCameraId) {
        this.requestedCameraId = requestedCameraId;
    }

    public boolean isScanInverted() {
        return this.scanInverted;
    }

    public void setScanInverted(boolean scanInverted) {
        this.scanInverted = scanInverted;
    }

    public boolean isBarcodeSceneModeEnabled() {
        return this.barcodeSceneModeEnabled;
    }

    public void setBarcodeSceneModeEnabled(boolean barcodeSceneModeEnabled) {
        this.barcodeSceneModeEnabled = barcodeSceneModeEnabled;
    }

    public boolean isExposureEnabled() {
        return this.exposureEnabled;
    }

    public void setExposureEnabled(boolean exposureEnabled) {
        this.exposureEnabled = exposureEnabled;
    }

    public boolean isMeteringEnabled() {
        return this.meteringEnabled;
    }

    public void setMeteringEnabled(boolean meteringEnabled) {
        this.meteringEnabled = meteringEnabled;
    }

    public boolean isAutoFocusEnabled() {
        return this.autoFocusEnabled;
    }

    public void setAutoFocusEnabled(boolean autoFocusEnabled) {
        this.autoFocusEnabled = autoFocusEnabled;
        if (autoFocusEnabled && this.continuousFocusEnabled) {
            this.focusMode = FocusMode.CONTINUOUS;
        } else if (autoFocusEnabled) {
            this.focusMode = FocusMode.AUTO;
        } else {
            this.focusMode = null;
        }
    }

    public boolean isContinuousFocusEnabled() {
        return this.continuousFocusEnabled;
    }

    public void setContinuousFocusEnabled(boolean continuousFocusEnabled) {
        this.continuousFocusEnabled = continuousFocusEnabled;
        if (continuousFocusEnabled) {
            this.focusMode = FocusMode.CONTINUOUS;
        } else if (this.autoFocusEnabled) {
            this.focusMode = FocusMode.AUTO;
        } else {
            this.focusMode = null;
        }
    }

    public FocusMode getFocusMode() {
        return this.focusMode;
    }

    public void setFocusMode(FocusMode focusMode) {
        this.focusMode = focusMode;
    }

    public boolean isAutoTorchEnabled() {
        return this.autoTorchEnabled;
    }

    public void setAutoTorchEnabled(boolean autoTorchEnabled) {
        this.autoTorchEnabled = autoTorchEnabled;
    }
}
