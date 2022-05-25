package com.ncw809.ordermanagementgui.commons;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ncw809.ordermanagementgui.vo.Store;

public class StoreListResponseFormat extends ResponseFormat {
	@SerializedName("data")
	@Expose
	public Data data;
	public class Data{
		@SerializedName("storelist")
		@Expose
		public List<Store> stores = new ArrayList<Store>();
	}
}
