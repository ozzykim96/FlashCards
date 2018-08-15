package com.jdent.flashcards.action;

public interface ActionHandler {
	
	/*
	 * doAction
	 * do some action and return next action handler
	 * 
	 * @param action action string
	 * @return next action handler. if next action handler is null, exit action runner.
	 */
	public ActionHandler doAction(Command command);
}
