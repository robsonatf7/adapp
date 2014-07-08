package com.adapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapp.models.CategoryModel;
import com.facebook.Session;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class NewAdActivity extends Activity implements OnItemClickListener {
	
	Button createAd;
	Context context;
	String feedUrl = "http://192.168.0.16:3000/categories.json";
	ArrayList<SpinnerCategory> spinnerCategories = new ArrayList<SpinnerCategory>();
	ArrayList<String> spinnerCategoryNames = new ArrayList<String>();
	ArrayList<Integer> spinnerCategoryIds = new ArrayList<Integer>();
	
	private DrawerLayout drawerLayout;
	private ListView featuresList;
	private String[] features;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_ad);
		
		features = getResources().getStringArray(R.array.features);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		featuresList = (ListView) findViewById(R.id.left_drawer);
		featuresList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, features));
		featuresList.setOnItemClickListener(this);
		
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
		
		public int id;
		
		public int getId(){
			return id;
		}
		
		public void setId(int id){
			this.id = id;
		}
		
	}
	
	public class NewAdSpinnerTask extends AsyncTask<Void, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Void... params) {
			CategoryModel jParser = new CategoryModel();
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
					
					spinnerCategory.setId(category.optInt("id"));
					spinnerCategoryIds.add(category.optInt("id"));
					
					spinnerCategories.add(spinnerCategory);
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
				String categoryName = (String) adCategory.getItemAtPosition(adCategory.getSelectedItemPosition());
				int position = -1;
				for (String s : spinnerCategoryNames) {
					position++;
					if (s.equals(categoryName)) {
						break;
					}
				}
				String value1 = spinnerCategoryIds.get(position).toString();
				
				TextView adTitle = (TextView)findViewById(R.id.new_ad_title);
				String value2 = adTitle.getText().toString();
				
				TextView adPrice = (TextView)findViewById(R.id.new_ad_price);
				String value3 = adPrice.getText().toString();
				
				TextView adDescription = (TextView)findViewById(R.id.new_ad_description);
				String value4 = adDescription.getText().toString();
				
				Intent getUserData = getIntent();
				String value5 = getUserData.getStringExtra("userEmail");
//				Log.i("hsuahusa", value5);
				
				Intent intent = new Intent(context, AddPhotoActivity.class);
				Bundle extras = new Bundle();
				
				extras.putString("categoryId", value1);
				extras.putString("title", value2);
				extras.putString("price", value3);
				extras.putString("description", value4);
				extras.putString("userEmail", value5);
				intent.putExtras(extras);
				
				startActivity(intent);
			}
		});
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		
		if (position == 0) {
			final Context context = this;

			Intent intent = new Intent(context, CategoryListActivity.class);
			startActivity(intent);
		} else if (position == 1){
			
			final Context context = this;

			Intent getUserData = getIntent();
			String userEmail = getUserData.getStringExtra("user_email");
					
			Intent intent = new Intent(context, NewAdActivity.class);
			intent.putExtra("userEmail", userEmail);
			startActivity(intent);

		} else if (position == 2){
			
			final Context context = this;

			Intent getUserData = getIntent();
			String userEmail = getUserData.getStringExtra("user_email");
					
			Intent intent = new Intent(context, AdListActivity.class);
			intent.putExtra("userEmail", userEmail);
			startActivity(intent);
			
		} else {
			Session session = Session.getActiveSession();
			session.closeAndClearTokenInformation();
			
			Intent intent = new Intent(context, MainActivity.class);
			startActivity(intent);
		}
	}
}
