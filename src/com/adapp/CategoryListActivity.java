package com.adapp;

import com.adapp.adapters.CategoryListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;


public class CategoryListActivity extends Activity {

		Button newAd;
		static final String[] Categories = new String[] {
			"Casa", "Carro", "Celular"
		};
			
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			setContentView(R.layout.category_list);
			
			CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this, Categories);
			ListView listView = (ListView) findViewById(R.id.listView1);
			listView.setAdapter(categoryListAdapter);
			
			onClickNewAd();
		}
		


		public void onClickNewAd() {
			
			final Context context = this;
			newAd = (Button) findViewById(R.id.button1);
			newAd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(context, NewAdActivity.class);
					startActivity(intent);
				}
			});
		}
		
}