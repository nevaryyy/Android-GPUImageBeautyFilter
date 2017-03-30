package com.nevaryyy.beautyfilterdemo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.nevaryyy.beautyfilterdemo.R;
import com.nevaryyy.beautyfilterdemo.base.IBaseView;

/**
 * @author yuejinyang
 */
public class DialogUtil {

    public static void showSimpleDialog(Context context, String title, String message,
                                        String positiveText, final DialogCallback positiveCallback,
                                        String negativeText, final DialogCallback negativeCallback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (positiveCallback != null) {
                            positiveCallback.callback();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (negativeCallback != null) {
                            negativeCallback.callback();
                        }
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public static void showNoPermissionDialog(final IBaseView baseView, String message, DialogCallback cancelCallback) {
        showSimpleDialog(baseView.getContext(),
                baseView.getContext().getString(R.string.dialog_no_permission_title),
                message,
                baseView.getContext().getString(R.string.goto_settings), new DialogCallback() {
                    @Override
                    public void callback() {
                        baseView.openDetailSettings();
                    }
                }, baseView.getContext().getString(R.string.cancel), cancelCallback);
    }

    public interface DialogCallback {
        void callback();
    }
}
