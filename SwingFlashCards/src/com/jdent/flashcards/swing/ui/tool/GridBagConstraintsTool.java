package com.jdent.flashcards.swing.ui.tool;

import java.awt.GridBagConstraints;

public class GridBagConstraintsTool extends GridBagConstraints {
	private static final long serialVersionUID = 1L;

	public GridBagConstraintsTool(GridBagConstraints gbc) {
		this.fill = gbc.fill;

		this.gridx = gbc.gridx;
		this.gridy = gbc.gridy;
		this.weightx = gbc.weightx;
		this.weighty = gbc.weighty;
		
		// TODO: set other properties
	}
	
	public GridBagConstraintsTool grid(int x, int y) {
		gridx = x;
		gridy = y;
		return this;
	}
	
	public GridBagConstraintsTool weight(double x, double y) {
		weightx = x;
		weighty = y;
		return this;
	}	
	
	public GridBagConstraintsTool width(int width) {
		this.gridwidth = width;
		return this;
	}
	
	public GridBagConstraintsTool height(int height) {
		this.gridheight = height;
		return this;
	}	
}
