package com.f7technology.javendi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.f7technology.javendi.R;
import com.f7technology.javendi.adapters.CategoryListAdapter;
import com.f7technology.javendi.adapters.ImageSwipeAdapter;
import com.f7technology.javendi.models.CategoryModel;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryListActivity extends DrawerCode {

		ArrayList<String> categoriesNamesArray = new ArrayList<String>();
		CategoryListAdapter categoryListAdapter;
		String[] categoriesString = {""};
		JSONArray categoriesJson = new JSONArray();
		Context context;
		String feedUrl = "http://192.168.0.11:3000/categories.json";
		
		private DrawerLayout drawerLayout;
		private ListView featuresList;
		private String[] features;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.category_list);
			
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
			
			AdView adView = (AdView)this.findViewById(R.id.adView);
		    AdRequest adRequest = new AdRequest.Builder().build();
		    adView.loadAd(adRequest);
			
			context = this;
			CategoryListTask loaderTask = new CategoryListTask();
			loaderTask.execute();
				
			ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
			ImageSwipeAdapter adapter = new ImageSwipeAdapter (this);
			viewPager.setAdapter(adapter);
			
			CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this, categoriesString);	
			ListView listView = (ListView) findViewById(R.id.category_list_view);
			listView.setAdapter(categoryListAdapter);
			
			listView.setOnItemClickListener(new ListClickHandler());
			
		}
		
		private class ListClickHandler implements OnItemClickListener {
			@Override
			public void onItemClick(AdapterView<?> Adapter, View view, int position, long arg3) {
				
				TextView listText = (TextView) view.findViewById(R.id.category_row_text);
				String categoryName = listText.getText().toString();

				Intent intent = new Intent(context, AdListActivity.class);
				intent.putExtra("categoryName", categoryName);
				startActivity(intent);
			}
		}
		
		public class CategoryListTask extends AsyncTask<Void, Void, JSONArray> {
			
			ProgressDialog dialog;

			@Override
			protected void onPreExecute() {
				dialog = new ProgressDialog(context);
				dialog.setTitle("Loading categories");
				dialog.show();
				super.onPreExecute();
			}

			@Override
			protected JSONArray doInBackground(Void... params) {
				CategoryModel jParser = new CategoryModel();
				JSONArray json = jParser.getJSONFromUrl(feedUrl);
				return json;
			}
			
			@Override
			protected void onPostExecute(JSONArray json) {
				dialog.dismiss();
				
				try{
					for (int i = 0; i < json.length(); i++) {
						JSONObject category = json.getJSONObject(i);
						String name = category.getString("name");
						categoriesNamesArray.add(name);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				categoriesString = categoriesNamesArray.toArray(new String[categoriesNamesArray.size()]);
				
				CategoryListAdapter categoryListAdapter = new CategoryListAdapter(context, categoriesString);	
				ListView listView = (ListView) findViewById(R.id.category_list_view);
				listView.setAdapter(categoryListAdapter);
				
				super.onPostExecute(json);
			}
			
		}
}