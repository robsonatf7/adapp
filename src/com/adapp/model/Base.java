package com.adapp.model;

import java.util.ArrayList;

public class Base {

	private ArrayList<ModelDelegate> delegates;

	public static String APIHost = "192.168.0.108:3000"; //Renato


	public void notifyModel() {
		if (delegates == null) {
			return;
		}

		for (ModelDelegate delegate : delegates) {
			delegate.performedModel();
		}
	}

	public void notifyModel(String string, String className) {
		if (delegates == null) {
			return;
		}

		for (ModelDelegate delegate : delegates) {
			delegate.performedModel(string, className);
		}
	}

	public void addDelegate(ModelDelegate delegate) {
		if (this.delegates == null) {
			this.delegates = new ArrayList<ModelDelegate>();
		}
		this.delegates.add(delegate);
	}

	public void clearDelegates() {
		this.delegates = null;
		
	}
}
