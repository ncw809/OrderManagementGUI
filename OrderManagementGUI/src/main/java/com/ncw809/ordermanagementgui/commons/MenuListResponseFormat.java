package com.ncw809.ordermanagementgui.commons;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ncw809.ordermanagementgui.vo.Menu;

public class MenuListResponseFormat {
	@SerializedName("data")
	@Expose
	public Data data;
	public class Data{
		@SerializedName("menulist")
		@Expose
		public List<Menu> menus = new ArrayList<Menu>();
	}
}
