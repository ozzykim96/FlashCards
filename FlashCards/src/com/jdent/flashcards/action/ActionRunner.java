package com.jdent.flashcards.action;

/**
 * ActionRunner 
 * get command and do action until there is no action handler returned.
 * - it runs series of actions.
 * 
 * @author CJY
 *
 */
public class ActionRunner {
	private ActionHandler handler;
	private ActionCommand actionCommand;

	public ActionRunner(ActionHandler handler, ActionCommand actionCommand) {
		this.handler = handler;
		this.actionCommand = actionCommand;
	}

	public void run() {		
		do {
			// get next command
			Command cmd = actionCommand.getCommand();

			// process action
			handler = handler.doAction(cmd);
			
		} while (handler != null);
	}
}
