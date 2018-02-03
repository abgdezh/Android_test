package com.example.mb.myapplication;


/**
 * Created by mb on 01.02.18.
 */

import com.vk.sdk.VKSdk;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }


}
