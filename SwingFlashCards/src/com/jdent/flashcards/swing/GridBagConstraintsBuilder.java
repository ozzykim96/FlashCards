package com.jdent.flashcards.swing;

import java.awt.GridBagConstraints;

public class GridBagConstraintsBuilder {
	private GridBagConstraints gbc;
	
	public GridBagConstraintsBuilder(GridBagConstraints gbc) {
		this.gbc = gbc;
	}
	
	public GridBagConstraintsTool build() {
		return new GridBagConstraintsTool(this.gbc);
	}
}
