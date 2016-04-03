package com.vnapnic.threadintentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by vn apnic on 4/3/2016.
 */
public class IntentServiceSample extends IntentService {
    private final String TAG = IntentServiceSample.class.getName();

    public static final String ACTION_INTENT = "IntentService_Sample";
    public static final String EXTRA_KEY_IN = "intent_start";
    public static final String EXTRA_KEY_OUT = "intent_stop";
    public static final String EXTRA_KEY_COUNT_DOWN = "intent_count_down";
    private Intent intentBroadCastReceriver;

    public IntentServiceSample() {
        super("");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        String start = intent.getStringExtra(EXTRA_KEY_IN);
        intentBroadCastReceriver = new Intent();
        intentBroadCastReceriver.setAction(ACTION_INTENT);
        intentBroadCastReceriver.addCategory(Intent.CATEGORY_DEFAULT);
        intentBroadCastReceriver.putExtra(EXTRA_KEY_IN, start);
        intentBroadCastReceriver.putExtra(EXTRA_KEY_COUNT_DOWN, true);
        sendBroadcast(intentBroadCastReceriver);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = start + " service result";
        intentBroadCastReceriver = new Intent();
        intentBroadCastReceriver.setAction(ACTION_INTENT);
        intentBroadCastReceriver.addCategory(Intent.CATEGORY_DEFAULT);
        intentBroadCastReceriver.putExtra(EXTRA_KEY_OUT, result);
        intentBroadCastReceriver.putExtra(EXTRA_KEY_COUNT_DOWN, false);
        sendBroadcast(intentBroadCastReceriver);
    }
}
