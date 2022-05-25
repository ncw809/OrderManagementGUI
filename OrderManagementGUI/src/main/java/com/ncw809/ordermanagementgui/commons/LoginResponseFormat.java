package com.ncw809.ordermanagementgui.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseFormat extends ResponseFormat{
	@SerializedName("data")
	@Expose
	public Data data;
	public class Data{
		@SerializedName("token")
		@Expose
		public String token;
	}
}
