package com.adapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.facebook.*;
import com.facebook.model.*;

public class MainActivity extends FragmentActivity  {
	
	private static final int SPLASH = 0;
	private static final int SELECTION = 1;
	private static final int FRAGMENT_COUNT = SELECTION +1;
	
	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	private boolean isResumed = false;
	private UiLifecycleHelper uiHelper;
	
	Button login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
//		onClickLogin();
		
		FragmentManager fm = getSupportFragmentManager();
		fragments[SPLASH] = fm.findFragmentById(R.id.splashFragment);
		fragments[SELECTION] = fm.findFragmentById(R.id.selectionFragment);
		
		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			transaction.hide(fragments[i]);
		}
		transaction.commit();
		
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
	}
	
	private void showFragment(int fragmentIndex, boolean addToBackStack) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			if (i == fragmentIndex) {
				transaction.show(fragments[i]);
			} else {
				transaction.hide(fragments[i]);
			}
		}
		if (addToBackStack) {
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}
		
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		isResumed = true;
	}
	
	@Override
	public void onPause(){
		super.onPause();
		uiHelper.onPause();
		isResumed = false;
	}
	
	private void onSessionStateChange (Session session, SessionState state, Exception exceptioin) {
		if (isResumed) {
			FragmentManager manager = getSupportFragmentManager();
			int backStackSize = manager.getBackStackEntryCount();
			for (int i = 0; i < backStackSize; i++) {
				manager.popBackStack();
			}
			if (state.isOpened()) {
				showFragment(SELECTION, false);
			} else if (state.isClosed()) {
				showFragment(SPLASH, false);
			}
		}
	}
	
	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		Session session = Session.getActiveSession();
		
		if (session != null && session.isOpened()) {
			showFragment(SELECTION, false);
		} else {
			showFragment(SPLASH, false);
		}
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
//	    Session.openActiveSession(this, true, new Session.StatusCallback() {
//
//	      @Override
//	      public void call(Session session, SessionState state, Exception exception) {
//	        if (session.isOpened()) {
//
//	          Request.newMeRequest(session, new Request.GraphUserCallback() {
//
//	            @Override
//	            public void onCompleted(GraphUser user, Response response) {
//	              if (user != null) {
//	                //TextView welcome = (TextView) findViewById(R.id.welcome);
//	                //welcome.setText("Hello " + user.getName() + "!");
//	              }
//	            }
//	          }).executeAsync();
//	        }
//	      }
//	    });
//	}

//	@Override
//	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
//	      super.onActivityResult(requestCode, resultCode, data);
//	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
//	  }
	
//	public void onClickLogin() {
//		
//		final Context context = this;
//		login = (Button) findViewById(R.id.user_login_button);
//		login.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, CategoryListActivity.class);
//				startActivity(intent);
//			}
//		});
//	}
}