package com.example.health_connect;


import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Seconddoc extends Activity {
	
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
	
	 public void MessageBox(String message)
	    {
	       Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	    }  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		ParseAnalytics.trackAppOpened(getIntent());
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				EditText name = (EditText) findViewById(R.id.email);
				EditText eid = (EditText) findViewById(R.id.spec);
				EditText bd = (EditText) findViewById(R.id.startdate);
				EditText pass = (EditText) findViewById(R.id.lic);
				if( TextUtils.isEmpty(name.getText().toString()) ||  TextUtils.isEmpty(eid.getText().toString())  || TextUtils.isEmpty(bd.getText().toString()) || TextUtils.isEmpty(pass.getText().toString()))
				{
					
					Log.d("Check", "Invalid");
					TextView tv = (TextView) findViewById(R.id.pname);
					MessageBox("Invalid Input");
				//	tv.setText(name.getText().toString());
				}
				else
				{
					Log.d("Check", "Reached in else part");
					ParseUser user = new ParseUser();
					user.setUsername(name.getText().toString());
					user.setPassword(pass.getText().toString());
					user.setEmail(eid.getText().toString());
					 
					// other fields can be set just like with ParseObject
					user.put("Dob", bd.getText().toString());
					user.put("Type", "Doctor");
					startLoading(); 
					user.signUpInBackground(new SignUpCallback() {
					  public void done(ParseException e) {
					    if (e == null) {
					      Log.d("Check","Hooray! Let the doctor use the app now.");
					      stopLoading();
			              finish();
					      Intent intent = new Intent(Seconddoc.this, Thirddoc.class);
						  startActivity(intent);
					      
					      
					    } else {
					    	stopLoading();
					    	 Log.d("Check","NO");
					    	 Log.d("Check", "NOOO", e);
					    	MessageBox(e.getMessage());
					      // Sign up didn't succeed. Look at the ParseException
					      // to figure out what went wrong
					    }
					  }
					});
					
					
				}                 // end of else
					
			
				
			}
		});

	}

}
