package com.ncw809.ordermanagementgui.vo;

import com.google.gson.annotations.SerializedName;

public class Menu {
	@SerializedName("seq")
	private Long seq;

	@SerializedName("name")
	private String name;

	@SerializedName("price")
	private Long price;

	@SerializedName("store")
	private Store store;

	public Long getSeq() {
		return seq;
	}

	public String getName() {
		return name;
	}

	public Long getPrice() {
		return price;
	}

	public Menu(Long seq){
		this.seq = seq;
	}
}
