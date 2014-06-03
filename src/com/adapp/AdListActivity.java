package com.adapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapp.adapters.AdListAdapter;
import com.adapp.models.AdModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdListActivity extends Activity {
	
	Button newAdOne;
	ArrayList<String> adsTitlesArray = new ArrayList<String>();
	AdListAdapter adListAdapter;
	String[] adsString = {""};
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ad_list);
		
		context = this;
		AdListTask loaderTask = new AdListTask();
		loaderTask.execute();
		
		AdListAdapter adListAdapter = new AdListAdapter(this, adsString);
		ListView listView = (ListView) findViewById(R.id.ad_list);
		listView.setAdapter(adListAdapter);
		
		listView.setOnItemClickListener(new ListClickHandler());
		
		onClickNewAd();
	}

	public void onClickNewAd() {
		
		final Context context = this;
		newAdOne = (Button) findViewById(R.id.ad_list_button);
		newAdOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, NewAdActivity.class);
				startActivity(intent);
			}
		});
	}

	private class ListClickHandler implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> Adapter, View view, int position, long arg3) {
			
			TextView listText = (TextView) view.findViewById(R.id.ad_row_text);
			String text = listText.getText().toString();
			
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
	}
	
	public class AdListTask extends AsyncTask<Void, Void, JSONArray> {
		
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
			String catName = intent.getStringExtra("categoryName");
			String feedUrl = "http://192.168.0.16:3000/ads.json?category_name="+ catName;
			
			AdModel jParser = new AdModel();
			JSONArray json = jParser.getJSONFromUrl(feedUrl);
			return json;
			
		}
		
		@Override
		protected void onPostExecute(JSONArray json) {
			dialog.dismiss();
			
			try {
				for (int i = 0; i < json.length(); i++) {
					JSONObject ad = json.getJSONObject(i);
					String title = ad.getString("title");
					adsTitlesArray.add(title);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			adsString = adsTitlesArray.toArray(new String[adsTitlesArray.size()]);
			
			AdListAdapter adListAdapter = new AdListAdapter(context, adsString);
			ListView listView = (ListView) findViewById(R.id.ad_list);
			listView.setAdapter(adListAdapter);
			
			super.onPostExecute(json);
		}
	}
}