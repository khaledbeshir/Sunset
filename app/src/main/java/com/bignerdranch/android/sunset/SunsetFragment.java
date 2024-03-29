package com.bignerdranch.android.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import java.time.Duration;

/**
 * Created by Mohamed Amr on 6/7/2019.
 */

public class SunsetFragment extends Fragment {
    private static final String TAG = "SunSetFragment";
    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    public static SunsetFragment newImstance (){
        return new SunsetFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sunset ,container , false);
        mSceneView = v;
        mSunView = v.findViewById(R.id.sun);
        mSkyView = v.findViewById(R.id.sky);
        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });
        return v;
    }

    private void startAnimation (){
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        Log.i(TAG , "SunYStart = " + sunYStart);
        Log.i(TAG , "SunYEnd = " + sunYEnd);

        ObjectAnimator heightAnimator = ObjectAnimator.
                ofFloat(mSunView , "y" ,sunYStart ,sunYEnd)
                .setDuration(3000);
        //heightAnimator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView , "backgroundColor" , mBlueSkyColor , mSunsetSkyColor)
                .setDuration(3000);

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView ,"backgroundColor" , mSunsetSkyColor ,mNightSkyColor )
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
    }
}
