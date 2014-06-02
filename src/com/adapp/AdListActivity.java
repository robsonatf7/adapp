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

import com.adapp.adapters.AdListAdapter;

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
import android.widget.Toast;

public class AdListActivity extends Activity {
	
	Button newAdOne;
	ArrayList<String> adsArray = new ArrayList<String>();
	AdListAdapter adListAdapter;
	String[] adsString = {""};
	Context context;
//	Intent intent = getIntent();
//	System.out.println(intent);
//	String catName = intent.getStringExtra("categoryName");
//	String feedUrl = "http://192.168.0.16:3000/ads.json?category_name="+ catName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//	}
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
	
	public class AdListTask extends AsyncTask<Void, Void, String[]> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setTitle("Loading ads");
			dialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected String[] doInBackground(Void... params) {
			
			Intent intent = getIntent();
			//System.out.println(intent);
			String catName = intent.getStringExtra("categoryName");
			String feedUrl = "http://192.168.0.16:3000/ads.json?category_name="+ catName;
			
			HttpClient client = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(feedUrl);
			
			try {
				HttpResponse response = client.execute(getRequest);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				
				if (statusCode != 200) {
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
				
				JSONArray json = new JSONArray(jsonData);
				for (int i = 0; i < json.length(); i++) {
					JSONObject object = json.getJSONObject(i);
					JSONObject ad = object.getJSONObject("ad");
					String title = ad.getString("title");
					adsArray.add(title);
				}
				
				adsString = adsArray.toArray(new String[adsArray.size()]);
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return adsString;
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			dialog.dismiss();
			
			AdListAdapter adListAdapter = new AdListAdapter(context, result);
			ListView listView = (ListView) findViewById(R.id.ad_list);
			listView.setAdapter(adListAdapter);
			
			super.onPostExecute(result);
		}
	}
}