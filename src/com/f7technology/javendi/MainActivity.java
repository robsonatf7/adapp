package com.f7technology.javendi;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

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
import com.f7technology.javendi.adapters.AdListAdapter;
import com.f7technology.javendi.adapters.ImageSwipeAdapter;
import com.f7technology.javendi.adapters.SwipeAdapter;
import com.f7technology.javendi.models.AdModel;
import com.facebook.Session;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SharedCode {
	
	Context context;
	String feedUrl, catName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setTitle("Já Vendi!");
		setDrawer();
		setAdMob();
	
		context = this;
		MainListTask loaderTask = new MainListTask();
		loaderTask.execute();
		
	}

	private class ListClickHandler implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> Adapter, View view, int position, long id) {

			Intent y = getIntent();
			catName = y.getStringExtra("categoryName");
			String catUrl = catName.replaceAll("\\s", "%20");
			String feedUrl = "http://192.168.0.11:3000/ads.json?category_name="+ catUrl;
			
			Intent intent = new Intent(context, AdViewActivity.class);
			Bundle extras = new Bundle();
			extras.putString("feedUrl", feedUrl);
			extras.putString("position", String.valueOf(id));
			extras.putString("categoryUrl", catUrl);
			extras.putString("categoryName", catName);
			intent.putExtras(extras);
			startActivity(intent);
		}
	}
	
	public class MainListTask extends AsyncTask<Void, Void, JSONArray> {
		
		ProgressDialog dialog;
		Bitmap bmp;
		ArrayList<String> adsTitlesArray = new ArrayList<String>();
		ArrayList<Bitmap> adsBitmapsArray = new ArrayList<Bitmap>();
		ArrayList<String> imageUrls = new ArrayList<String>();
		String url = "http://192.168.0.11:3000/ads.json";
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setTitle("Loading ads");
			dialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected JSONArray doInBackground(Void... params) {

			AdModel jParser = new AdModel();
			JSONArray json = jParser.getJSONFromUrl(url);
			
			try {
				for (int i = 0; i < json.length(); i++) {
					JSONObject ad = json.getJSONObject(i);
					String title = ad.getString("title");
					adsTitlesArray.add(title);
					String image = ad.getString("image");
					imageUrls.add(image);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < imageUrls.size(); i++) {
				
				String imgUrl = "http://192.168.0.11:3000" + imageUrls.get(i);
				
				try {
			        URL url = new URL(imgUrl);
			        HttpGet httpRequest = null;
			        httpRequest = new HttpGet(url.toURI());
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpResponse response = (HttpResponse) httpclient
			                .execute(httpRequest);

			        HttpEntity entity = response.getEntity();
			        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
			        InputStream input = b_entity.getContent();

			        bmp = BitmapFactory.decodeStream(input);
			        
			    } catch (Exception ex) {
			    	ex.printStackTrace();
			    }
				
				adsBitmapsArray.add(bmp);
			}

			
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					
//					ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
//					SwipeAdapter adapter = new SwipeAdapter(getApplicationContext(), adsBitmapsArray);
//					viewPager.setAdapter(adapter);
					
					AdListAdapter adListAdapter = new AdListAdapter(context, adsTitlesArray, adsBitmapsArray);
					GridView gridView = (GridView) findViewById(R.id.main_list);
					gridView.setAdapter(adListAdapter);
			
					gridView.setOnItemClickListener(new ListClickHandler());
				}
				
			});
			
			return json;
			
		}
		
		@Override
		protected void onPostExecute(JSONArray json) {
			dialog.dismiss();
			super.onPostExecute(json);
		}
	}
}
