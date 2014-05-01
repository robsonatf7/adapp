package com.adapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class ProductViewActivity extends Activity{

	Button buy, pro, cat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_view);
		onClickBuy();
		onClickPro();
		onClickCat();
	}

	private void onClickCat() {
		final Context context = this;
		cat = (Button) findViewById(R.id.product_view_cat_button);
		cat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CategoryListActivity.class);
				startActivity(intent);
			}
		});
	}

	private void onClickPro() {
		final Context context = this;
		pro = (Button) findViewById(R.id.product_view_pro_button);
		pro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ProductListActivity.class);
				startActivity(intent);
			}
		});
	}

	private void onClickBuy() {
		final Context context = this;
		buy = (Button) findViewById(R.id.product_view_buy_button);
		buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SendMessageActivity.class);
				startActivity(intent);
			}
		});
	}
}