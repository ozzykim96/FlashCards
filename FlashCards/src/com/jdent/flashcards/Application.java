package com.jdent.flashcards;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.jdent.flashcards.action.ActionRunner;

public class Application {
	private static final LogManager logManager = LogManager.getLogManager();
	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());	
	static {
		try {
			logManager.readConfiguration(new FileInputStream("./logging.properties"));
		}
		catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in loading configuration.");
		}		
	}
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
