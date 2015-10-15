package com.gowda.chetan.motivate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ImageView picView;
	Intent receivedIntent;
	String receivedAction;
	String receivedType;
	private Uri receivedUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		picView = (ImageView)findViewById(R.id.picture);
		receivedIntent = getIntent();
		String receivedAction = receivedIntent.getAction();
		String receivedType = receivedIntent.getType();
		
		Intent intent = new Intent(getApplicationContext(), MotivateService.class);
		startService(intent);
		
		Toast.makeText(this,"Motivate Service started", Toast.LENGTH_LONG).show();
		Log.v("Motivate","Motivate Service started");

		if(receivedAction.equals(Intent.ACTION_SEND)){
			//content is being shared					
			if(receivedType.startsWith("image/")){

				receivedUri = (Uri)receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
				if (receivedUri != null) {
					//set the picture
					//RESAMPLE YOUR IMAGE DATA BEFORE DISPLAYING
					Toast.makeText(this,"deBUG", Toast.LENGTH_LONG).show();

					picView.setImageURI(receivedUri);//just for demonstration
					
				}
				else
				{
					Toast.makeText(this,"Not_Image.ACTION_SEND" + receivedAction, Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(this,"Not_Intent.ACTION_SEND" + receivedAction, Toast.LENGTH_LONG).show();
			}
		}
		else if(receivedAction.equals(Intent.ACTION_MAIN)){
			//app has been launched directly, not from share list
		}
		else
		{
			Toast.makeText(this,receivedAction, Toast.LENGTH_LONG).show();
		}

		picView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WallpaperManager myWallpaperManager 
				= WallpaperManager.getInstance(getApplicationContext());
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int screenHeight = size.y;
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				String destinationFilename = null;

				try {
					Bitmap bm = BitmapFactory.decodeFile(receivedUri.getPath());
					int width = bm.getWidth();
					width = (width * screenHeight) / bm.getHeight();
					myWallpaperManager.setBitmap(Bitmap.createScaledBitmap(bm, width, screenHeight, true));
					String sourceFilename= receivedUri.getPath();

					SharedPreferences mySharePref = getSharedPreferences("app_pref",Context.MODE_PRIVATE);
					Integer fileNum = mySharePref.getInt("Filenumbername", 0);

					fileNum++;
					fileNum.toString();

					destinationFilename = android.os.Environment.getExternalStorageDirectory().getPath()+File.separatorChar+fileNum.toString()+".jpeg";

					bis = new BufferedInputStream(new FileInputStream(sourceFilename));
					bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
					byte[] buf = new byte[1024];
					bis.read(buf);
					do {
						bos.write(buf);
					} while(bis.read(buf) != -1);
					SharedPreferences.Editor sharePrefEditor = mySharePref.edit();
					sharePrefEditor.putInt("Filenumbername", fileNum);
					sharePrefEditor.commit();
					// myWallpaperManager.setStream(is);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally 
				{
					try {
						if (bis != null) bis.close();
						if (bos != null) bos.close();
					} catch (IOException e) {

					}

				}
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
