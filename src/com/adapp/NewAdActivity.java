package com.adapp;

import java.util.ArrayList;

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
	JSONObject adData = new JSONObject();
	
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
			
			
			String value1 = "category";
			
			String value2 = "price";
			
			String value3 = "description";
			
			try {
				adData.accumulate("category", value1);
				adData.accumulate("price", value2);
				adData.accumulate("description", value3);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void onClickCreateAd() {
		
		final Context context = this;
		createAd = (Button) findViewById(R.id.new_ad_button);
		createAd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AddPhotoActivity.class);
				startActivity(intent);
			}
		});
	}

}
