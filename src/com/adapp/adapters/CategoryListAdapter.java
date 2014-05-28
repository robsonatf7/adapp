package com.adapp.adapters;

import java.util.ArrayList;

import com.adapp.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CategoryListAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] values;
	
	
	public CategoryListAdapter(Context context, String[] values) {
		super(context, R.layout.category_row, values);
		this.context = context;
		this.values = values;
		
//		for (String s : values){
//			Log.i("categoreisString", s);
//		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.category_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.category_row_text);
		textView.setText(values[position]);
		
		return rowView;
	}

}
