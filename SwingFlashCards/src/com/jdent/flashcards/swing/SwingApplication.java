package com.jdent.flashcards.swing;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class SwingApplication {
	private static final LogManager logManager = LogManager.getLogManager();
	private static final Logger LOGGER = Logger.getLogger(SwingApplication.class.getName());	
	static {
		try {
			logManager.readConfiguration(new FileInputStream("./logging.properties"));
		}
		catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in loading configuration.");
		}
	}
	
	public static void main(String[] args) {
		LOGGER.fine("Application is launched.");
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	private static void createAndShowGUI() {
		JFrame frame = new FlashCardsFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// display the window
		frame.pack();
		frame.setVisible(true);
	}
	
}
