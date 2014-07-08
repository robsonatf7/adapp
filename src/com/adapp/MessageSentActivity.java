package com.adapp;

import com.facebook.Session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MessageSentActivity extends Activity implements OnItemClickListener {
	
	Button cat, pro;
	
	private DrawerLayout drawerLayout;
	private ListView featuresList;
	private String[] features;
	
	protected void onCreate(Bundle savedInstaceState) {
		super.onCreate(savedInstaceState);
		setContentView(R.layout.message_sent);
		
		features = getResources().getStringArray(R.array.features);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		featuresList = (ListView) findViewById(R.id.left_drawer);
		featuresList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, features));
		featuresList.setOnItemClickListener(this);
		
		onClickCat();
		onClickPro();
	}

	public void onClickPro() {
		final Context context = this;
		pro = (Button) findViewById(R.id.message_sent_pro_button);
		pro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdListActivity.class);
				startActivity(intent);
			}
		});
	}

	private void onClickCat() {
		final Context context = this;
		cat = (Button) findViewById(R.id.message_sent_cat_button);
		cat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CategoryListActivity.class);
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
			final Context context = this;
			
			Session session = Session.getActiveSession();
			session.closeAndClearTokenInformation();
			
			Intent intent = new Intent(context, MainActivity.class);
			startActivity(intent);
		}
	}
}
