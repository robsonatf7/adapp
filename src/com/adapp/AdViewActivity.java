package com.adapp;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
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
			
			Intent i = getIntent();
			Bundle e = i.getExtras();
			feedUrl = e.getString("feedUrl");
			
			AdListModel jParser = new AdListModel();
			JSONArray json = jParser.getJSONFromUrl(feedUrl);
			return json;
			
		}
		
		@Override
		protected void onPostExecute(JSONArray json) {
			dialog.dismiss();
			
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			
			position = extras.getString("position");
			int positionInt = Integer.parseInt(position);
			
			String price = "";
			String description = "";
			
			try {
				JSONObject ad = json.getJSONObject(positionInt);
				price = ad.getString("price");
				description = ad.getString("description");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			TextView viewPrice = (TextView) findViewById(R.id.ad_view_price);
			viewPrice.setText(price);
			
			TextView viewDescription = (TextView) findViewById(R.id.ad_view_description);
			viewDescription.setText(description);

			
			super.onPostExecute(json);
		}
	}
}