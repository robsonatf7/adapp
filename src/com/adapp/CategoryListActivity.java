package com.adapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapp.adapters.CategoryListAdapter;
import com.adapp.models.CategoryListModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryListActivity extends Activity {

		Button newAd;
		ArrayList<String> categoriesNamesArray = new ArrayList<String>();
		CategoryListAdapter categoryListAdapter;
		String[] categoriesString = {""};
		JSONArray categoriesJson = new JSONArray();
		Context context;
		String feedUrl = "http://192.168.0.16:3000/categories.json";
			
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.category_list);
			
			context = this;
			CategoryListTask loaderTask = new CategoryListTask();
			loaderTask.execute();
						
			CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this, categoriesString);	
			ListView listView = (ListView) findViewById(R.id.category_list_view);
			listView.setAdapter(categoryListAdapter);
			
			listView.setOnItemClickListener(new ListClickHandler());
		
			onClickNewAd();
			
		}
		
		public void onClickNewAd() {
			
			final Context context = this;
			newAd = (Button) findViewById(R.id.category_list_button);
			newAd.setOnClickListener(new OnClickListener() {
				
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
				CategoryListModel jParser = new CategoryListModel();
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