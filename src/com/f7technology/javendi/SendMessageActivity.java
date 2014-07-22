package com.f7technology.javendi;

import com.f7technology.javendi.R;
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

public class SendMessageActivity extends DrawerCode {
	
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
}
