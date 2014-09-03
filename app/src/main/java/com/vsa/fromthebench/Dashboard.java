package com.vsa.fromthebench;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.vsa.fromthebench.utils.Constants;
import com.vsa.fromthebench.utils.DialogManager;
import com.vsa.fromthebench.utils.DisplayInfo;
import com.vsa.fromthebench.utils.PreferencesManager;

import java.io.File;


public class Dashboard extends Activity implements View.OnClickListener{

    private static final String TAG = "Dashboard";

    private Button mButtonAboutMe;
    private Button mButtonProCon;
    private Button mButtonEmptyCache;
    private Button mButtonCloseSession;

    private ImageView mImageViewFTBLogo;
    private ImageView mImageViewLeft;
    private ImageView mImageViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mImageViewFTBLogo = (ImageView) findViewById(R.id.logo_ftb_imageview);
        mImageViewLeft = (ImageView) findViewById(R.id.dashboard_left_imageview);
        mImageViewRight = (ImageView) findViewById(R.id.dashboard_right_imageview);

        mButtonAboutMe = (Button) findViewById(R.id.about_me_button);
        mButtonProCon = (Button) findViewById(R.id.procon_button);
        mButtonEmptyCache = (Button) findViewById(R.id.empty_cache_button);
        mButtonCloseSession = (Button) findViewById(R.id.close_session_button);

        mButtonAboutMe.setOnClickListener(this);
        mButtonProCon.setOnClickListener(this);
        mButtonEmptyCache.setOnClickListener(this);
        mButtonCloseSession.setOnClickListener(this);

        configureAnimations();

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

    private void configureAnimations(){

        mImageViewFTBLogo.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ObjectAnimator animationLogo = ObjectAnimator.ofFloat(mImageViewFTBLogo, "translationY", -mImageViewFTBLogo.getHeight(), 0);
                animationLogo.setDuration(600);
                animationLogo.setInterpolator(new LinearInterpolator());
                animationLogo.start();
                mImageViewFTBLogo.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });

        mImageViewLeft.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mImageViewLeft.getViewTreeObserver().removeOnPreDrawListener(this);
                ObjectAnimator animationLeft = ObjectAnimator.ofFloat(mImageViewLeft, "translationX", -mImageViewLeft.getWidth(), 0);
                animationLeft.setStartDelay(500);
                animationLeft.setDuration(1000);
                animationLeft.setInterpolator(new LinearInterpolator());
                animationLeft.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        mImageViewLeft.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {}

                    @Override
                    public void onAnimationCancel(Animator animator) {}

                    @Override
                    public void onAnimationRepeat(Animator animator) {}

                });
                animationLeft.start();
                return true;
            }
        });

        mImageViewRight.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                Point screenDimensions = DisplayInfo.getDimensions(Dashboard.this);
                Log.d(TAG, "SCREEN SIZE: " + screenDimensions.x + " IMAGE WIDTH: " + mImageViewRight.getWidth());
                mImageViewRight.getViewTreeObserver().removeOnPreDrawListener(this);
                ObjectAnimator animationRight = ObjectAnimator.ofFloat(mImageViewRight, "translationX", mImageViewRight.getWidth(), 0);
                animationRight.setStartDelay(500);
                animationRight.setDuration(1000);
                animationRight.setInterpolator(new LinearInterpolator());
                animationRight.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        mImageViewRight.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {}

                    @Override
                    public void onAnimationCancel(Animator animator) {}

                    @Override
                    public void onAnimationRepeat(Animator animator) {}

                });
                animationRight.start();
                return true;
            }
        });

    }


}
