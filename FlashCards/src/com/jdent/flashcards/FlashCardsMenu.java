package com.jdent.flashcards;

import com.jdent.flashcards.action.ActionHandler;
import com.jdent.flashcards.action.Command;
import com.jdent.flashcards.card.Card;
import com.jdent.flashcards.card.CardSet;
import com.jdent.flashcards.card.CardSetList;
import com.jdent.flashcards.menu.DefaultMenuItemFactory;
import com.jdent.flashcards.menu.Menu;
import com.jdent.flashcards.menu.MenuCommand;
import com.jdent.flashcards.menu.MenuItem;
import com.jdent.flashcards.menu.MenuItemAction;
import com.jdent.flashcards.menu.MenuItemContext;

public class FlashCardsMenu {
	private Menu rootMenu;
	private MenuCommand menuCommand = new MenuCommand();

	public FlashCardsMenu(FlashCards flashCards) {
		rootMenu = createRootMenu(flashCards);
	}
	
	public Menu getRootMenu() {
		return rootMenu;
	}
	
	public MenuCommand getMenuCommand() {
		return menuCommand;
	}
	
	/**
	 * create root menu
	 * 
	 * (s) select cards
	 * (c) create cards
	 * (d) delete cards
	 * (x) exit
	 * 
	 * @return
	 */
	private Menu createRootMenu(FlashCards flashCards) {
		// create new cards
		MenuItem createCards = new MenuItem("create a new set of cards");
		createCards.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {
				// get title and create a card set
				String title = MenuCommand.getNextLine("name: ");
				
				CardSet cardSet = new CardSet(title);
				
				CardSetList cardSetList = (CardSetList)context.getContext();
				cardSetList.add(cardSet);
				
				return context.getMenu();
			}
		});
		
		// delete cards
		MenuItem deleteCards = new MenuItem("delete a set of cards");
		deleteCards.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {
				CardSetList cardSetList = (CardSetList)context.getContext();
				
				for (int i = 0; i < cardSetList.getList().size(); i++) {
					System.out.print(i + ":");
					CardSet cards = cardSetList.getCards(i);
					System.out.println(cards.getTitle());
				}
				
				// select cardset (index) and delete it
				String num = MenuCommand.getNextLine("> ");
				
				cardSetList.remove(Integer.parseInt(num));
				
				return context.getMenu();
			}
		});
		
		// create root menu
		Menu menu = new Menu();
		menu.setContext(flashCards.getCardsList());
		menu.setTitile("FlashCards");
		
		menu.add("s", createSelectCards(menu));
		menu.add("c", createCards);
		menu.add("x", DefaultMenuItemFactory.createExitMenuItem());
		
		return menu;
	}
	
	/**
	 * create sub menu of select cards
	 * 
	 * (d) display cards
	 * (s) study card
	 * (a) add a card
	 * (b) back
	 * 
	 * @param parent
	 * @return select cards menu item
	 */
	private MenuItem createSelectCards(Menu parent) {
		// display card list
		MenuItem displayCards = new MenuItem("display cards");
		displayCards.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {
				CardSet cards = (CardSet)context.getContext();
				
				// display card list
				for (Card card: cards.getList()) {
					System.out.println(card);
				}
				return context.getMenu();
			}
		});
		
		// add card
		MenuItem addCard = new MenuItem("add a card");
		addCard.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {	
				CardSet cards = (CardSet)context.getContext();
				
				// input and add it to card list
				String name = MenuCommand.getNextLine("name: ");
				String desc = MenuCommand.getNextLine("description: ");
				cards.add(name, desc);
				return context.getMenu();
			}
		});
		
		// reset study
		MenuItem resetStudy = new MenuItem("reset study");
		resetStudy.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {
				CardSet cards = (CardSet)context.getContext();
				
				cards.resetStudy();
				
				return context.getMenu();
			}
		});
		
		// select cards menu
		Menu selectCards = new Menu(parent);
		selectCards.setTitile("Select Cards");
		
		// add items to rootMenu
		selectCards.add("d", displayCards);
		selectCards.add("s", createStudyCard(selectCards));
		selectCards.add("a", addCard);
		selectCards.add("r", resetStudy);
		selectCards.add("b", DefaultMenuItemFactory.createBackMenuItem());
		
		// select card set item. when selected, returns selectCards menu.
		MenuItem selectCardsItem = new MenuItem("select cards");
		selectCardsItem.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {
				
				CardSetList cardSetList = (CardSetList)context.getContext();
				
				// show card set list
				for (int i = 0; i < cardSetList.getList().size(); i++) {
					System.out.print(i + ":");
					CardSet cards = cardSetList.getCards(i);
					System.out.println(cards.getTitle());
				}
				
				// select card set
				String num = MenuCommand.getNextLine("> ");
				
				CardSet cards = cardSetList.getCards(Integer.parseInt(num));
				cards.reset();
				
				// set cards to study menu context
				selectCards.setContext(cards);
				selectCards.show();
				
				return selectCards;				
			}
		});
		
		return selectCardsItem;
	}
	
	/**
	 * create sub menu of study card menu
	 * 
	 * (n) next card
	 * (d) delete card
	 * (b) back
	 * 
	 * @param parent
	 * @return
	 */
	private MenuItem createStudyCard(Menu parent) 
	{
		// show next card
		MenuItem showNextCard = new MenuItem("next card");
		showNextCard.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {
				CardSet cards = (CardSet)context.getContext();
				String answer;

				Card card = cards.nextStudy();
				if (card == null) {
					Menu parent = context.getMenu().getParent();
					
					System.out.println("No cards to study.");
					parent.show();
					
					return parent;
				}
				
				answer = MenuCommand.getNextLine(card.getName() + " (y/n)?");
				if (answer.equals("y")) 
					card.setStudied(true);
				System.out.println(card.getDescription());
				
				return context.getMenu();
			}
		});
		// delete previous card
		MenuItem deleteCard = new MenuItem("delete card");
		deleteCard.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) {
				
				CardSet cardSet = (CardSet)context.getContext();
				cardSet.remove();
				
				return context.getMenu();
			}
		});
		
		// study card menu
		Menu study = new Menu(parent);
		study.setTitile("Study Cards");
		
		study.add("n", showNextCard);
		study.add("d", deleteCard);
		study.add("b", DefaultMenuItemFactory.createBackMenuItem());
		
		MenuItem studyCard = new MenuItem("study card");
		studyCard.setAction(new MenuItemAction() {
			@Override
			public ActionHandler onAction(MenuItemContext context) 
			{		
				CardSet cardSet = (CardSet)context.getContext();
				cardSet.reset();
				
				// set cardSet to study menu context
				study.setContext(cardSet);
				
				// show the first card
				study.doAction(new Command("n"));
				
				study.show();
				
				return study;
			}
		});
		
		return studyCard;
	}
}
