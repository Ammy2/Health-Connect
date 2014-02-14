package com.example.health_connect;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Chat extends Activity {
	
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

	ParseUser currentUser;
	
	ListView listView ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "e0FVFRBMAWJi5shg4XF8zL3SIuRwDIufww3338so", "toTJmlHTEF43u7PoAFT4fedwqfhoWiSajj1Se7FT");
		// ParseAnalytics.trackAppOpened(getIntent());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		currentUser = ParseUser.getCurrentUser();
		final String s_user_name;
		final String s_user_id;
		s_user_id = getIntent().getStringExtra("Id");
		s_user_name = getIntent().getStringExtra("name");
		
		TextView second_user = (TextView) findViewById(R.id.textView1);
		second_user.setText(s_user_name);
		if( currentUser.getString("Type").equalsIgnoreCase("Doctor"))
		{
			second_user.setBackgroundColor(Color.rgb(247,239,239));
        	second_user.setTextColor(Color.rgb(117,179,255));
        //	view.setBackgroundColor(Color.rgb(117,179,255));
		}
		
		
		
		int size = 0;
	     
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        
        // Defined Array values to show in ListView
        String[] values = new String[] { "Android List View", 
                                         "Adapter implementation",
                                         "Simple List View In Android",
                                         "Create List View Android", 
                                         "Android Example", 
                                         "List View Source Code", 
                                         "List View Array Adapter", 
                                         "Android Example List View" 
                                        };
        
        
        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        
        final ArrayList<String> report_list = new ArrayList<String>();
        ParseQuery<ParseObject> fperson_query = ParseQuery.getQuery("CHAT");
        fperson_query.whereEqualTo("from_id", currentUser.getObjectId());
        fperson_query.whereEqualTo("to_id", s_user_id);
        ParseQuery<ParseObject> sperson_query = ParseQuery.getQuery("CHAT");
        sperson_query.whereEqualTo("to_id", currentUser.getObjectId());
        sperson_query.whereEqualTo("from_id", s_user_id);
        
        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(fperson_query);
        queries.add(sperson_query);
         
        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.orderByAscending("createdAt");
        startLoading();
        
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
        
      
       // query.findInBackground(new FindCallback<ParseObject>() {
        	
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for (int i=0;i< scoreList.size();i++) {
		        	 
   		        	String msg =scoreList.get(i).getString("message");
   		        	Date time =scoreList.get(i).getCreatedAt();
   		        	 //String time =scoreList.get(i).getString("createdAt");
   		        	Log.d("TEST",msg);
   		        	report_list.add(msg);
   		        	// report_list.add(time.toString());
   		        	
   		        	
   		        	
   		        	Map<String, String> datum = new HashMap<String, String>(3);
   		         datum.put("title", msg);
   		         datum.put("date", time.toString());
   		         datum.put("from",scoreList.get(i).getString("from_id") );
   		         data.add(datum);
   		        	
   		        	
   		        	
   		        	  }
                    stopLoading();
                    
                    
                } else {
                	stopLoading();
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                           android.R.id.text2}){
        					public View getView(int position, View convertView, android.view.ViewGroup parent) 
        					{
        						View view = super.getView(position, convertView, parent);
        				        TextView text = (TextView) view.findViewById(android.R.id.text1);
        				        text.setTextColor(Color.WHITE);
        				        TextView sub_text = (TextView) view.findViewById(android.R.id.text2);
        				        sub_text.setTextColor(Color.rgb(85,102,109));
        				        if(text.getText().toString().substring(0,1).equalsIgnoreCase("-"))
        				        {
        				        	text.setTextColor(Color.rgb(247,239,239));
        				        	view.setBackgroundColor(Color.rgb(117,179,255));
        				        	text.setPadding(5, 5, 5, 5);
        				        	text.setGravity(Gravity.LEFT);
        				        	
        				        	
        				        }
        				        else
        				        {
        				        	text.setTextColor(Color.rgb(247,140,123));
        				        	view.setBackgroundColor(Color.rgb(241,244,239));
        				        	text.setPadding(5, 5, 5, 5);
        				        	text.setGravity(Gravity.RIGHT);
        				        }
        				        sub_text.setGravity(Gravity.RIGHT);
        				        sub_text.setPadding(5, 0, 5,5);
        				        String str = text.getText().toString().substring(2);
        				        text.setText(str);
        				        return view;
        						
        					}
                           
        
        };
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
         // android.R.layout.simple_list_item_1, android.R.id.text1, report_list);
       
        
        // Assign adapter to ListView
        listView.setAdapter(adapter);
       
        
        
		
		
		Button push = (Button) findViewById(R.id.button1);
		push.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText ed = (EditText) findViewById(R.id.editText1);
				if( TextUtils.isEmpty(ed.getText().toString()) )
				{
				//	TextView msg = (TextView) findViewById(R.id.textView2);
					//msg.setText("Invalid Email-Id");				
				}
				else
				{
					
					startLoading();
					char ch='-'; // taking patient now
					if(currentUser.getString("Type").equalsIgnoreCase("Doctor"))
					{
						ch='+';
					}
					
					/* IF he is patient 
					 * then ch = '-' else ch='+'
					 */
					
					ParseObject new_mssg = new ParseObject("CHAT");
					new_mssg.put("to_id",s_user_id);
					new_mssg.put("from_id", currentUser.getObjectId());
					new_mssg.put("message", ch + " " + ed.getText().toString());
				
					new_mssg.saveInBackground();
					stopLoading();
					ed.setText("");
					
					Intent intent = new Intent(Chat.this,Chat.class);
					intent.putExtra("Id", s_user_id);
					intent.putExtra("name", s_user_name);
				    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    startActivity(intent);
					
				}
				
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
