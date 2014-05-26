package com.adapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapp.adapters.CategoryListAdapter;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CategoryListActivity extends Activity {

		Button newAd;
//		static final String[] Categories = new String[] {
//			"Casa", "Carro", "Celular"
//		};
		ListView categoryList;
		ArrayList<String> categoryArrayList = new ArrayList<String>();
		ArrayAdapter<String> categoryAdapter;
		Context context;
		String feedUrl = "http://192.168.0.16:3000/categories.json";
			
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.category_list);
			context = this;
			
//			CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this, Categories);
//			ListView listView = (ListView) findViewById(R.id.listView1);
//			listView.setAdapter(categoryListAdapter);
			
//			categoryList.setOnItemClickListener(new ListClickHandler());
			
			CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this, categoryArrayList);
			
//			categoryList = (ListView) findViewById(R.id.category_list_view);
//			categoryAdapter = new ArrayAdapter<String>(this, R.layout.category_row, 0, categoryArrayList);
//			categoryList.setAdapter(categoryAdapter);
			
			CategoryListTask loaderTask = new CategoryListTask();
			loaderTask.execute();
			
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
				String text = listText.getText().toString();
				
				Toast.makeText(context, text + "Clicked at Position" + position, Toast.LENGTH_SHORT).show();
			}
			

		}
		
		public class CategoryListTask extends AsyncTask<Void, Void, Void> {
			
			ProgressDialog dialog;

			@Override
			protected void onPreExecute() {
				dialog = new ProgressDialog(context);
				dialog.setTitle("Loading videos");
				dialog.show();
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				
				HttpClient client = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet(feedUrl);
				
				try {
					HttpResponse response = client.execute(getRequest);
					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					
					if(statusCode != 200) {
						return null;
					}
				
					InputStream jsonStream = response.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream));
					StringBuilder builder = new StringBuilder();
					String line;
					
					while((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					String jsonData = builder.toString();
					
					JSONObject json = new JSONObject(jsonData);
					JSONArray categories = json.getJSONArray("categories");
					for (int i = 0; i <categories.length(); i++) {
						JSONObject object = categories.getJSONObject(i);
						JSONObject category = object.getJSONObject("category");
						categoryArrayList.add(category.getString("name"));
					}
					
//					JSONObject json = new JSONObject(jsonData);
//					JSONObject categories = json.getJSONObject("categories");
//					JSONArray category = categories.getJSONArray("category");
					
//					for (int i = 0; i < category.length(); i++) {
//						JSONObject cat = category.getJSONObject(i);
//						categoryArrayList.add(cat.getString("name"));
//					}
					
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				return null;
			
			}
			
			
			@Override
			protected void onPostExecute(Void result) {
				dialog.dismiss();
				categoryAdapter.notifyDataSetChanged();
				super.onPostExecute(result);
			}
			
		}
	
}