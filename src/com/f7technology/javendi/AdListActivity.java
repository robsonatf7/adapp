package com.f7technology.javendi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.f7technology.javendi.R;
import com.f7technology.javendi.adapters.AdListAdapter;
import com.f7technology.javendi.models.AdModel;
import com.facebook.Session;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdListActivity extends DrawerCode {
	
	ArrayList<String> adsTitlesArray = new ArrayList<String>();
	AdListAdapter adListAdapter;
	String[] adsString = {""};
	Context context;
	String feedUrl;
	
	private DrawerLayout drawerLayout;
	private ListView featuresList;
	private String[] features;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_list);
		
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
		
		AdView adView = (AdView)this.findViewById(R.id.adView1);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);

		context = this;
		AdListTask loaderTask = new AdListTask();
		loaderTask.execute();
		
		AdListAdapter adListAdapter = new AdListAdapter(this, adsString);
		ListView listView = (ListView) findViewById(R.id.ad_list);
		listView.setAdapter(adListAdapter);
		listView.setDivider(null);
		
		listView.setOnItemClickListener(new ListClickHandler());
		
	}

	private class ListClickHandler implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> Adapter, View view, int position, long id) {

			Intent i = getIntent();
			String catName = i.getStringExtra("categoryName");
			String catName1 = catName.replaceAll("\\s", "%20");
			String feedUrl = "http://192.168.0.11:3000/ads.json?category_name="+ catName1;
			
			Intent intent = new Intent(context, AdViewActivity.class);
			Bundle extras = new Bundle();
			extras.putString("feedUrl", feedUrl);
			extras.putString("position", String.valueOf(id));
			intent.putExtras(extras);
			startActivity(intent);
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
			
			String feedUrl;
			Intent intent = getIntent();
			
			if (intent.getStringExtra("categoryName") != null) {
				String catName = intent.getStringExtra("categoryName");
				String catName1 = catName.replaceAll("\\s", "%20");
				System.out.println(catName1);
				feedUrl = "http://192.168.0.11:3000/ads.json?category_name="+ catName1;
			} else {
				String userEmail = intent.getStringExtra("userEmail");
				feedUrl = "http://192.168.0.11:3000/ads.json?user_email="+ userEmail;
			}

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