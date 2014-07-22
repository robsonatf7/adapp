package com.f7technology.javendi;

import com.f7technology.javendi.R;
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

public class MessageSentActivity extends DrawerCode {
	
	Button cat, pro;
	
	private DrawerLayout drawerLayout;
	private ListView featuresList;
	private String[] features;
	
	protected void onCreate(Bundle savedInstaceState) {
		super.onCreate(savedInstaceState);
		setContentView(R.layout.message_sent);
		
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
}
