package com.adapp.adapter;

import java.util.ArrayList;
import java.util.List;

import com.adapp.CategoryListActivity.ListViewItem;
import com.adapp.ProductListActivity;
import com.adapp.R;
import com.adapp.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryListAdapter extends BaseAdapter {
	
	public List<ListViewItem> items;
	private Context context;
	private ArrayList<ListViewItem> arrayList;
	
	public CategoryListAdapter(Activity context, List<ListViewItem> items) {
		this.context = context;
		this.items = items;
	}
	
	public List setItems(Context context, List<ListViewItem> items) {
		this.context = context;
		this.items = items;
		this.arrayList = new ArrayList<ListViewItem>();
		this.arrayList.addAll(items);
		return this.arrayList;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ListViewItem item = items.get(position);
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.category_row, parent, false);
			holder = new ViewHolder();
			holder.textViewName = (TextView) convertView.findViewById(R.id.category_row_name);
			holder.textViewTotal = (TextView) convertView.findViewById(R.id.category_row_total);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textViewName.setText(item.name);
		holder.textViewTotal.setText(item.total);
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ProductListActivity.class);
				intent.putExtra("idlabel", item.id);
				
				context.startActivity(intent);
			}
		});
		return convertView;
	}
	
	public static class ViewHolder {
		TextView textViewName, textViewTotal;
	}
	
	@Override
	public int getViewTypeCount(){
		return getCount();
	}
	
	@Override
	public int getItemViewType(int position) {
		return position;
	}

}