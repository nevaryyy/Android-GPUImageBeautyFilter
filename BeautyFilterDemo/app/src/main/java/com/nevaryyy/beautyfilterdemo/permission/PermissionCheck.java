package com.nevaryyy.beautyfilterdemo.permission;

import com.nevaryyy.beautyfilterdemo.base.BaseActivity;

/**
 * @author yuejinyang
 */
public class PermissionCheck {

    private String[] permissions;
    private int requestCode;
    private OnPermissionChecked permissionGranted;
    private OnPermissionChecked permissionDenied;

    public PermissionCheck(String[] permissions,
                           int requestCode,
                           OnPermissionChecked permissionGranted,
                           OnPermissionChecked permissionDenied) {
        this.permissions = permissions;
        this.requestCode = requestCode;
        this.permissionGranted = permissionGranted;
        this.permissionDenied = permissionDenied;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void grantedCallback() {
        if (permissionGranted != null) {
            permissionGranted.callback();
        }
    }

    public void deniedCallback() {
        if (permissionDenied != null) {
            permissionDenied.callback();
        }
    }

    public interface OnPermissionChecked {
        void callback();
    }

    public static PermissionCheck getCameraPermissionCheck
            (OnPermissionChecked permissionGranted, OnPermissionChecked permissionDenied) {
        return new PermissionCheck(
                new String[]{"android.permission.CAMERA"},
                BaseActivity.REQUEST_CODE_PERMISSION_CAMERA,
                permissionGranted, permissionDenied);
    }
}
