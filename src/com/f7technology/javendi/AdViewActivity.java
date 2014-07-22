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
import android.view.View;
import android.view.View.OnClickListener;

public class AdViewActivity extends DrawerCode {

	Button buy;
	Context context;
	String position = null;
	String feedUrl = null;
	AdModel jParser = new AdModel();
	
	private DrawerLayout drawerLayout;
	private ListView featuresList;
	private String[] features;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_view);
		
		Session session = Session.getActiveSession();
		if(session != null && session.isOpened()) {
			features = getResources().getStringArray(R.array.loggedin);
			drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			featuresList = (ListView) findViewById(R.id.left_drawer);
			featuresList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, features));
			featuresList.setOnItemClickListener(this);
		} else {
			features = getResources().getStringArray(R.array.loggedout);
			drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			featuresList = (ListView) findViewById(R.id.left_drawer);
			featuresList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, features));
			featuresList.setOnItemClickListener(this);
		}
		
		AdView adView = (AdView)this.findViewById(R.id.adView2);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
		
		context = this;
		AdViewTask loaderTask = new AdViewTask();
		loaderTask.execute();
		
		onClickBuy();
	}
	
	public class AdViewTask extends AsyncTask<Void, Void, JSONArray> {
		
		ProgressDialog dialog;
		
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
		}
	}
	
	private void onClickBuy() {
		final Context context = this;
		buy = (Button) findViewById(R.id.ad_view_buy);
		buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SendMessageActivity.class);
				startActivity(intent);
			}
		});
	}
}