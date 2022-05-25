package com.ncw809.ordermanagementgui.vo;

import java.time.LocalTime;

import com.google.gson.annotations.SerializedName;

public class Store {

	@SerializedName("seq")
	private Long seq;

	@SerializedName("openTime")
	private String openTime;

	@SerializedName("closeTime")
	private String closeTime;

	@SerializedName("name")
	private String name;

	public String getName() {
		return name;
	}
	public Long getSeq() {
		return seq;
	}
}
