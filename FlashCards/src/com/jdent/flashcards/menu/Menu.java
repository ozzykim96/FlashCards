package com.jdent.flashcards.menu;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jdent.flashcards.action.ActionHandler;
import com.jdent.flashcards.action.Command;

/** 
 * Menu is a container of menu items, a context and an action handler.
 * 
 * @author CJY
 *
 */
public class Menu implements ActionHandler {
	private Menu parent;		// parent menu
	private Object context;		// context of menu
	private String title;		// menu title
	
	// item map (action string, menu item)
	private Map<String, MenuItem> itemMap = new LinkedHashMap<>();
	
	private void create(Menu parent, Object context) {
		this.parent = parent;
		this.context = context;
	}
	
	public Menu() {
		create(null, null);
	}
	
	public Menu(Menu parent) {
		create(parent, null);
	}

	public Menu getParent() {
		return parent;
	}
	
	public void setTitile(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Object getContext() {
		return context;
	}
	
	public void setContext(Object context) {
		this.context = context;
	}
	
	public void add(String action, MenuItem item) {
		// set menu of item
		item.setMenu(this);
		
		itemMap.put(action, item);
	}
	
	public void show() {
		System.out.println("---------------");
		
		System.out.print("  ");
		if (getParent() != null)
			System.out.print(".. ");
		if (getTitle() != null)
			System.out.print(getTitle());
		System.out.println();
		
		System.out.println("---------------");
		
		for (String action : itemMap.keySet()) {
			System.out.println("(" + action + ") " + itemMap.get(action));
		}
		
		System.out.println("---------------");
	}
	
	@Override
	public ActionHandler doAction(Command command) {
		ActionHandler handler = this;
		
		MenuItem item = itemMap.get(command.getCommand());
		if (item != null) {
			handler = item.onAction();
		}
		else {
			show();
		}

		return handler;
	}
}
