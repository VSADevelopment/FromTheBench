package com.vsa.fromthebench;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.vsa.fromthebench.utils.Constants;
import com.vsa.fromthebench.utils.DialogManager;
import com.vsa.fromthebench.utils.PreferencesManager;

import java.io.File;


public class Dashboard extends Activity implements View.OnClickListener{

    private Button mButtonAboutMe;
    private Button mButtonProCon;
    private Button mButtonEmptyCache;
    private Button mButtonCloseSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mButtonAboutMe = (Button) findViewById(R.id.about_me_button);
        mButtonProCon = (Button) findViewById(R.id.procon_button);
        mButtonEmptyCache = (Button) findViewById(R.id.empty_cache_button);
        mButtonCloseSession = (Button) findViewById(R.id.close_session_button);

        mButtonAboutMe.setOnClickListener(this);
        mButtonProCon.setOnClickListener(this);
        mButtonEmptyCache.setOnClickListener(this);
        mButtonCloseSession.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        if(view == mButtonAboutMe){

            intent = new Intent(this, AboutMe.class);
            startActivity(intent);

        }

        if(view == mButtonProCon){

            intent = new Intent(this, ProCon.class);
            startActivity(intent);

        }

        if(view == mButtonEmptyCache){

            vaciarCache();
            DialogManager.createDialog(this, getString(R.string.alert_cache_deleted)).show();

        }

        if(view == mButtonCloseSession){

            logout();

        }

    }

    private void vaciarCache(){
        File applicationFolder = new File(Constants.APPLICATION_FOLDER);
        File[] applicationFiles = applicationFolder.listFiles();
        for(File file:applicationFiles)
            file.delete();
    }

    private void logout(){
        PreferencesManager.delete(this, PreferencesManager.Constants.KEY_USER, PreferencesManager.Constants.KEY_USER);
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }


}
