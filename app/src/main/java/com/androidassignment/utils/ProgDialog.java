package com.androidassignment.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.androidassignment.R;


public class ProgDialog {
    public ProgressDialog progressDialog;
    Context context;

    public ProgDialog(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
    }

    public void ProgressDialogStart() {

        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    public void ProgressDialogStop() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public boolean isShowing() {
        return progressDialog.isShowing();
    }
}
