package com.ncw809.ordermanagementgui.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFormat {
	@SerializedName("httpStatus")
	@Expose
	public String httpStatus;

	@SerializedName("message")
	@Expose
	public String message;
}
