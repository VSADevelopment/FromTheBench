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
import com.vsa.fromthebench.utils.DialogManager;
import com.vsa.fromthebench.utils.PreferencesManager;

import java.util.concurrent.ExecutionException;


public class Login extends Activity implements View.OnClickListener{

    private static final String TAG = "Login";

    private EditText mEditTextUser;
    private EditText mEditTextPassword;
    private Button mButtonProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(!PreferencesManager.getUser(this).isEmpty())
            Toast.makeText(this, "USUARIO LOGEADO: " + PreferencesManager.getUser(this), Toast.LENGTH_LONG);

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
            LoginAsyncTask mLoginAsyncTask = new LoginAsyncTask(this, mEditTextUser.getText().toString(), mEditTextPassword.getText().toString());
            mLoginAsyncTask.execute();
            try {
                final LoginResponseBean response = new LoginResponseBean(mLoginAsyncTask.get());
                Log.d(TAG, "RESPONSE: " + response);

                String dialogMessage = "";
                switch (response.getStatus()){
                    case 0:
                        dialogMessage = getString(R.string.login_success_message);
                        PreferencesManager.storeCredentials(this, mEditTextUser.getText().toString(), mEditTextPassword.getText().toString());
                        break;
                    case -2:
                        dialogMessage = getString(R.string.login_unknown_error);
                        break;
                    default:
                        dialogMessage = response.getMessage();
                }

                Dialog dialog = DialogManager.createDialog(this, dialogMessage);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(response.getStatus() == 0){
                            Intent intent = new Intent(Login.this, DownloadService.class);
                            startService(intent);
                        }
                    }
                });
                dialog.show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
