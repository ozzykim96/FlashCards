package com.jdent.flashcards;

import com.jdent.flashcards.card.CardSet;
import com.jdent.flashcards.card.CardSetList;

public class FlashCards {
	private CardSetList cardsList;
	private static FlashCards flashCards;
	private final String filename = "cardslist.ser";

	public void buildDefaultCardsList() {
		cardsList = new CardSetList();
		
		// add first cards
		CardSet cards = new CardSet("example1");
		cards.buildDefault();
		cardsList.add(cards);
		
		// add second cards
		cards = new CardSet("example2");
		cards.buildDefault();		
		cardsList.add(cards);
	}
	
	public FlashCards() {
		flashCards = this;
	}
	
	public static FlashCards getInstance() {
		return flashCards;
	}
	
	public CardSetList getCardsList() {
		return cardsList;
	}
	
	public boolean load() {		
		cardsList = CardSetList.load(filename);
		return cardsList != null;
	}
	
	public boolean save() {
		return CardSetList.save(cardsList, filename);
	}
}
