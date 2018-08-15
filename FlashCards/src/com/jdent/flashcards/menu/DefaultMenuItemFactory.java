package com.jdent.flashcards.menu;

import com.jdent.flashcards.action.ActionHandler;

public class DefaultMenuItemFactory {
	public static MenuItem createExitMenuItem()
	{
		MenuItem exit = new MenuItem("exit");
		exit.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {
				return null;
			}
		});
		
		return exit;
	}
	
	public static MenuItem createBackMenuItem()
	{
		MenuItem back = new MenuItem("back");
		back.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {				
				Menu parent = context.getMenu().getParent();
				
				// set next action to parent menu				
				// show menu
				parent.show();
				
				// return to the previous menu
				return parent;
			}
		});	
		
		return back;
	}
}
