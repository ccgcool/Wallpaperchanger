package com.gowda.chetan.motivate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Intent motivateServiceintent = new Intent(context, MotivateService.class);
		context.startService(motivateServiceintent);
		
		Toast.makeText(context,"BroadCast received", Toast.LENGTH_LONG).show();
		Log.v("Motivate", "BootCompletedBroadcastReceived");
	}
}
