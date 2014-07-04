package com.adapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MessageSentActivity extends Activity{
	
	Button cat, pro;
	
	private DrawerLayout drawerLayout;
	private ListView listView;
	
	protected void onCreate(Bundle savedInstaceState) {
		super.onCreate(savedInstaceState);
		setContentView(R.layout.message_sent);
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		listView = (ListView) findViewById(R.id.left_drawer);
		
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

}
