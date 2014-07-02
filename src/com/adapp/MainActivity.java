package com.adapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.*;
import com.facebook.model.*;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends FragmentActivity {

	private MainFragment mainFragment;
	
	Button categories;
	Button newAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	    if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	    
	 // start Facebook Login
	    Session.openActiveSession(this, true, new Session.StatusCallback() {

	      // callback when session changes state
	      @Override
	      public void call(Session session, SessionState state, Exception exception) {
	    	  if (session.isOpened()) {
	    		  // make request to the /me API
	    		  Request.newMeRequest(session, new Request.GraphUserCallback() {

	    		  // callback after Graph API response with user object
	    		  @Override
	    		  public void onCompleted(GraphUser user, Response response) {
	    		    	if (user != null) {
	    		    		Intent inetnt = new Intent(getApplicationContext(), CategoryListActivity.class);
	    		    		inetnt.putExtra("user_name", user.getName());
	    		    		inetnt.putExtra("user_email", user.asMap().get("email").toString());
	    		    		startActivity(inetnt);
	    		    	}
	    		    }
	    		  }).executeAsync();
	    	  }
	      }
	    });
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
//		onClickCategories();
//		onClickNewAd();
//	}
	
//	public void onClickCategories() {
//		
//		final Context context = this;
//		categories = (Button) findViewById(R.id.main_categories_button);
//		categories.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, CategoryListActivity.class);
//				startActivity(intent);
//			}
//		});
//	}
//	
//	public void onClickNewAd() {
//		
//		final Context context = this;
//		newAd = (Button) findViewById(R.id.main_new_button);
//		newAd.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, NewAdActivity.class);
//				startActivity(intent);
//			}
//		});
//	}
}