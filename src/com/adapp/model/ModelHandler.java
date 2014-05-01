package com.adapp.model;

import android.os.Handler;

public class ModelHandler extends Handler {

	protected Base object;

	public ModelHandler(Base object) {
		this.object = object;
	}

}
