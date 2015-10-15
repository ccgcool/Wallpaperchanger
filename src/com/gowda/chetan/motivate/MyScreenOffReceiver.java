package com.gowda.chetan.motivate;

import java.io.File;
import java.io.IOException;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MyScreenOffReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			Log.v("Motivate", "Broadcast triggered");
			WallpaperManager myWallpaperManager = WallpaperManager
					.getInstance(context);
			String sourceFilepath = null;
			try {
				SharedPreferences mySharePref = context.getSharedPreferences(
						"app_pref", Context.MODE_PRIVATE);
				Integer activefile = mySharePref.getInt("activefile", 0);
				Integer fileNum = mySharePref.getInt("Filenumbername", 0);

				sourceFilepath = android.os.Environment
						.getExternalStorageDirectory().getPath()
						+ File.separatorChar + activefile.toString()+ ".jpeg";
				File file = new File(sourceFilepath);
				if(file.exists()) 
				{
					
				myWallpaperManager.setBitmap(BitmapFactory
						.decodeFile(sourceFilepath));
				}

				activefile++;

				if (activefile > fileNum)
					activefile = 0;

				SharedPreferences.Editor sharePrefEditor = mySharePref.edit();
				sharePrefEditor.putInt("activefile", activefile);
				sharePrefEditor.commit();

				Log.v("Motivate", "wallpapersetcompleted");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.v("Motivate", "wallpapersetfailed");
			}
		}
		
	}
}
