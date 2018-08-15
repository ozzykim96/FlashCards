package com.jdent.flashcards.menu;

import com.jdent.flashcards.action.ActionHandler;

public interface MenuItemAction {
	ActionHandler onAction(MenuItemContext context);
}
