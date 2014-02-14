package com.example.health_connect;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PicListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<Picture> picList = null;
	private ArrayList<Picture> listpicOrigin;

	public PicListAdapter(Context context, List<Picture> picList) {
		mContext = context;
		this.picList = picList;
		mInflater = LayoutInflater.from(mContext);
		this.listpicOrigin = new ArrayList<Picture>();
		listpicOrigin.clear();
		this.listpicOrigin.addAll(picList);
		Log.d("Error","piclistorigin added");
	}

	public class ViewHolder {
		
		TextView picName;
		TextView picType;
		ImageView picIcon;
		public LinearLayout linearLayout;
	}

	public View getView(int position, View view, ViewGroup parent) {
		Log.d("Error","here1");
		final ViewHolder holder;
		
		//holder.linearLayout = (LinearLayout) view.findViewById(R.id.listpic_layout);
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
		Log.d("Error","Here!!");
		//holder.linearLayout = (LinearLayout) view.findViewById(R.id.listpic_layout);
		Log.d("Error","Or maybe here");
		Log.d("Error","here4");
		//holder.picName = new TextView(view.getContext());
		holder.picName.setText(picList.get(position).getPicName());
		//holder.linearLayout.addView(holder.picName);
		//holder.picType = new TextView(view.getContext());
		holder.picType.setText(picList.get(position).getPicType());
		//holder.linearLayout.addView(holder.picType);
		//holder.picIcon = new ImageView(view.getContext());
		
		
		holder.picIcon.setImageResource(picList.get(position).getPicSource());
		
		
		//holder.linearLayout.addView(holder.picIcon);
		//if(holder.linearLayout.getChildCount() > 0)
	      //  holder.linearLayout.removeAllViews();
		Log.d("Error","here5");
		return view;
	}

	public int getCount() {
		return picList.size();
	}

	public Picture getItem(int position) {
		return picList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * Filter
	 * @author 9Android.net
	 * 
	 */
	public void filter(String charText) {
		charText = charText.toLowerCase();
		picList.clear();
		if (charText.length() == 0) {
			picList.addAll(listpicOrigin);
		} else {
			for (Picture pic : listpicOrigin) {
				if (pic.getPicName().toLowerCase().contains(charText)) {
					picList.add(pic);
				}
			}
		}
		
		notifyDataSetChanged();
		
	}

}