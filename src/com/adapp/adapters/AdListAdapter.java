package com.adapp.adapters;

import com.adapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdListAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] values;
	
	public AdListAdapter(Context context, String[] values) {
		super(context, R.layout.ad_row, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.ad_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.ad_row_text);
		textView.setText(values[position]);
		
		return rowView;
	}

}