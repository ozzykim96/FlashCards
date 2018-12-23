package com.jdent.flashcards.swing.ui.tool;

import java.awt.GridBagConstraints;

public class GridBagConstraintsToolBuilder {
	private GridBagConstraints gbc;
	
	public GridBagConstraintsToolBuilder(GridBagConstraints gbc) {
		this.gbc = gbc;
	}
	
	public GridBagConstraintsTool build() {
		return new GridBagConstraintsTool(this.gbc);
	}
}
