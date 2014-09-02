package com.vsa.fromthebench.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.vsa.fromthebench.R;

/**
 * Created by Alberto on 02/09/2014.
 */
public class CustomProgressDialog extends ProgressDialog {

    private AnimationDrawable mAnimation;

    private ImageView mImageViewAnimation;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        setCancelable(false);

        mImageViewAnimation = (ImageView) findViewById(R.id.animation_imageview);
    }


    @Override
    public void show() {
        super.show();
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new LinearInterpolator());

        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        animationSet.addAnimation(rotateAnimation);
        mImageViewAnimation.startAnimation(animationSet);
    }


}
