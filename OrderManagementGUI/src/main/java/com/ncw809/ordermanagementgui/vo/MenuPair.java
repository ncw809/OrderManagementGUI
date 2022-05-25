package com.ncw809.ordermanagementgui.vo;

import java.util.List;
import java.util.stream.Collectors;

public class MenuPair {

	private Menu menu;
	private Long quantity;

	public MenuPair(Menu menu, Long quantity){
		this.menu = menu;
		this.quantity = quantity;
	}

	@Override
	public String toString(){
		return menu.getSeq() + " : " + quantity;
	}

	public Long getPrice(){
		return menu.getPrice() * quantity;
	}

	public static String toString(List<MenuPair> menuPairList){
		return menuPairList.stream()
			.map(e -> e.toString())
			.collect(Collectors.joining(", "));
	}

	public Menu getMenu() {
		return menu;
	}

	public Long getQuantity() {
		return quantity;
	}
}
