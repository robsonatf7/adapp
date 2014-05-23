package com.adapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class AddPhotoActivity extends Activity {

	Button finishAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_photo);
		onClickFinishAd();
	}

	private void onClickFinishAd() {
		
		final Context context = this;
		finishAd = (Button) findViewById(R.id.add_photo_finish_button);
		finishAd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ProductViewActivity.class);
				startActivity(intent);
			}
		});
	}
	
}