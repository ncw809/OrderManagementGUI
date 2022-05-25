package com.ncw809.ordermanagementgui.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorityCheckResponseFormat extends ResponseFormat{
	@SerializedName("data")
	@Expose
	public Data data;
	public class Data{
		@SerializedName("authority")
		@Expose
		public String authority;
	}
}
