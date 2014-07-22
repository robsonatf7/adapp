package com.f7technology.javendi;

import com.facebook.Session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DrawerCode extends Activity implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		
		TextView text = (TextView) arg1.findViewById(android.R.id.text1);
		String string = text.getText().toString();
		System.out.println(string);
		
		if (string.equals("Categories")) {
			
			final Context context = this;
			Intent intent = new Intent(context, CategoryListActivity.class);
			startActivity(intent);
			
		} else if (string.equals("Log in")){
			
			final Context context = this;
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
		
		} else if (string.equals("New ad")){
			
			final Context context = this;
			
			Intent getUserData = getIntent();
			String userEmail = getUserData.getStringExtra("user_email");
			
			Intent intent = new Intent(context, NewAdActivity.class);
			intent.putExtra("userEmail", userEmail);
			startActivity(intent);
			
		} else if (string.equals("My ads")){
			
			final Context context = this;

			Intent getUserData = getIntent();
			String userEmail = getUserData.getStringExtra("user_email");
					
			Intent intent = new Intent(context, AdListActivity.class);
			intent.putExtra("userEmail", userEmail);
			startActivity(intent);
			
		} else if (string.equals("Log out")) {
			Session session = Session.getActiveSession();
			session.closeAndClearTokenInformation();
			
			Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
			startActivity(intent);
		}
	}

}
