package com.example.health_connect;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
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
import android.view.View.OnKeyListener;
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

public class MySampleFragment5 extends Fragment implements TextWatcher,
OnItemClickListener {
	
	private static View mView;

	private static final int LIST_PIC_SCREEN = 0;
	private static final int VIEW_PIC_SCREEN = 1;

	private ArrayList<String>listPicId = new ArrayList<String>();
	private ArrayList<String>listPicName = new ArrayList<String>();
	private ArrayList<String> listPicEmail = new ArrayList<String>();
	private ArrayList<String> listPicAddress = new ArrayList<String>();
	private ArrayList<String> listPicLic = new ArrayList<String>();
	private ArrayList<String> listPicDegree = new ArrayList<String>();
	private ArrayList<String> listPicSchool = new ArrayList<String>();
	private ArrayList<String> listPicStart = new ArrayList<String>();
	private ArrayList<String> listPicSpec = new ArrayList<String>();
	private List<Integer> listPicDrawable = new ArrayList<Integer>();
	
	private ArrayList<Picture> listPic = new ArrayList<Picture>();
	private ListView listview;
	private PicListAdapter adapter = null;
	private EditText searchEdt;
	private ViewFlipper fliper;
	
	private ImageView viewpic;
	private TextView viewname;
	private TextView viewemail;
	private TextView viewadd;
	private TextView viewlic;
	private TextView viewdegree;
	private TextView viewschool;
	private TextView viewspec;
	private TextView viewstart;
	
	ProgressDialog mProgressDialog;
	List<ParseUser> objects;
	List<ParseUser> obj;
	List<ParseObject> doc;
	private boolean flag=true;
	
	
    public static final MySampleFragment5 newInstance(String sampleText) {
        MySampleFragment5 f = new MySampleFragment5();

        Bundle b = new Bundle();
        f.setArguments(b);
    return f;
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {

        mView = inflater.inflate(R.layout.sample_fragment5, container, false);
        Parse.initialize(mView.getContext(), "e0FVFRBMAWJi5shg4XF8zL3SIuRwDIufww3338so", "toTJmlHTEF43u7PoAFT4fedwqfhoWiSajj1Se7FT");
        final ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser!=null)
		{
			listPicName.clear();
			listPicEmail.clear();
			
			listPicAddress.clear();
			listPicLic.clear();
			listPicDegree.clear();
			listPicSchool.clear();
			listPicStart.clear();
			listPicSpec.clear();
			listPicDrawable.clear();
			listPicId.clear();
			
			
			try
			{
		
				ParseQuery<ParseUser> query= ParseUser.getQuery();
				query.whereEqualTo("Type", "Doctor");
				objects = query.find();
		    		
			    Log.d("Check", "here it is");
			        
			           	for(ParseUser r : objects)
			         	{
			           		listPicId.add((String)r.getObjectId());
			            	listPicName.add((String)r.get("username"));
			           		listPicEmail.add((String)r.get("email"));
			           		listPicAddress.add((String)r.get("Address"));
			           		listPicLic.add((String)r.get("LicenseNo"));
			           		listPicDegree.add((String)r.get("Degree"));
			           		listPicSchool.add((String)r.get("Medical_School"));
			           		listPicSpec.add((String)r.get("Primary_speciality"));
			           		listPicStart.add((String)r.get("Start_Practice"));
			           		listPicDrawable.add(R.drawable.process2);
			           		Log.d("Check", "in for parse user");
			           	}
			}
			catch(ParseException e)
			{
				Log.d("Error", "Oh nooo..");
			}           	
		}
		
		Log.d("Error","lists added");
		
		fliper = (ViewFlipper)mView.findViewById(R.id.viewFlipper1);
		listview = (ListView)mView.findViewById(R.id.listView1);
		listPic.clear();
		for (int i = 0; i < listPicName.size(); i++) {
			Log.d("Error","for loop");
			Picture pic = new Picture(listPicName.get(i), listPicEmail.get(i),listPicDrawable.get(i),listPicAddress.get(i),listPicLic.get(i),listPicDegree.get(i)
					,listPicSchool.get(i),listPicSpec.get(i),listPicStart.get(i),listPicId.get(i));
			Log.d("Error","pic added");
			listPic.add(pic);
		}
		Log.d("Error","for exited");
		if(adapter==null)
			adapter = new PicListAdapter(getActivity().getBaseContext(), listPic);
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
	    		viewlic.setText(listPic.get(position).getPicLic());
	    		viewdegree.setText(listPic.get(position).getPicDegree());
	    		viewschool.setText(listPic.get(position).getPicSchool());
	    		viewspec.setText(listPic.get(position).getPicSpec());
	    		viewstart.setText(listPic.get(position).getPicStart());
	    		
	    		
	    		
	    		Button cbtn = (Button) mView.findViewById(R.id.cht);
	    		cbtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mView.getContext(), Chat.class);
						intent.putExtra("Id", listPic.get(position).getPicId());
						intent.putExtra("name", listPic.get(position).getPicName());
						startActivity(intent);
						
					}
				});
	    		
	    		
	   		Button btn=(Button)mView.findViewById(R.id.button1);
	   		Log.d("Error","Button click");
	    		btn.setOnClickListener(new OnClickListener(){	   
					@Override
					public void onClick(View arg0) {
//						ParseObject add_doctor=new ParseObject("AddDoctor");
//				   		Log.d("Error","Built object");
//						add_doctor.put("patient_id",currentUser.getObjectId());
//				   		Log.d("Error","Added patient ID");
						String x=listPic.get(position).getPicType();
				   		Log.d("Error","Got Email");
						ParseQuery<ParseUser> query1 = ParseUser.getQuery();
						query1.whereEqualTo("Type", "Doctor");
						query1.whereEqualTo("email",x);
				   		Log.d("Error","Query done");
						try {
							obj = query1.find();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				   		Log.d("Error","Query found");

						
				   		
				   		
				   		
				   		

				   		ParseQuery<ParseObject> query4 = new ParseQuery<ParseObject>("AddDoctor");
						 query4.whereEqualTo("patient_id",currentUser.getObjectId());

						 try{
							doc=query4.find();
						 }
						 catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 for(ParseObject t : doc)
						 {
							 for(ParseUser r : obj)
							 {

								 if(((String) t.get("doctor_id")).equalsIgnoreCase(r.getObjectId()))
									 flag=false;
								 Log.d("Checkurvashi",flag+" ");
							 }
						 }
								if(flag==true)
								{
									ParseObject add_doctor=new ParseObject("AddDoctor");
						   		Log.d("Error","Built object");
								add_doctor.put("patient_id",currentUser.getObjectId());
						   		Log.d("Error","Added patient ID");
									for(ParseUser r : obj)
									 {
										String docid=r.getObjectId();
										add_doctor.put("doctor_id",docid);
									 }


				   		
				   		
				   		
				   		
				   		
				   		
				   		
//						for(ParseUser r : obj)
//			         	{
//							String docid=r.getObjectId();
//						    add_doctor.put("doctor_id",docid);
//			         	}
				   		Log.d("Error","Doc added");
						add_doctor.saveInBackground();
//					//	TextView txt=(TextView)mView.findViewById(R.id.textView8);
//					//	txt.setText("Doctor successfully added");
						MessageBox("Doctor successfully added");
					}
								else{
									MessageBox("Doctor already added");
								}
					}
	    		});
	    		Log.d("Error","in adapter hereee!!!!!!!");
	   
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
		viewspec = (TextView)mView.findViewById(R.id.pbg);
		viewdegree = (TextView)mView.findViewById(R.id.pnation);
		viewschool = (TextView)mView.findViewById(R.id.pwt);
		viewlic = (TextView)mView.findViewById(R.id.pht);
		viewemail = (TextView)mView.findViewById(R.id.pemail);
		viewstart = (TextView)mView.findViewById(R.id.pdob);
		
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
		viewlic.setText(listPic.get(position).getPicLic());
		viewdegree.setText(listPic.get(position).getPicDegree());
		viewschool.setText(listPic.get(position).getPicSchool());
		viewspec.setText(listPic.get(position).getPicSpec());
		viewstart.setText(listPic.get(position).getPicStart());
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


