package com.adapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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

import com.adapp.AdListActivity.AdListTask;
import com.adapp.adapters.AdListAdapter;
import com.adapp.models.AdListModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AdViewActivity extends Activity{

	Button buy, pro, cat;
	Context context;
	String position = null;
	String feedUrl = null;
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_view);
		onClickBuy();
		onClickPro();
		onClickCat();
		
		context = this;
		AdViewTask loaderTask = new AdViewTask();
		loaderTask.execute();
	}

	private void onClickCat() {
		final Context context = this;
		cat = (Button) findViewById(R.id.ad_view_cat_button);
		cat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CategoryListActivity.class);
				startActivity(intent);
			}
		});
	}

	private void onClickPro() {
		final Context context = this;
		pro = (Button) findViewById(R.id.ad_view_pro_button);
		pro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdListActivity.class);
				startActivity(intent);
			}
		});
	}

	private void onClickBuy() {
		final Context context = this;
		buy = (Button) findViewById(R.id.ad_view_buy_button);
		buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SendMessageActivity.class);
				startActivity(intent);
			}
		});
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
			//String imageURL = "";
			
			AdListModel jParser = new AdListModel();
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
			
			//String imageURL = "http://192.168.0.16:3000" + image;
			
			String imageURL = "http://192.168.0.16:3000" + image;
			
			img = (ImageView) findViewById(R.id.ad_view_photo_image);
			try {
			        URL url = new URL(imageURL);
			        //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
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

			    }
			return json;
		}
		
		@Override
		protected void onPostExecute(JSONArray json) {
			dialog.dismiss();
			
//			Intent intent = getIntent();
//			Bundle extras = intent.getExtras();
//			
//			position = extras.getString("position");
//			int positionInt = Integer.parseInt(position);
//			
//			String price = "";
//			String description = "";
//			String image = "";
//			//String imageURL = "";
//			
//			try {
//				JSONObject ad = json.getJSONObject(positionInt);
//				price = ad.getString("price");
//				description = ad.getString("description");
//				image = ad.getString("image");
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			
//			TextView viewPrice = (TextView) findViewById(R.id.ad_view_price);
//			viewPrice.setText(price);
//			
//			TextView viewDescription = (TextView) findViewById(R.id.ad_view_description);
//			viewDescription.setText(description);
//			
//			//String imageURL = "http://192.168.0.16:3000" + image;

			//WebView web = (WebView) findViewById(R.id.ad_webview_photo_image);
			//web.loadUrl(imageURL);
			
			super.onPostExecute(json);
		}
	}
}