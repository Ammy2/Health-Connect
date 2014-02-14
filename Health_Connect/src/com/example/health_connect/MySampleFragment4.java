package com.example.health_connect;



import com.parse.Parse;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MySampleFragment4 extends Fragment {
	
	
	
	
	private static View mView;

    public static final MySampleFragment4 newInstance(String sampleText) {
        MySampleFragment4 f = new MySampleFragment4();

        Bundle b = new Bundle();
        f.setArguments(b);

    return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.sample_fragment4, container, false);
        Parse.initialize(mView.getContext(), "e0FVFRBMAWJi5shg4XF8zL3SIuRwDIufww3338so", "toTJmlHTEF43u7PoAFT4fedwqfhoWiSajj1Se7FT");
        final ParseUser currentUser = ParseUser.getCurrentUser();
//		EditText e_name = (EditText) findViewById(R.id.editText1);
//		EditText e_eid = (EditText) findViewById(R.id.editText2);
		final EditText e_add = (EditText) mView.findViewById(R.id.startdate);
		final EditText e_dob = (EditText) mView.findViewById(R.id.lic);
		final EditText e_lno = (EditText) mView.findViewById(R.id.school);
		final EditText e_mschool = (EditText) mView.findViewById(R.id.degree);
		final EditText e_spec = (EditText) mView.findViewById(R.id.editText7);
		final EditText e_deg = (EditText) mView.findViewById(R.id.editText8);
		final EditText e_year = (EditText) mView.findViewById(R.id.editText9);
		
	//	e_name.setText(currentUser.getUsername());
		e_add.setText(currentUser.getString("Address"));
		e_dob.setText(currentUser.getString("Dob"));
		e_lno.setText(currentUser.getString("LicenseNo"));
		e_mschool.setText(currentUser.getString("Medical_School"));
		e_spec.setText(currentUser.getString("Primary_speciality"));
		e_deg.setText(currentUser.getString("Degree"));
	//	e_eid.setText(currentUser.getEmail());
		e_year.setText(currentUser.getString("Start_Practice"));
		TextView a_na = (TextView) mView.findViewById(R.id.na);
		TextView a_ei = (TextView) mView.findViewById(R.id.ei);
		
		
		a_na.setText(currentUser.getUsername());
		a_ei.setText(currentUser.getEmail());
		
		Button sz = (Button) mView.findViewById(R.id.save);
		sz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentUser.put("Address", e_add.getText().toString());
				currentUser.put("Dob", e_dob.getText().toString());
				currentUser.put("LicenseNo", e_lno.getText().toString());
				currentUser.put("Medical_School", e_mschool.getText().toString());
				currentUser.put("Primary_speciality", e_spec.getText().toString());
				currentUser.put("Degree", e_deg.getText().toString());
				currentUser.put("Start_Practice", e_year.getText().toString());
				currentUser.saveInBackground();
				MessageBox("Changes Saved");
				Intent i2= new Intent(mView.getContext(),SwipeHome.class);
				startActivity(i2);
			}
		});
		

        return mView;
    }

    public void MessageBox(String message)
    {
       Toast.makeText(mView.getContext(), message, Toast.LENGTH_SHORT).show();
    }  
	

}

