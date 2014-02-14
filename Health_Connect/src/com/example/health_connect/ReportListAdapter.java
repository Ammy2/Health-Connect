package com.example.health_connect;

import java.util.ArrayList;
import java.util.List;

import com.example.health_connect.PicListAdapter.ViewHolder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReportListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<Report> picList = null;
	private ArrayList<Report> listpicOrigin;

	public ReportListAdapter(Context context, List<Report> picList) {
		mContext = context;
		this.picList = picList;
		mInflater = LayoutInflater.from(mContext);
		this.listpicOrigin = new ArrayList<Report>();
		this.listpicOrigin.addAll(picList);
		Log.d("Error","piclistorigin added");
	}

	
	public class ViewHolder {
		
		TextView picName;
		TextView picType;
		ImageView picIcon;
	}

	public View getView(int position, View view, ViewGroup parent) {
		Log.d("Error","here1");
		final ViewHolder holder;
		if (view == null) {
			Log.d("Error","here21");
			holder = new ViewHolder();
			view = mInflater.inflate(R.layout.list_item, null);
			holder.picName = (TextView) view.findViewById(R.id.pic_name_txt);
			Log.d("Error","here22");
			holder.picType = (TextView) view.findViewById(R.id.pic_type_txt);
			Log.d("Error","here23");
			holder.picIcon = (ImageView) view.findViewById(R.id.pic_icon_img);
			view.setTag(holder);
		} else {
			Log.d("Error","here3");
			holder = (ViewHolder) view.getTag();
		}
		Log.d("Error","here4");
		holder.picName.setText(picList.get(position).getPicName());
		holder.picType.setText(picList.get(position).getPicType());
		holder.picIcon.setImageResource(picList.get(position).getPicSource());
		Log.d("Error","here5");
		return view;
	}

	public int getCount() {
		return picList.size();
	}

	public Report getItem(int position) {
		return picList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public void filter(String charText) {
		charText = charText.toLowerCase();
		picList.clear();
		if (charText.length() == 0) {
			picList.addAll(listpicOrigin);
		} else {
			for (Report pic : listpicOrigin) {
				if (pic.getPicName().toLowerCase().contains(charText)) {
					picList.add(pic);
				}
			}
		}
		notifyDataSetChanged();
	}


}
