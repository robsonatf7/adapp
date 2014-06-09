package com.adapp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

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
		
	}

	private void onClickFinishAd() {
		
		final Context context = this;
		finishAd = (Button) findViewById(R.id.ad_photo_finish_button);
		finishAd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = getIntent();
				String adCategory = i.getStringExtra("category");
				String adTitle = i.getStringExtra("title");
				String adPrice = i.getStringExtra("price");
				String adDescription = i.getStringExtra("description");
				
				JSONObject newAd = new JSONObject();
				try {
					newAd.put("category", adCategory);
					newAd.put("title", adTitle);
					newAd.put("price", adPrice);
					newAd.put("description", adDescription);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String jsonStr = newAd.toString();
				
				int TIMEOUT_MILLISEC = 10000;
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);
				
				HttpPost request = new HttpPost(serverUrl);
				request.setEntity(new ByteArrayEntity(postMessage.toString().getBytes("UTF8")));
				HttpResponse response = client.execute(request);
				
				Intent intent = new Intent(context, NewAdActivity.class);
				startActivity(intent);
			}
		});
	}
	
}