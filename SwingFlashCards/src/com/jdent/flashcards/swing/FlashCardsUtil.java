package com.jdent.flashcards.swing;

import java.awt.GridBagConstraints;

public class FlashCardsUtil {
	public static GridBagConstraints getDefaultGridBagConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.gridwidth = GridBagConstraints.RELATIVE;
		//gbc.gridheight = GridBagConstraints.RELATIVE;
		gbc.weightx = 1.0;
		
		return gbc;
	}
}
