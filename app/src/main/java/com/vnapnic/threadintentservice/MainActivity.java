package com.vnapnic.threadintentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getName();

    TextView txtIn, txtOut;
    private MyBroadCastReceiver myBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtIn = (TextView) findViewById(R.id.fishService);
        txtOut = (TextView) findViewById(R.id.resultService);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "fab.setOnClickListener");
                Intent intent = new Intent(MainActivity.this, IntentServiceSample.class);
                intent.putExtra(IntentServiceSample.EXTRA_KEY_IN, "MainActivity Start service");
                startService(intent);
            }
        });

        myBroadCastReceiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter(IntentServiceSample.ACTION_INTENT);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadCastReceiver, filter);
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        private final String TAG = MyBroadCastReceiver.class.getName();
        private OnCountDown onCountDown = new OnCountDown(System.currentTimeMillis(), 1000);

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");

            String start = intent.getStringExtra(IntentServiceSample.EXTRA_KEY_IN);
            if (!TextUtils.isEmpty(start)) {
                onCountDown.setContent(start);
            }

            String result = intent.getStringExtra(IntentServiceSample.EXTRA_KEY_OUT);
            txtOut.setText(TextUtils.isEmpty(result) ? "" : result);

            boolean isCountDown = intent.getBooleanExtra(IntentServiceSample.EXTRA_KEY_COUNT_DOWN, false);
            if (isCountDown) {
                onCountDown.start();
            } else {
                onCountDown.cancel();
            }


        }
    }

    class OnCountDown extends CountDownTimer {

        private String content;
        int i;

        public void setContent(String content) {
            this.content = content;
        }

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public OnCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            txtIn.setText(this.content + " " + i++ + "");
        }

        @Override
        public void onFinish() {

        }
    }
}
