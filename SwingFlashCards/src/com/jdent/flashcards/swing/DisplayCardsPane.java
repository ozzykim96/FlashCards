package com.jdent.flashcards.swing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jdent.flashcards.card.Card;
import com.jdent.flashcards.card.CardSet;

public class DisplayCardsPane extends JPanel 
		implements ListSelectionListener, CardUIContext {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DisplayCardsPane.class.getName());
	
	private JList<String> list;
	private DefaultListModel<String> listModel;
	CardSet cardSet;

	public DisplayCardsPane() {
		super(new GridBagLayout());
		
		LOGGER.info("create DisplayCardsPane pane");
		
		createPane();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
	}
	
	@Override
	public void setContext(Object obj) {
		cardSet = (CardSet)obj;
		
		loadContents();
	}
	
	private void loadContents() {
		// remove all elements
		listModel.removeAllElements();
		
		// add cards
		// display card list
		for (Card card: cardSet.getList()) {
			listModel.addElement(card.toString());
		}
	}	
	
	private void createPane() {
		// create list and scroll pane
		list = createDisplayCardsList();
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setPreferredSize(
				new Dimension(Constants.DEFAULT_LIST_WIDTH, Constants.DEFAULT_LIST_HEIGHT));
		
		// create control button pane
		JPanel controlButtonPane = createControlButtonPane();
		
		// add panes to panel
		GridBagConstraints gbc = FlashCardsUtil.getDefaultGridBagConstraints();
		GridBagConstraintsBuilder builder = new GridBagConstraintsBuilder(gbc);
		
		add(new JLabel("List of Cards:"), 
				builder.build().grid(0, 0));
		add(listScrollPane, 
				builder.build().grid(0, 1).weight(1.0, 1.0));		
		add(controlButtonPane, 
				builder.build().grid(0, 2));		
	}
	
	private JList<String> createDisplayCardsList() {
		listModel = new DefaultListModel<>();
		
		// create the list and put it in a scroll pane.
		JList<String> list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(30);
		
		return list;
	}
	
	private JPanel createControlButtonPane() {
		// create buttons
		JButton studyButton = new JButton("Study cards");
		studyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPane(Constants.STUDYCARDS_PANE, cardSet);
			}
		});
		JButton addCardButton = new JButton("Add a card");
		addCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPane(Constants.NEWCARDS_PANE, cardSet);
			}
		});
		JButton deleteCardButton = new JButton("Delete a card");
		deleteCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				
				if (index >= 0) {
					int dialogResult = JOptionPane.showConfirmDialog(
							null, "Would you like to delete this card?", "Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_NO_OPTION) {
						cardSet.remove(index);
						
						loadContents();
					}
				}
			}
		});
		
		JButton previousMenuButton = new JButton("Previous menu");
		previousMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPane(Constants.FLASHCARDS_PANE, null);
			}
		});
		
		// create a panel for buttons
		JPanel buttonPane = new JPanel();
		buttonPane.add(studyButton);
		buttonPane.add(addCardButton);
		buttonPane.add(deleteCardButton);
		buttonPane.add(previousMenuButton);
		
		return buttonPane;
	}
}

