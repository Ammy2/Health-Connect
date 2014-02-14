package com.example.health_connect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class Third extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third);
		Parse.initialize(this, "e0FVFRBMAWJi5shg4XF8zL3SIuRwDIufww3338so", "toTJmlHTEF43u7PoAFT4fedwqfhoWiSajj1Se7FT");
		ParseAnalytics.trackAppOpened(getIntent());
		
		
		final ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
		  // do stuff with the user

			Button next = (Button) findViewById(R.id.button1);
			next.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TextView tv = (TextView) findViewById(R.id.pname);
					tv.setText("Done till here");
					
					// TODO Auto-generated method stub
					
					EditText fname = (EditText) findViewById(R.id.email);
					EditText nation = (EditText) findViewById(R.id.spec);
					EditText w = (EditText) findViewById(R.id.startdate);
					EditText h = (EditText) findViewById(R.id.lic);
					EditText bg = (EditText) findViewById(R.id.school);
					EditText da = (EditText) findViewById(R.id.degree);
					
					currentUser.put("Fname",fname.getText().toString());
					currentUser.put("Nation",nation.getText().toString());
					currentUser.put("Weight",w.getText().toString());
					currentUser.put("Height",h.getText().toString());
					currentUser.put("BG",bg.getText().toString());
					currentUser.put("DA",da.getText().toString());
					currentUser.saveInBackground();
					
				      Intent intent = new Intent(Third.this, Fourth.class);
					  startActivity(intent);
					
				}
			});
			
			
			
		} else {
			Intent intent = new Intent(Third.this, MainActivity.class);
			startActivity(intent);
		  // show the signup or login screen
		}
		
		
		
		
		
		
		
		
	}

}
