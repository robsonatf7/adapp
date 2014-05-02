package com.adapp.adapter;

import java.util.ArrayList;
import java.util.List;

import com.adapp.ProductListActivity;
import com.adapp.ProductListActivity.ListViewItem;
import com.adapp.R;
import com.adapp.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductListAdapter extends BaseAdapter {
	
	public List<com.adapp.CategoryListActivity.ListViewItem> items;
	private Context context;
	private ArrayList<ListViewItem> arrayList;
	
	public ProductListAdapter(Activity context, List<ListViewItem> items) {
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
			convertView = li.inflate(R.layout.product_row, parent, false);
			holder = new ViewHolder();
			holder.textViewName = (TextView) convertView.findViewById(R.id.product_row_name);
			holder.textViewPrice = (TextView) convertView.findViewById(R.id.product_row_price);
			holder.textViewLocation = (TextView) convertView.findViewById(R.id.product_row_location);
			holder.imageView = (ImageView) convertView.findViewById(R.id.product_row_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.textViewName.setText(item.productName);
		holder.textViewPrice.setText(item.productPrice);
		holder.textViewLocation.setText(item.productLocation);
		
	}
	
	public static class ViewHolder {
		TextView textViewName, textViewPrice, textViewLocation;
		ImageView imageView;
	}
	
	@Override
	public int getViewTypeCount() {
		return getCount();
	}
	
	@Override
	public int getItemViewType(int position) {
		return position;
	}
}
