package com.adapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AddPhotoActivity extends Activity {

	Button finishAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_photo);
		onClickFinishAd();
		
		Intent intent = getIntent();
		String adCategory = intent.getStringExtra("category");
		String adTitle = intent.getStringExtra("title");
		String adPrice = intent.getStringExtra("price");
		String adDescription = intent.getStringExtra("description");
		
		Log.i("oioioioi", adCategory);
		Log.i("alalala", adTitle);
		Log.i("nononono", adPrice);
		Log.i("bubububu", adDescription);
	}

	private void onClickFinishAd() {
		
		final Context context = this;
		finishAd = (Button) findViewById(R.id.ad_photo_finish_button);
		finishAd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdViewActivity.class);
				startActivity(intent);
			}
		});
	}
	
}