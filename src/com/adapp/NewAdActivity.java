package com.adapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class NewAdActivity extends Activity {
	
	Button createAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_ad);
		onClickCreateAd();
	}

	private void onClickCreateAd() {
		
		final Context context = this;
		createAd = (Button) findViewById(R.id.new_ad_button);
		createAd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AddPhotoActivity.class);
				startActivity(intent);
			}
		});
	}

}
