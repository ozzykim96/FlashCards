package com.jdent.flashcards.swing;

import java.awt.GridBagConstraints;

public class GridBagConstraintsBuilder {
	private GridBagConstraints gbc;
	private GridBagConstraints originalGbc;
	
	public GridBagConstraintsBuilder(GridBagConstraints gbc) {
		this.gbc = gbc;
		this.originalGbc = (GridBagConstraints)gbc.clone();
	}
	
	public GridBagConstraintsBuilder reset() {
		this.gbc = (GridBagConstraints)this.originalGbc.clone();
		return this;
	}
	
	public GridBagConstraintsBuilder grid(int x, int y) {
		gbc.gridx = x; 
		gbc.gridy = y;
		return this;
	}
	
	public GridBagConstraintsBuilder weight(double x, double y) {
		gbc.weightx = x;
		gbc.weighty = y;
		return this;
	}
	
	public GridBagConstraints get() {
		return this.gbc;
	}
}
