package com.jdent.flashcards.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jdent.flashcards.FlashCards;

public class FlashCardsFrame extends JFrame {	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FlashCardsFrame.class.getName());	
	private static FlashCardsFrame flashCardsFrame;
	
	private FlashCards flashCards;
	private JPanel cards;
	
	// item map (action string, JPanel)
	private Map<String, JPanel> cardsMap = new LinkedHashMap<>();
	
	public FlashCardsFrame() {
		super("FlashCards");
		flashCardsFrame = this;

		// flash cards
		flashCards = new FlashCards();
		
		LOGGER.info("load FlashCards.");
		
		// load saved flash cards
		if (!flashCards.load()) {
			LOGGER.warning("Loading failed. load default card sets");
			
			flashCards.buildDefaultCardsList();
		}
		
		// create cards panes
		cards = createCards();

		// set layout for frame
		setLayout(new BorderLayout());
		//add(cards, BorderLayout.CENTER);
		add(cards);
		
		pack();
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	
		    	// TODO: save flashcards
		    	
		    	LOGGER.info("exit FlashCards.");
		    }
		});		
		
		// switch to the first pane, Constants.FLASHCARDS_PANE
		switchPane(Constants.FLASHCARDS_PANE, flashCards.getCardsList());
	}
	
	public static FlashCardsFrame getInstance() {
		return flashCardsFrame;
	}
	
	public void switchPane(String name, Object obj) {
		// set context
		if (obj != null) {
			CardUIContext context = (CardUIContext)cardsMap.get(name);
			context.setContext(obj);		
		}

		// show card pane
		CardLayout cl = (CardLayout)cards.getLayout();
		cl.show(cards, name);
	}
		
	private JPanel createCards() {
		// create all panes
		cardsMap.put(Constants.FLASHCARDS_PANE, new FlashCardsPane());
		cardsMap.put(Constants.STUDYCARDS_PANE, new StudyCardsPane());
		cardsMap.put(Constants.DISPLAYCARDS_PANE, new DisplayCardsPane());
		cardsMap.put(Constants.NEWCARDS_PANE, new NewCardsPane());
		
		// create card layout pane
		JPanel cards = new JPanel(new CardLayout());
		
		cards.add(cardsMap.get(Constants.FLASHCARDS_PANE), Constants.FLASHCARDS_PANE);
		cards.add(cardsMap.get(Constants.STUDYCARDS_PANE), Constants.STUDYCARDS_PANE);
		cards.add(cardsMap.get(Constants.DISPLAYCARDS_PANE), Constants.DISPLAYCARDS_PANE);
		cards.add(cardsMap.get(Constants.NEWCARDS_PANE), Constants.NEWCARDS_PANE);
		cards.setOpaque(true);
		
		return cards;
	}
}
