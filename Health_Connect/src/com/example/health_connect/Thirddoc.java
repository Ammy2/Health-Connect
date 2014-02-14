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

public class Thirddoc extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thirddoc);
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
					
					EditText lno = (EditText) findViewById(R.id.email);
					EditText address = (EditText) findViewById(R.id.spec);
					EditText year = (EditText) findViewById(R.id.startdate);
					EditText school = (EditText) findViewById(R.id.lic);
					EditText spec = (EditText) findViewById(R.id.school);
					EditText deg = (EditText) findViewById(R.id.degree);
					
					currentUser.put("LicenseNo",lno.getText().toString());
					currentUser.put("Address",address.getText().toString());
					currentUser.put("Start_Practice",year.getText().toString());
					currentUser.put("Medical_School",school.getText().toString());
					currentUser.put("Primary_speciality",spec.getText().toString());
					currentUser.put("Degree",deg.getText().toString());
					currentUser.saveInBackground();
				      Intent intent = new Intent(Thirddoc.this, Fourth.class);
					  startActivity(intent);
					
				}
			});
			
			
			
		} else {
			Intent intent = new Intent(Thirddoc.this, MainActivity.class);
			startActivity(intent);
		}
	}

}
