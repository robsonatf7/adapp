package com.adapp;

import com.facebook.Session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;

public class SendMessageActivity extends Activity implements OnItemClickListener {
	
	Button buttonSend;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	
	private DrawerLayout drawerLayout;
	private ListView featuresList;
	private String[] features;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message);
		
		features = getResources().getStringArray(R.array.features);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		featuresList = (ListView) findViewById(R.id.left_drawer);
		featuresList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, features));
		featuresList.setOnItemClickListener(this);
		
		buttonSend = (Button) findViewById(R.id.buttonSend);
		textTo = (EditText) findViewById(R.id.editTextTo);
		textSubject = (EditText) findViewById(R.id.editTextSubject);
		textMessage = (EditText) findViewById(R.id.editTextMessage);
		
		buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String to = textTo.getText().toString();
				String subject = textSubject.getText().toString();
				String message = textMessage.getText().toString();
				
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, message);
				
				email.setType("message/rfc822");
				
				startActivity(Intent.createChooser(email, "Choose an Email client :"));
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
