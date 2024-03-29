package com.vsa.fromthebench;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vsa.fromthebench.beans.LoginResponseBean;
import com.vsa.fromthebench.services.DownloadService;
import com.vsa.fromthebench.services.LoginAsyncTask;
import com.vsa.fromthebench.utils.DeviceInfo;
import com.vsa.fromthebench.utils.DialogManager;
import com.vsa.fromthebench.utils.PreferencesManager;
import com.vsa.fromthebench.view.CustomProgressDialog;

import java.util.concurrent.ExecutionException;


public class Login extends Activity implements View.OnClickListener{

    private static final String TAG = "Login";

    private EditText mEditTextUser;
    private EditText mEditTextPassword;
    private Button mButtonProceed;

    private LoginAsyncTask.OnLoginCompletedListener mOnLoginCompletedListener = new LoginAsyncTask.OnLoginCompletedListener() {
        @Override
        public void onLoginCompleted(String response) {

            final LoginResponseBean loginResponseBean = new LoginResponseBean(response);
            Log.d(TAG, "RESPONSE: " + response);

            String dialogMessage = "";
            switch (loginResponseBean.getStatus()){
                case 0:
                    dialogMessage = getString(R.string.login_success_message);
                    PreferencesManager.storeCredentials(Login.this, mEditTextUser.getText().toString(), mEditTextPassword.getText().toString());
                    break;
                case -2:
                    dialogMessage = getString(R.string.login_unknown_error);
                    break;
                default:
                    dialogMessage = loginResponseBean.getMessage();
            }

            Dialog dialog = DialogManager.createDialog(Login.this, dialogMessage);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(loginResponseBean.getStatus() == 0){
                        Intent intent = new Intent(Login.this, DownloadService.class);
                        startService(intent);
                        intent = new Intent(Login.this, Dashboard.class);
                        startActivity(intent);
                    }
                }
            });
            dialog.show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(!PreferencesManager.getUser(this).isEmpty()){
            Intent intent = new Intent(this, DownloadService.class);
            startService(intent);
            intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
        }


        mEditTextUser = (EditText) findViewById(R.id.user_edittext);
        mEditTextPassword = (EditText) findViewById(R.id.password_edittext);
        mButtonProceed = (Button) findViewById(R.id.proceed_button);

        mButtonProceed.setOnClickListener(this);

        mEditTextUser.setText("albertovecina@gmail.com");
        mEditTextPassword.setText("Sm1a1R");

    }

    @Override
    public void onClick(View view) {
        if(view == mButtonProceed){
            if(DeviceInfo.isConnectionAvailable(this)) {
                LoginAsyncTask mLoginAsyncTask = new LoginAsyncTask(this, mEditTextUser.getText().toString(), mEditTextPassword.getText().toString());
                mLoginAsyncTask.setOnLoginCompletedListener(mOnLoginCompletedListener);
                mLoginAsyncTask.execute();
            }else{
                DialogManager.createDialog(this, getString(R.string.connection_warning)).show();
            }
        }
    }
}
