package com.adapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapp.models.CategoryListModel;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class NewAdActivity extends Activity {
	
	Button createAd;
	Context context;
	String feedUrl = "http://192.168.0.16:3000/categories.json";
	ArrayList<SpinnerCategory> spinnerCategories = new ArrayList<SpinnerCategory>();
	ArrayList<String> spinnerCategoryNames = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_ad);
		
		context = this;
		NewAdSpinnerTask loaderTask = new NewAdSpinnerTask();
		loaderTask.execute();
		
		onClickCreateAd();
		
	}

	public class SpinnerCategory {
		private String name;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public class NewAdSpinnerTask extends AsyncTask<Void, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Void... params) {
			CategoryListModel jParser = new CategoryListModel();
			JSONArray json = jParser.getJSONFromUrl(feedUrl);
			return json;
		}
		
		protected void onPostExecute(JSONArray json){
			
			try {
				for (int i = 0; i < json.length(); i++) {
					JSONObject category = json.getJSONObject(i);
					SpinnerCategory spinnerCategory = new SpinnerCategory();
					spinnerCategory.setName(category.optString("name"));
					spinnerCategoryNames.add(category.optString("name"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Spinner mySpinner = (Spinner) findViewById(R.id.new_ad_category);
			mySpinner.setAdapter (new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, spinnerCategoryNames));
			
		}
		
	}
	
	private void onClickCreateAd() {
		
		final Context context = this;
		createAd = (Button) findViewById(R.id.new_ad_button);
		createAd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Spinner adCategory = (Spinner)findViewById(R.id.new_ad_category);
				String value1 = adCategory.getSelectedItem().toString();
				
				TextView adTitle = (TextView)findViewById(R.id.new_ad_title);
				String value2 = adTitle.getText().toString();

				TextView adPrice = (TextView)findViewById(R.id.new_ad_price);
				String value3 = adPrice.getText().toString();
				
				TextView adDescription = (TextView)findViewById(R.id.new_ad_description);
				String value4 = adDescription.getText().toString();
				
				Intent intent = new Intent(context, AddPhotoActivity.class);
				Bundle extras = new Bundle();
				
				extras.putString("category", value1);
				extras.putString("title", value2);
				extras.putString("price", value3);
				extras.putString("description", value4);
				intent.putExtras(extras);
				
				startActivity(intent);
			}
		});
	}

}
