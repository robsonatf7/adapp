package com.adapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class SendMessageActivity extends Activity{
	
	Button send;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message);
		onClickSend();
	}

	private void onClickSend() {
		final Context context = this;
		send = (Button) findViewById(R.id.send_message_button);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MessageSentActivity.class);
				startActivity(intent);
			}
		});
	}

}
