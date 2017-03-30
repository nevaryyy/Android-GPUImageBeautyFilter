package com.nevaryyy.beautyfilterdemo.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.nevaryyy.beautyfilterdemo.R;
import com.nevaryyy.beautyfilterdemo.permission.PermissionCheck;
import com.nevaryyy.beautyfilterdemo.permission.PermissionEnZh;
import com.nevaryyy.beautyfilterdemo.util.DialogUtil;
import com.nevaryyy.beautyfilterdemo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuejinyang
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    public static final int REQUEST_CODE_PERMISSION_READ_CONTACT = 0x80;
    public static final int REQUEST_CODE_PERMISSION_STORAGE = 0x81;
    public static final int REQUEST_CODE_PERMISSION_CALL = 0x82;
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 0x83;
    public static final int REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 0x84;
    public static final int REQUEST_CODE_DETAIL_SETTINGS = 0x85;

    protected PermissionCheck permissionCheck;
    protected boolean permissionChecking;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void openDetailSettings() {
        Uri packageUri = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri);
        startActivityForResult(intent, REQUEST_CODE_DETAIL_SETTINGS);
    }

    public void checkPermission(final PermissionCheck permissionCheck) {
        if (permissionChecking || permissionCheck == null) {
            return;
        }

        this.permissionCheck = permissionCheck;
        permissionChecking = true;

        final List<String> permissionsDenied = new ArrayList<>();
        List<String> permissionsNeededShow = new ArrayList<>();

        for (String permission : permissionCheck.getPermissions()) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsDenied.add(permission);
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    permissionsNeededShow.add(permission);
                }
            }
        }

        if (permissionsDenied.size() > 0) {
            if (permissionsNeededShow.size() > 0) {
                String message = getString(R.string.dialog_need_permission_title);
                for (String permissionNeededShow : permissionsNeededShow) {
                    String permission = PermissionEnZh.getPermissionZh(permissionNeededShow);
                    if (!StringUtil.isEmpty(permission)) {
                        message = message + permission + getString(R.string.comma);
                    }
                }

                message = message.substring(0, message.length() - getString(R.string.comma).length());
                message += getString(R.string.period);

                DialogUtil.showSimpleDialog(this, null, message,
                        getString(R.string.grant), new DialogUtil.DialogCallback() {
                            @Override
                            public void callback() {
                                ActivityCompat.requestPermissions(BaseActivity.this,
                                        permissionsDenied.toArray(new String[permissionsDenied.size()]),
                                        permissionCheck.getRequestCode());
                            }
                        }, getString(R.string.deny), new DialogUtil.DialogCallback() {
                            @Override
                            public void callback() {
                                permissionCheck.deniedCallback();
                                permissionChecking = false;
                            }
                        });
            }
            else {
                ActivityCompat.requestPermissions(BaseActivity.this,
                        permissionsDenied.toArray(new String[permissionsDenied.size()]),
                        permissionCheck.getRequestCode());
            }

            return;
        }

        permissionCheck.grantedCallback();
        permissionChecking = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_STORAGE ||
                requestCode == REQUEST_CODE_PERMISSION_READ_CONTACT ||
                requestCode == REQUEST_CODE_PERMISSION_CALL ||
                requestCode == REQUEST_CODE_PERMISSION_CAMERA ||
                requestCode == REQUEST_CODE_PERMISSION_READ_PHONE_STATE) {

            boolean granted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                permissionCheck.grantedCallback();
            }
            else {
                permissionCheck.deniedCallback();
            }

            permissionChecking = false;
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_DETAIL_SETTINGS) {
            checkPermission(permissionCheck);
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
