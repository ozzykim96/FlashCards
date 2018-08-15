package com.jdent.flashcards.menu;

import com.jdent.flashcards.action.ActionHandler;

public class MenuItem {
	private String name;
	private MenuItemAction action;
	private Menu menu;
	
	public MenuItem(String name) {
		this.name = name;
	}
		
	public String getName() {
		return name;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public void setAction(MenuItemAction action) {
		this.action = action;
	}

	public ActionHandler onAction() {
		MenuItemContext context = new MenuItemContext(this, menu.getContext());
		if (action != null)
			return action.onAction(context);
		else {
			System.out.println("No action");
		}
		
		return null;
	}

	@Override
	public String toString() {
		return name;
	}

}
