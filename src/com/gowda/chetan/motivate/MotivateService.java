package com.gowda.chetan.motivate;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class MotivateService extends Service {
	IntentFilter intfil = null;
	BroadcastReceiver screenOffReceiver = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		IntentFilter intfil = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		BroadcastReceiver screenOffReceiver = new MyScreenOffReceiver();
		getApplicationContext().registerReceiver(screenOffReceiver, intfil);
		
		Log.v("Motivate","MotivateServicestarted");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
//		IntentFilter intfil = new IntentFilter(Intent.ACTION_SCREEN_OFF);
//		BroadcastReceiver screenOffReceiver = new MyScreenOffReceiver();
//		getApplicationContext().registerReceiver(screenOffReceiver, intfil);
//		
//		Log.v("Motivate","MotivateServicestarted");
		
		return super.onStartCommand(intent, flags, startId);

	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getApplicationContext().unregisterReceiver(screenOffReceiver);
		Log.v("Motivate","MotivateServiceDestroyed");
	}

}
