package com.example.health_connect;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

public class Search_doc extends Activity implements TextWatcher,
		OnItemClickListener {

	private static final int LIST_PIC_SCREEN = 0;
	private static final int VIEW_PIC_SCREEN = 1;

	private ArrayList<String>listPicName = new ArrayList<String>();
	private ArrayList<String>listPicURL = new ArrayList<String>();
	private ArrayList<String> listPicType = new ArrayList<String>();
	private List<Integer> listPicDrawable = new ArrayList<Integer>();
	private WebView webview;
	private ArrayList<Report> listPic = new ArrayList<Report>();
	private ListView listview;
	private ReportListAdapter adapter;
	private EditText searchEdt;
	private ViewFlipper fliper;
//	private ImageView viewpic;
	ProgressDialog mProgressDialog;
	List<ParseObject> objects;
	//List<ParseUser> objects;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_doc);
		Log.d("Error","in search_doc");
	/*	listPicName.add("icon");
		listPicName.add("pikachu");
		listPicName.add("apple");
		
		listPicType.add("android");
		listPicType.add("cartoon");
		listPicType.add("fruit");
		
		listPicDrawable.add(R.drawable.ic_launcher);
		listPicDrawable.add(R.drawable.pikachu);
		listPicDrawable.add(R.drawable.apple);
		*/
		
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser!=null)
		{
			try
			{
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("FILES");
		    query.whereEqualTo("U_id",currentUser.getObjectId());
			 query.orderByDescending("updatedAt");
		/*		ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.whereEqualTo("Type", "Doctor");
		  */
		    objects = query.find();
		    		
			         	Log.d("Check", "here it is");
			           	for (ParseObject r : objects)
			           //	for(ParseUser r : objects)
			         	{
			           		listPicName.add((String)r.get("ReportHeading"));
			           		listPicType.add((String)r.get("Subject"));
			           		listPicURL.add((String)r.get("F_aws_name"));
			           		listPicDrawable.add(R.drawable.filist);
			          /* 		listPicName.add((String)r.get("username"));
			           		listPicType.add((String)r.get("email"));
			           		listPicDrawable.add(R.drawable.pikachu);*/
			           		Log.d("Check", "in for parse");
			           	}
			}
			catch(ParseException e)
			{
				Log.d("Error", "Oh nooo..");
			    }
		}
		
		Log.d("Error","lists added");
		
		fliper = (ViewFlipper) findViewById(R.id.viewFlipper2);
		listview = (ListView) findViewById(R.id.listView2);
		for (int i = 0; i < listPicName.size(); i++) {
			Log.d("Error","for loop");
			Report pic = new Report(listPicName.get(i), listPicType.get(i),
					listPicDrawable.get(i),listPicURL.get(i));
			Log.d("Error","pic added");
			listPic.add(pic);
		}
		Log.d("Error","for exited");
		adapter = new ReportListAdapter(this, listPic);
		Log.d("Error","returned from adapter");
		listview.setAdapter(adapter);
		Log.d("Error","in adapter");
		listview.setOnItemClickListener(this);
		Log.d("Error","exited adapter");
		searchEdt = (EditText) findViewById(R.id.serach_edt);
		searchEdt.addTextChangedListener(this);
		webview = (WebView) findViewById(R.id.webView1);
	//	viewpic = (ImageView) findViewById(R.id.pic_report);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * @author 9Android.net
	 */
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		String text = searchEdt.getText().toString().toLowerCase();
		adapter.filter(text);
	}

	/**
	 *  @author 9Android.net
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.d("Error","click !11");
		Sliding.slideFromRightToLeft(VIEW_PIC_SCREEN, fliper);
	
		String url_s ="http://hconnectreports.s3.amazonaws.com/" + listPic.get(position).getPicURL();
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(url_s);
	//	viewpic.setImageResource(listPic.get(position).getPicSource());

	}

	 
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			int screen = fliper.getDisplayedChild();

			if (screen == VIEW_PIC_SCREEN) {
				Sliding.slideFromLeftToRight(LIST_PIC_SCREEN, fliper);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
