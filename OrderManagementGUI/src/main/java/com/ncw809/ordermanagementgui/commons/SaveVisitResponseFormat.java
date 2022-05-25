package com.ncw809.ordermanagementgui.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveVisitResponseFormat extends ResponseFormat{
	@SerializedName("data")
	@Expose
	public Data data;
	public class Data{
		@SerializedName("seq")
		@Expose
		public Long seq;
	}
}
