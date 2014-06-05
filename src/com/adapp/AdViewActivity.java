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
	String passedVar=null;
	private TextView passedView=null;
	
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
		
		//passedVar=getIntent().getStringExtra("position");
		//passedView=(TextView)findViewById(R.id.ad_view_price);
		//passedView.setText(passedVar);
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
			
			//Intent intent = getIntent();
			//String catName = intent.getStringExtra("categoryName");
			String feedUrl = "http://192.168.0.16:3000/ads.json?category_name="+ "Casa";
			
			AdListModel jParser = new AdListModel();
			JSONArray json = jParser.getJSONFromUrl(feedUrl);
			return json;
			
		}
		
		@Override
		protected void onPostExecute(JSONArray json) {
			dialog.dismiss();
			passedVar=getIntent().getStringExtra("position");
			
			try {
				for (int i = 0; i < json.length(); i++) {
					JSONObject ad = json.getJSONObject(i);
					String id = ad.getString("id");
					if (id == passedVar) {
						break;
					}
//					adsTitlesArray.add(title);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			JSONObject ad = json.getJSONObject(i);
			String id = ad.getString("id");
			Log.i("id", id);
//			adsString = adsTitlesArray.toArray(new String[adsTitlesArray.size()]);
			
//			AdListAdapter adListAdapter = new AdListAdapter(context, adsString);
//			ListView listView = (ListView) findViewById(R.id.ad_list);
//			listView.setAdapter(adListAdapter);

			
			super.onPostExecute(json);
		}
	}
}