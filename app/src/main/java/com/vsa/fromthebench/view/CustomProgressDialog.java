package com.vsa.fromthebench.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.vsa.fromthebench.R;

/**
 * Created by Alberto on 02/09/2014.
 */
public class CustomProgressDialog extends ProgressDialog {

    private ObjectAnimator mAnimation;

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

        mAnimation = ObjectAnimator.ofFloat(mImageViewAnimation, "rotation", 0, 360);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setDuration(1500);
        mAnimation.setRepeatCount(Animation.INFINITE);
    }


    @Override
    public void show() {
        super.show();
        mAnimation.start();
    }


}
