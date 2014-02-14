package com.example.health_connect;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseException;


public class MainActivity extends Activity {
	protected ProgressDialog proDialog;
	
	
	protected void startLoading() {
	    proDialog = new ProgressDialog(this);
	    proDialog.setMessage("loading...");
	    proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    proDialog.setCancelable(false);
	    proDialog.show();
	}

	protected void stopLoading() {
	    proDialog.dismiss();
	    proDialog = null;
	}

	
	public void onBackPressed() {
		// TODO Auto-generated method stub
	//	super.onBackPressed();
		Intent intent = new Intent(MainActivity.this, Welcome.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("Exit me", true);
		startActivity(intent);
		finish();
		
	}
	public void MessageBox(String message)
    {
       Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		if( getIntent().getBooleanExtra("Exit me", false)){
		       finish();
		       return; // add this to prevent from doing unnecessary stuffs
		   }
		
		Parse.initialize(this, "e0FVFRBMAWJi5shg4XF8zL3SIuRwDIufww3338so", "toTJmlHTEF43u7PoAFT4fedwqfhoWiSajj1Se7FT");
		ParseAnalytics.trackAppOpened(getIntent());
		
		final ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null)
		{
			Intent intent2 = new Intent(MainActivity.this,SwipeHome.class);
			startActivity(intent2);
		}
		
		
		Log.d("Check","came back");
		
		
		final EditText eid = (EditText) findViewById(R.id.spec);
		
		// eid is username 
		final EditText pass = (EditText) findViewById(R.id.startdate);
		Button lin = (Button) findViewById(R.id.button1);
		Button lind = (Button) findViewById(R.id.button3);
		
				// log-in
		lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if( TextUtils.isEmpty(eid.getText().toString()) )
				{
					TextView msg = (TextView) findViewById(R.id.doc_degree);
					msg.setText("Invalid Email-Id");
					MessageBox("Invalid Email-Id");
				
				}
				else if( TextUtils.isEmpty(pass.getText().toString()) )
				{
					TextView msg = (TextView) findViewById(R.id.doc_degree);
					msg.setText("Invalid Password");
					MessageBox("Invalid Password");
				}
				else
				{
					startLoading();
					ParseUser.logInInBackground(eid.getText().toString(),pass.getText().toString(), new LogInCallback() {
						  public void done(ParseUser user, ParseException e) {
						    if (user != null && user.getString("Type").equals("Patient")) {
						    	TextView msg = (TextView) findViewById(R.id.doc_degree);
						    	msg.setText("Hooray! The user is logged in.");
						    	//ParseManager.sCurrentUser = user;
				                stopLoading();
				                finish();
						    	Intent intent = new Intent(MainActivity.this, SwipeHome.class);
	                			startActivity(intent);
						    	
						      // Hooray! The user is logged in.
						    } else {
						    	stopLoading();
						    	TextView msg = (TextView) findViewById(R.id.doc_degree);
						    	msg.setText("Please try again");
						    //	Log.d("Check", "NOOO", e);
						    	MessageBox("Incorrect Details");
						      // Signup failed. Look at the ParseException to see what happened.
						    }
						  }
						});
				}
				
			}
		});
		
		
		lind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if( TextUtils.isEmpty(eid.getText().toString()) )
				{
					TextView msg = (TextView) findViewById(R.id.doc_degree);
					msg.setText("Invalid Email-Id");
					MessageBox("Invalid Email-Id");
				}
				else if( TextUtils.isEmpty(pass.getText().toString()) )
				{
					TextView msg = (TextView) findViewById(R.id.doc_degree);
					msg.setText("Invalid Password");	
					MessageBox("Invalid Password");
				}
				else
				{
					startLoading();
					ParseUser.logInInBackground(eid.getText().toString(),pass.getText().toString(), new LogInCallback() {
						  public void done(ParseUser user, ParseException e) {
						    if (user != null && user.getString("Type").equals("Doctor")) {
						    	TextView msg = (TextView) findViewById(R.id.doc_degree);
						    	msg.setText("Hooray! The user is logged in.");
						    	//ParseManager.sCurrentUser = user;
				                stopLoading();
				                finish();
						    	Intent intent = new Intent(MainActivity.this, SwipeHome.class);
	                			startActivity(intent);
						    	
						      // Hooray! The user is logged in.
						    } else {
						    	stopLoading();
						    	TextView msg = (TextView) findViewById(R.id.doc_degree);
						    	msg.setText("Please try again");
						    	MessageBox("Incorrect Details");
						    //	Log.d("Check", "NOOO", e);
						      // Signup failed. Look at the ParseException to see what happened.
						    }
						  }
						});
				}
				
			}
		});
		
		
		// sign-up
		Button sup = (Button) findViewById(R.id.cht);
		Button supd = (Button) findViewById(R.id.button4);
		sup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, Second.class);
				startActivity(intent);
				
			}
		});
		supd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, Seconddoc.class);
				startActivity(intent);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
