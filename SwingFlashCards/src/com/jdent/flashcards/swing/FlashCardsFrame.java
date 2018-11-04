package com.jdent.flashcards.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.jdent.flashcards.FlashCards;

public class FlashCardsFrame extends JFrame {	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FlashCardsFrame.class.getName());	
	private static FlashCardsFrame flashCardsFrame;
	
	private Preferences node;
	
	private FlashCards flashCards;
	private JPanel cardPane;
	
	// item map (action String, JPanel)
	private Map<String, JPanel> cardPaneMap = new LinkedHashMap<>();
	
	public FlashCardsFrame() {
		super("FlashCards");
		flashCardsFrame = this;

		loadFlashCards();
		
		// frame initialization
		loadFrame();
		
		// create menu bar
		setJMenuBar(createMenuBar());
		
		// create cards panes
		cardPane = createCardPanes();

		// set layout for frame
		setLayout(new BorderLayout());
		add(cardPane);
		
		pack();
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {		
		    	closeFlashCards();
		    	saveFrame();
		    	
		    	LOGGER.info("Closing FlashCards.");
		    }
		});		
		
		// switch to the first pane, Constants.FLASHCARDS_PANE
		switchPane(Constants.FLASHCARDS_PANE, flashCards.getCardsList());
	}
	
	public static FlashCardsFrame getInstance() {
		return flashCardsFrame;
	}
	
	public void switchPane(String name, Object obj) {
		CardUIContext context = (CardUIContext)cardPaneMap.get(name);
		assert context != null;
		
		// set context
		if (obj != null) {
			context.setContext(obj);		
		}
		context.refreshContents();

		// show card pane
		CardLayout cl = (CardLayout)cardPane.getLayout();
		cl.show(cardPane, name);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		
		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Exit FlashCards.");

				closeFlashCards();
				saveFrame();
				
				System.exit(0);
			}
		});
		menu.add(menuItem);

		menuBar.add(menu);
		return menuBar;
	}
	
	private JPanel createCardPanes() {
		// create all panes
		cardPaneMap.put(Constants.FLASHCARDS_PANE, new FlashCardsPane());
		cardPaneMap.put(Constants.STUDYCARDS_PANE, new StudyCardsPane());
		cardPaneMap.put(Constants.DISPLAYCARDS_PANE, new DisplayCardsPane());
		cardPaneMap.put(Constants.NEWCARDS_PANE, new NewCardsPane());
		
		// create card layout pane
		JPanel cards = new JPanel(new CardLayout());
		
		cards.add(cardPaneMap.get(Constants.FLASHCARDS_PANE), Constants.FLASHCARDS_PANE);
		cards.add(cardPaneMap.get(Constants.STUDYCARDS_PANE), Constants.STUDYCARDS_PANE);
		cards.add(cardPaneMap.get(Constants.DISPLAYCARDS_PANE), Constants.DISPLAYCARDS_PANE);
		cards.add(cardPaneMap.get(Constants.NEWCARDS_PANE), Constants.NEWCARDS_PANE);
		cards.setOpaque(true);
		
		return cards;
	}
	
	private void loadFlashCards() {
		LOGGER.info("Load FlashCards.");

		// flash cards
		flashCards = new FlashCards();		
		
		// load saved flash cards
		if (!flashCards.load()) {
			LOGGER.warning("Loading failed. Load default card sets");
			
			flashCards.buildDefaultCardsList();
		}		
	}
	
	private void closeFlashCards() {
		assert flashCards != null;
		
		LOGGER.info("Save FlashCards.");
		
		flashCards.save();
	}
	
	private void loadFrame() {
		Preferences root = Preferences.userRoot();
		node = root.node("/com/jdent/flashcards/swing");
		
		int left = node.getInt("left", 0);
		int top = node.getInt("top", 0);
		int width = node.getInt("width", Constants.DEFAULT_FRAME_WIDTH);
		int height = node.getInt("height", Constants.DEFAULT_FRAME_HEIGHT);
		setBounds(left, top, width, height);
		
		setPreferredSize(new Dimension(width, height));
	}
	
	private void saveFrame() {
		assert node != null;
		
        node.putInt("left", getX());
        node.putInt("top", getY());
        node.putInt("width", getWidth());
        node.putInt("height", getHeight());
	}
}
