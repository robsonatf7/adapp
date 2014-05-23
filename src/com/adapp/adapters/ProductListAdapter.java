package com.adapp.adapters;

import com.adapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductListAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] values;
	
	public ProductListAdapter(Context context, String[] values) {
		super(context, R.layout.product_row, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.product_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.productList);
		textView.setText(values[position]);
		
		return rowView;
	}

}