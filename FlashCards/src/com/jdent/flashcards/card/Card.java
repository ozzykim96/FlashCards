package com.jdent.flashcards.card;

import java.io.Serializable;

public class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private boolean studied;
	
	public Card(String name, String description) {
		this.name = name;
		this.description = description;
		this.studied = false;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean getStudied() {
		return studied;
	}
	
	public void setStudied(boolean studied) {
		this.studied = studied;
	}
	
	@Override
	public String toString() {
		String ox = studied ? "(O)" : "(X)";
		return name + ":" + description + ":" + ox;
	}
}
