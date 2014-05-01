package com.adapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CategoryListActivity extends Activity {

		Button newAd;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.category_list);
			onClickNewAd();
		}

		public void onClickNewAd() {
			
			final Context context = this;
			newAd = (Button) findViewById(R.id.category_list_button);
			newAd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(context, NewAdActivity.class);
					startActivity(intent);
				}
			});
		}
		
		public class ListViewItem {
			
		}
}
