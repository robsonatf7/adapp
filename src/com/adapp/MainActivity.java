package com.adapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends FragmentActivity {

	private MainFragment mainFragment;
	
	private String TAG = "MainActivity";
	private TextView lblEmail;
	
	Button categories;
	Button newAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	    if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	}
//		onClickCategories();
//		onClickNewAd();
//	}
	
//	public void onClickCategories() {
//		
//		final Context context = this;
//		categories = (Button) findViewById(R.id.main_categories_button);
//		categories.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, CategoryListActivity.class);
//				startActivity(intent);
//			}
//		});
//	}
//	
//	public void onClickNewAd() {
//		
//		final Context context = this;
//		newAd = (Button) findViewById(R.id.main_new_button);
//		newAd.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, NewAdActivity.class);
//				startActivity(intent);
//			}
//		});
//	}
}