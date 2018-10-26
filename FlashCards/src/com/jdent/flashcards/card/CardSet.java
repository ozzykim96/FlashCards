package com.jdent.flashcards.card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CardSet implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cardsTitle;

	private ArrayList<Card> cardList = new ArrayList<>();
	
	private transient Iterator<Card> iter = cardList.iterator();
		
	public CardSet(String cardsTitle) {
		this.cardsTitle = cardsTitle;
	}
	
	public void buildDefault() {
		cardList = new ArrayList<>(
				Arrays.asList(new Card("banana", "바나나"),
						new Card("icecream", "아이스크림"),
						new Card("chocolate", "초콜렛")
				));
	}
	
	public String getTitle() {
		return cardsTitle;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Card> getList() {
		return (ArrayList<Card>)cardList.clone();
	}

	public int getCount() {
		return cardList.size();
	}
	
	public int getStudiedCount() {
		int totalStudied = 0;
		for (Card card: cardList) {
			
			if (card.getStudied())
				totalStudied++;
		}
		
		return totalStudied;
	}
	
	public void reset() {
		iter = cardList.iterator();
	}
	
	public void resetStudy() {
		for (Card card: cardList) {
			card.setStudied(false);
		}
	}
	
	public Card next() {
		if (!iter.hasNext())
			iter = cardList.iterator();
		
		return iter.next();
	}
	
	public Card nextStudy() {
		Card card;
		while (true) {
			if (!iter.hasNext())
				iter = cardList.iterator();
			
			card = iter.next();
			if (!card.getStudied())
				break;
		}
		
		return card;
	}
	
	public void add(String name, String desc) {
		cardList.add(new Card(name, desc));
	}
	
	public void remove() {
		try {
			iter.remove();
		}
		catch (IllegalStateException e) {
			System.out.println("No card is shown yet.");
		}
	}
	
	public void remove(int index) {
		cardList.remove(index);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("[" + getTitle() + "]");
		for (Card card : getList()) {
			builder.append(card.getName() + ":" + card.getDescription() + ",");
		}
		return builder.toString();
	}
}
