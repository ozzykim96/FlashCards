package com.jdent.flashcards.menu;

public class MenuItemContext {
	private MenuItem item;
	private Object context;
	
	public MenuItemContext(MenuItem item, Object context) {
		this.item = item;
		this.context = context;
	}
	
	public MenuItem getItem() {
		return item;
	}
	
	public Menu getMenu() {
		return item.getMenu();
	}
	
	public Object getContext() {
		return context;
	}
}
