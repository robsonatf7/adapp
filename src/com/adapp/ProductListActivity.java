package com.adapp;

import com.adapp.adapters.ProductListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ProductListActivity extends Activity {
	
	Button newAdOne;
	static final String[] Products = new String[] {
		"iPhone", "Samsung", "Motorola"
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.product_list);
		
		ProductListAdapter productListAdapter = new ProductListAdapter(this, Products);
		ListView listView = (ListView) findViewById(R.id.productList);
		listView.setAdapter(productListAdapter);
		
		onClickNewAd();
	}

	public void onClickNewAd() {
		
		final Context context = this;
		newAdOne = (Button) findViewById(R.id.productListButton);
		newAdOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, NewAdActivity.class);
				startActivity(intent);
			}
		});
	}

}