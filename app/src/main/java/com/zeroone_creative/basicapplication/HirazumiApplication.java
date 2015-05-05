package com.zeroone_creative.basicapplication;

import android.app.Application;

import com.deploygate.sdk.DeployGate;

/**
 * Created by shunhosaka on 2015/04/13.
 */
public class HirazumiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //DeployGate.install(this);
        DeployGate.install(this, null, true);
    }
}