package com.ncw809.ordermanagementgui.vo;

import com.google.gson.annotations.SerializedName;

public class Table {
	@SerializedName("seq")
	private Long seq;

	@SerializedName("num")
	private Long num;

	@SerializedName("places")
	private Long places;

	@SerializedName("store")
	private Store store;

	public Long getPlaces() {
		return places;
	}
}
