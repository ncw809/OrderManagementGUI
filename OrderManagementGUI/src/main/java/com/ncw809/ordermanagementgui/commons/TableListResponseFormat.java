package com.ncw809.ordermanagementgui.commons;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ncw809.ordermanagementgui.vo.Table;

public class TableListResponseFormat {
	@SerializedName("data")
	@Expose
	public Data data;
	public class Data{
		@SerializedName("tablelist")
		@Expose
		public List<Table> tables = new ArrayList<Table>();
	}
}
