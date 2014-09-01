package com.vsa.fromthebench.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vsa.fromthebench.R;

/**
 * Created by Alberto on 01/09/2014.
 */
public class DialogManager {

    public static final Dialog createDialog (Context context, String text){

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_ftb, null);
        Button buttonProceed = (Button) dialogView.findViewById(R.id.dialog_button);
        TextView messageTextView = (TextView) dialogView.findViewById(R.id.dialog_message_textview);


        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(dialogView);

        messageTextView.setText(text);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

}
