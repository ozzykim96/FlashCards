package com.jdent.flashcards.menu;

import java.util.Scanner;

import com.jdent.flashcards.action.ActionCommand;
import com.jdent.flashcards.action.Command;

public class MenuCommand implements ActionCommand {
	private static Scanner scan = new Scanner(System.in);
	
	@Override
	public Command getCommand() {
		String command = getNextLine("> ");
		
		return new Command(command);
	}
	
	public static String getNextLine(String prompt) {
		System.out.print(prompt);
		return scan.nextLine();
	}
}
