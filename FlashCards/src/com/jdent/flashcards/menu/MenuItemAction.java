package com.jdent.flashcards.menu;

import com.jdent.flashcards.action.ActionHandler;

public interface MenuItemAction {
	/**
	 * onAction is called, when this menu item is selected.
	 * 
	 * @param context
	 * @return return next handler (e.g. next menu item after this menu item action is done)
	 */
	ActionHandler onAction(MenuItemContext context);
}
