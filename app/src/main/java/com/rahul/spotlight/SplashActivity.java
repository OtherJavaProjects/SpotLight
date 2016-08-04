package com.rahul.spotlight;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;

public class SplashActivity extends Activity {



	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(getApplicationContext(), SpotlightActivity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, 3000);
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
