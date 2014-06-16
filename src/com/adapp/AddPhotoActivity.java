package com.adapp;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
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

				Thread t = new Thread() {

					public void run() {
			            	
						Intent i = getIntent();
						String adCategory = i.getStringExtra("categoryId");
						String adTitle = i.getStringExtra("title");
						String adPrice = i.getStringExtra("price");
						String adDescription = i.getStringExtra("description");
			            
						int catId = Integer.parseInt(adCategory);
						
			            Looper.prepare();
			            HttpClient client = new DefaultHttpClient();
			            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
			            HttpResponse response;
			            JSONObject json = new JSONObject();

			            try {
			            	HttpPost post = new HttpPost("http://192.168.0.16:3000/ads");
			                json.put("category_id", catId);
			                json.put("title", adTitle);
			                json.put("price", adPrice);
			                json.put("description", adDescription);
			                StringEntity se = new StringEntity( json.toString());  
			                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			                post.setEntity(se);
			                response = client.execute(post);

			                if(response!=null){
			                	InputStream in = response.getEntity().getContent(); //Get the data in the entity
			                }

			            } catch(Exception e) {
			            	e.printStackTrace();
			            }

			            	Looper.loop();
			            }
			        };

			        t.start();      

				Intent intent = new Intent(context, NewAdActivity.class);
				startActivity(intent);
			}
		});
	}
	
}