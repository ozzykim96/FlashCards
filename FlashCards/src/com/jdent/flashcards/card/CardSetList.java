package com.jdent.flashcards.card;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class CardSetList implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
			System.out.println("load...");
			
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			cardSetList = (CardSetList)in.readObject();
			in.close();

			System.out.println(cardSetList);

			return cardSetList;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			System.out.println("CardsList class is not found");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean save(CardSetList cardSetList, String filename) {
		try {
			System.out.println("save...");
			System.out.println(cardSetList);			
			
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
