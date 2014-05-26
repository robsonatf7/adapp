package com.adapp;

import com.adapp.adapters.AdListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	static final String[] Ads = new String[] {
		"iPhone", "Samsung", "Motorola"
	};
	Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_list);
		context = this;
		
		AdListAdapter productListAdapter = new AdListAdapter(this, Ads);
		ListView listView = (ListView) findViewById(R.id.ad_list);
		listView.setAdapter(productListAdapter);
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
}