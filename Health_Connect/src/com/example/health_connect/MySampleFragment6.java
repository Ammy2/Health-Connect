package com.example.health_connect;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;


public class MySampleFragment6 extends Fragment implements TextWatcher,
OnItemClickListener {
	
	private static View mView;

	private static final int LIST_PIC_SCREEN = 0;
	private static final int VIEW_PIC_SCREEN = 1;

	private ArrayList<String>listPicName = new ArrayList<String>();
	private ArrayList<String> listPicEmail = new ArrayList<String>();
	private ArrayList<String> listPicAddress = new ArrayList<String>();
	private ArrayList<String> listPicNation = new ArrayList<String>();
	private ArrayList<String> listPicDob = new ArrayList<String>();
	private ArrayList<String> listPicBg = new ArrayList<String>();
	private ArrayList<String> listPicWt = new ArrayList<String>();
	private ArrayList<String> listPicHt = new ArrayList<String>();
	private ArrayList<String> listPicDA = new ArrayList<String>();
	private ArrayList<String> listPicId = new ArrayList<String>();
	private List<Integer> listPicDrawable = new ArrayList<Integer>();
	
	private ArrayList<Patient> listPic = new ArrayList<Patient>();
	private ListView listview;
	private PatientPicAdapter adapter;
	private EditText searchEdt;
	private ViewFlipper fliper;
	
	private ImageView viewpic;
	private TextView viewname;
	private TextView viewemail;
	private TextView viewadd;
	private TextView viewnation;
	private TextView viewdob;
	private TextView viewwt;
	private TextView viewht;
	private TextView viewbg;
	private TextView viewda;
	
	ProgressDialog mProgressDialog;
	List<ParseObject> objects;
	List<ParseUser> patients;
	
	
    public static final MySampleFragment6 newInstance(String sampleText) {
        MySampleFragment6 f = new MySampleFragment6();

        Bundle b = new Bundle();
        f.setArguments(b);
    return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {

        mView = inflater.inflate(R.layout.sample_fragment6, container, false);
        Parse.initialize(mView.getContext(), "e0FVFRBMAWJi5shg4XF8zL3SIuRwDIufww3338so", "toTJmlHTEF43u7PoAFT4fedwqfhoWiSajj1Se7FT");
        
        final ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser!=null)
		{
			try
			{
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("AddDoctor");
			    query.whereEqualTo("doctor_id",currentUser.getObjectId());
		    
				 objects = query.find();
		    		
			         	Log.d("Check", "here it is");
			        
			           	for(ParseObject s : objects)
			         	{
			           		ParseQuery<ParseUser> query1 = ParseUser.getQuery();
							query1.whereEqualTo("objectId",s.get("patient_id"));
			           		
							try {
								patients = query1.find();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							for( ParseUser r : patients)
							{
							listPicId.add((String)r.getObjectId());
			            	listPicName.add((String)r.get("username"));
			           		listPicEmail.add((String)r.get("email"));
			           		listPicAddress.add((String)r.get("Address"));
			           		listPicNation.add((String)r.get("Nation"));
			           		listPicDob.add((String)r.get("DOB"));
			           		listPicWt.add((String)r.get("Weight"));
			           		listPicHt.add((String)r.get("Height"));
			           		listPicBg.add((String)r.get("BG"));
			           		listPicDA.add((String)r.get("DA"));
			           		listPicDrawable.add(R.drawable.process1);
			           		Log.d("Check", "in for parse user");
							}
			           	}
			}
			catch(ParseException e)
			{
				Log.d("Error", "Oh nooo..");
			}           	
		}
		
		Log.d("Error","lists added");
		
		fliper = (ViewFlipper)mView.findViewById(R.id.viewFlipper3);
		listview = (ListView)mView.findViewById(R.id.listView3);
		listPic.clear();
		for (int i = 0; i < listPicName.size(); i++) {
			Log.d("Error","for loop");
			Patient pic = new Patient(listPicName.get(i), listPicEmail.get(i),
					listPicDrawable.get(i),listPicAddress.get(i),listPicNation.get(i),listPicDob.get(i)
					,listPicWt.get(i),listPicHt.get(i),listPicBg.get(i),listPicDA.get(i),listPicId.get(i));
			Log.d("Error","pic added");
			listPic.add(pic);
		}
		Log.d("Error","for exited");
		adapter = new PatientPicAdapter(getActivity().getBaseContext(), listPic);
		Log.d("Error","returned from adapter");
		listview.setAdapter(adapter);
		Log.d("Error","in adapter");
		listview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, final int position,  long id) {
	        	Sliding.slideFromRightToLeft(VIEW_PIC_SCREEN, fliper);
	    		viewpic.setImageResource(listPic.get(position).getPicSource());
	    		viewname.setText(listPic.get(position).getPicName());
	    		viewadd.setText(listPic.get(position).getPicAdd());
	    		viewemail.setText(listPic.get(position).getPicType());
	    		viewnation.setText(listPic.get(position).getPicNation());
	    		viewwt.setText(listPic.get(position).getPicWt());
	    		viewht.setText(listPic.get(position).getPicHt());
	    		viewbg.setText(listPic.get(position).getPicBg());
	    		viewdob.setText(listPic.get(position).getPicDob());
	    		viewda.setText(listPic.get(position).getPicDA());
	   		
	    		Log.d("Error","in adapter hereee!!!!!!!");
	    		
	    		
	    		Button chat = (Button) mView.findViewById(R.id.cht);
	    		chat.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mView.getContext(), Chat.class);
						intent.putExtra("Id", listPic.get(position).getPicId());
						intent.putExtra("name", listPic.get(position).getPicName());
						startActivity(intent);
						
					}
				});
	    		
	    		Button rep = (Button)mView.findViewById(R.id.button1);
	    		
	    		rep.setOnClickListener(new OnClickListener(){	   
					    			
	    			@Override
					public void onClick(View arg0) {
	    				Log.d("Check"," starting intent");
	    				Intent myIntent = new Intent(mView.getContext(), Seventh.class);
	    				myIntent.putExtra("key", listPicId.get(position)); //Optional parameters
	    				Log.d("Check : ", listPicId.get(position) );
	    				mView.getContext().startActivity(myIntent);
	    				Log.d("Check","returned from intent");
	    			}
	    		});
	        }
	    });
				
		Log.d("Error","exited adapter");
		searchEdt = (EditText)mView.findViewById(R.id.serach_edt);
		Log.d("Error","here6");
		searchEdt.addTextChangedListener(new TextWatcher() {
		    public void afterTextChanged(Editable s) {
		    	String text = searchEdt.getText().toString().toLowerCase();
				adapter.filter(text);
		    }
		    public void beforeTextChanged(CharSequence s, int start, int count, int after){
		    }
		    public void onTextChanged(CharSequence s, int start, int before, int count)
		    {
		    }
		});	
		viewpic = (ImageView)mView.findViewById(R.id.pic3);
		viewadd = (TextView)mView.findViewById(R.id.docadd);
		viewname = (TextView)mView.findViewById(R.id.pname);
		viewbg = (TextView)mView.findViewById(R.id.pbg);
		viewnation = (TextView)mView.findViewById(R.id.pnation);
		viewwt = (TextView)mView.findViewById(R.id.pwt);
		viewht = (TextView)mView.findViewById(R.id.pht);
		viewemail = (TextView)mView.findViewById(R.id.pemail);
		viewdob = (TextView)mView.findViewById(R.id.pdob);
		viewda = (TextView)mView.findViewById(R.id.pda);
		return mView;
    }

  
	
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Sliding.slideFromRightToLeft(VIEW_PIC_SCREEN, fliper);
		viewpic.setImageResource(listPic.get(position).getPicSource());
		Log.d("Error","here71");
		viewname.setText(listPic.get(position).getPicName());
		viewadd.setText(listPic.get(position).getPicAdd());
		viewemail.setText(listPic.get(position).getPicType());
		viewnation.setText(listPic.get(position).getPicNation());
		viewwt.setText(listPic.get(position).getPicWt());
		viewht.setText(listPic.get(position).getPicHt());
		viewbg.setText(listPic.get(position).getPicBg());
		viewdob.setText(listPic.get(position).getPicDob());
		viewda.setText(listPic.get(position).getPicDA());
		Log.d("Error","here73");
	}
	
        public boolean onKey(int keyCode, KeyEvent event) {
              if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
                    
            	  int screen = fliper.getDisplayedChild();

      			if (screen == VIEW_PIC_SCREEN) {
      				Sliding.slideFromLeftToRight(LIST_PIC_SCREEN, fliper);
      				return true;
      			}
              }
			return false;
              
        }
        



    public void MessageBox(String message)
    {
       Toast.makeText(mView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}  
	

}