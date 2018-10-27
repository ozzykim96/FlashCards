package com.jdent.flashcards.swing.ui;

import java.awt.Font;

import javax.swing.JLabel;

public class FlashCardsLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	public FlashCardsLabel(String text) {
		super(text);
		Font font = getFont();
		
		setFont(new Font(font.getName(), Font.PLAIN, 30));
	}
}
