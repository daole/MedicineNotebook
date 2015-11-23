package com.dreamdigitizers.medicinenote.utils;

import android.os.Handler;
import android.support.v7.appcompat.BuildConfig;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.ref.WeakReference;

public class AdListener extends com.google.android.gms.ads.AdListener {
    private static final int REFRESH_TIME = 10000;
    private WeakReference<AdView> mAdView;

    private Handler mRefreshHandler;
    private Runnable mRefreshRunnable;
    private boolean mFirstAdReceived;

    public AdListener(AdView pAdView) {
        this.mAdView = new WeakReference<>(pAdView);
        this.mRefreshHandler = new Handler();
        this.mRefreshRunnable = new Runnable() {
            @Override
            public void run() {
                AdListener.this.requestAd();
            }
        };
        this.requestAd();
    }

    @Override
    public void onAdLoaded() {
        this.mFirstAdReceived = true;
        AdView adView = this.mAdView.get();
        if(adView != null) {
            adView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAdFailedToLoad(int pErrorCode) {
        if (!this.mFirstAdReceived) {
            AdView adView = this.mAdView.get();
            if(adView != null) {
                adView.setVisibility(View.GONE);
                this.mRefreshHandler.postDelayed(this.mRefreshRunnable, AdListener.REFRESH_TIME);
            }
        }
    }

    private void requestAd() {
        AdRequest adRequest;
        if (BuildConfig.DEBUG) {
            adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("BA60322C3D2E065E9975FB53C2D78F32").build();
        } else {
            adRequest = new AdRequest.Builder().build();
        }
        AdView adView = this.mAdView.get();
        if(adView != null) {
            adView.loadAd(adRequest);
        }
    }
}
