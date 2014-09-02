package com.vsa.fromthebench;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


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

        }

        if(view == mButtonProCon){

            intent = new Intent(this, ProCon.class);
            startActivity(intent);

        }

        if(view == mButtonEmptyCache){

        }

        if(view == mButtonCloseSession){

        }

    }
}
