package com.jdent.flashcards.card;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jdent.flashcards.Application;

public class CardSetList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());	
	
	private ArrayList<CardSet> cardsList = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public ArrayList<CardSet> getList() {
		return (ArrayList<CardSet>)cardsList.clone();
	}
	
	public CardSet getCards(int index) {
		return cardsList.get(index);
	}
	
	public void add(CardSet cards) {
		cardsList.add(cards);
	}
	
	public void remove(int index) {
		cardsList.remove(index);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (CardSet cards : getList()) {
			builder.append(cards);
		}
		return builder.toString();
	}
	
	public static CardSetList load(String filename) {
		CardSetList cardSetList;
		
		try {
			LOGGER.log(Level.FINE, "Load CardSetList file {0}", filename);
			
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			cardSetList = (CardSetList)in.readObject();
			in.close();

			if (cardSetList != null)
				LOGGER.fine(cardSetList.toString());

			return cardSetList;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			LOGGER.severe("CardSetList class is not found.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean save(CardSetList cardSetList, String filename) {
		try {
			LOGGER.log(Level.FINE, "Save CardSetList file {0}.", filename);
			LOGGER.fine(cardSetList.toString());
			
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(cardSetList);
			out.close();
			
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
