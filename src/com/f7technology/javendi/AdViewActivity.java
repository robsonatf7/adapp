package com.f7technology.javendi;

import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.f7technology.javendi.R;
import com.f7technology.javendi.models.AdModel;
import com.facebook.Session;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AdViewActivity extends SharedCode implements AsyncResponse {

	Button buy, back;
	Context context;
	String position = null;
	String feedUrl = null;
	AdModel jParser = new AdModel();
	String mailTo = "";
	String categoryName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_view);
		
		Intent ii = getIntent();
		Bundle ee = ii.getExtras();
		categoryName = ee.getString("categoryName");
		
		setTitle(categoryName);
		setDrawer();
		setAdMob();
		
		context = this;
		AdViewTask loaderTask = new AdViewTask();
		loaderTask.delegate = this;
		loaderTask.execute();
		
		onClickBuy();
		onClickBack();
	}

	public class AdViewTask extends AsyncTask<Void, Void, JSONArray> {
		
		public AsyncResponse delegate = null;
		ProgressDialog dialog;
		String sellerEmail = "";
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setTitle("Loading ads");
			dialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected JSONArray doInBackground(Void... params) {
			
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			position = extras.getString("position");
			int positionInt = Integer.parseInt(position);
			
			Intent i = getIntent();
			Bundle e = i.getExtras();
			feedUrl = e.getString("feedUrl");
			
			String price = "";
			String description = "";
			String image = "";
			
			AdModel jParser = new AdModel();
			JSONArray json = jParser.getJSONFromUrl(feedUrl);

			try {
				JSONObject ad = json.getJSONObject(positionInt);
				price = ad.getString("price");
				description = ad.getString("description");
				image = ad.getString("image");
				sellerEmail = ad.getString("user_email");
			} catch (JSONException ex) {
				ex.printStackTrace();
			}

			TextView viewPrice = (TextView) findViewById(R.id.ad_view_price);
			viewPrice.setText(price);
			
			TextView viewDescription = (TextView) findViewById(R.id.ad_view_description);
			viewDescription.setText(description);

			final String imageURL = "http://192.168.0.11:3000" + image;
			ImageView img = (ImageView)findViewById(R.id.ad_view_photo_image);
			try {
			        URL url = new URL(imageURL);
			        HttpGet httpRequest = null;
			        httpRequest = new HttpGet(url.toURI());
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpResponse response = (HttpResponse) httpclient
			                .execute(httpRequest);

			        HttpEntity entity = response.getEntity();
			        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
			        InputStream input = b_entity.getContent();

			        Bitmap bitmap = BitmapFactory.decodeStream(input);
			        img.setImageBitmap(bitmap);
			        
			    } catch (Exception ex) {
			    	ex.printStackTrace();
			    }
			return json;
		}
		
		@Override
		protected void onPostExecute(JSONArray json) {
			dialog.dismiss();
			delegate.processFinish(sellerEmail);
		}
	}
	
	@Override
	public void processFinish(String output) {
		mailTo = output;
	}
	
	private void onClickBuy() {
		final Context context = this;
		buy = (Button) findViewById(R.id.ad_view_buy);
		buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { mailTo });
				email.setType("message/rfc822");
				
				startActivity(Intent.createChooser(email, "Choose an Email client :"));
			}
		});
	}
	

	private void onClickBack() {
		final Context context = this;
		back = (Button) findViewById(R.id.ad_view_back);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, AdListActivity.class);
				intent.putExtra("categoryName", categoryName);
				startActivity(intent);
				
			}
		});
	}

}