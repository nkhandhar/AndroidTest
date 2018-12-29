package com.androidassignment.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.androidassignment.R;


public class ConnectionDetector {

    private Context _context;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    public ConnectionDetector(Context context){
        this._context=context;
        builder = new AlertDialog.Builder(context,R.style.AppCompatAlertDialogStyle);
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

    public void error_Dialog()
    {
        builder.setTitle("Connection Error !");
        builder.setMessage(_context.getResources().getString(R.string.Connection_Error));
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

}
