package com.jdent.flashcards.swing;

import java.awt.GridBagConstraints;

import com.jdent.flashcards.swing.ui.tool.GridBagConstraintsToolBuilder;

public class FlashCardsUtil {	
	public static GridBagConstraintsToolBuilder makeDefaultGridBagConstraintsBuilder() {
		GridBagConstraints gbc = new GridBagConstraints();
		
		// default properties
		gbc.fill = GridBagConstraints.BOTH;

		return new GridBagConstraintsToolBuilder(gbc);
	}
}
