package com.jdent.flashcards;

import com.jdent.flashcards.action.ActionRunner;

public class Application {
	public static void main(String[] args) {
		// create FlashCards context
		FlashCards flashCards = new FlashCards();
		// load saved flash cards
		if (!flashCards.load()) {
			flashCards.buildDefaultCardsList();
		}
		
		// create menu
		FlashCardsMenu menu = new FlashCardsMenu(flashCards);
		
		// create action runner
		ActionRunner runner = new ActionRunner(menu.getRootMenu(), menu.getMenuCommand());
		
		System.out.println("Welcome to FlashCards");
		
		// run actions
		runner.run();
		
		// save changes
		flashCards.save();
		
		System.out.println("exit.");
		
		
	}
}
