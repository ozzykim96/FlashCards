package com.jdent.flashcards.swing;

import java.awt.Component;
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
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jdent.flashcards.card.Card;
import com.jdent.flashcards.card.CardSet;
import com.jdent.flashcards.swing.ui.tool.GridBagConstraintsToolBuilder;

public class DisplayCardsPane extends JPanel 
		implements ListSelectionListener, CardUIContext {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DisplayCardsPane.class.getName());
	
	private JList<Card> list;
	private DefaultListModel<Card> listModel;
	CardSet cardSet;
	
	JButton deleteCardButton;

	public DisplayCardsPane() {
		super(new GridBagLayout());
		LOGGER.info("create DisplayCardsPane pane");
		
		createUI();
	}
		
	private void createUI() {
		// create list and scroll pane
		list = createDisplayCardsList();
		JScrollPane listScrollPane = new JScrollPane(list);
		
		// create control button pane
		JPanel controlButtonPane = createControlButtonPane();
		
		GridBagConstraintsToolBuilder builder = 
				FlashCardsUtil.makeDefaultGridBagConstraintsBuilder();
		
		add(new JLabel("List of Cards:"), 
				builder.build().grid(0, 0));
		add(listScrollPane, 
				builder.build().grid(0, 1).weight(1.0, 1.0));		
		add(controlButtonPane, 
				builder.build().grid(0, 2));		
	}
		
	private JList<Card> createDisplayCardsList() {
		listModel = new DefaultListModel<>();
		
		// create the list and put it in a scroll pane.
		JList<Card> list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new CardRenderer());
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(30);
		
		return list;
	}
	
	private JPanel createControlButtonPane() {
		// create buttons
		JButton studyButton = new JButton("Study");
		studyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (cardSet.getStudiedCount() == cardSet.getCount()) {
					JOptionPane.showMessageDialog(FlashCardsFrame.getInstance(), "No cards to study.");
				}
				else {
					FlashCardsFrame.getInstance().switchPaneTo(Constants.STUDYCARDS_PANE, cardSet);	
				}				
			}
		});
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(
						FlashCardsFrame.getInstance(), 
						"Would you like to study again?", "Warning", 
						JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_NO_OPTION) {
					cardSet.resetStudy();
					
					loadContents();
				}
			}
		});
		
		JButton addCardButton = new JButton("Add");
		addCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPaneTo(Constants.NEWCARDS_PANE, cardSet);
			}
		});
		deleteCardButton = new JButton("Delete");
		deleteCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				
				if (index >= 0) {
					int dialogResult = JOptionPane.showConfirmDialog(
							FlashCardsFrame.getInstance(), 
							"Would you like to delete this card?", "Warning", 
							JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_NO_OPTION) {
						cardSet.remove(index);
						
						loadContents();
					}
				}
			}
		});
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPaneTo(Constants.FLASHCARDS_PANE, null);
			}
		});
		
		// create a panel for buttons
		JPanel buttonPane = new JPanel();
		buttonPane.add(studyButton);
		buttonPane.add(resetButton);
		buttonPane.add(addCardButton);
		buttonPane.add(deleteCardButton);
		buttonPane.add(backButton);
		
		return buttonPane;
	}
	
	private void loadContents() {
		// remove all elements
		listModel.removeAllElements();
		
		// add cards
		// display card list
		for (Card card: cardSet.getList()) {
			listModel.addElement(card);
		}
	}	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		refreshUI();
	}
	
	@Override
	public void setContext(Object obj) {
		cardSet = (CardSet)obj;	
		loadContents();
	}
	
	@Override
	public void refreshContents() {
		loadContents();
		refreshUI();
	}
	
	private void refreshUI() {
		int index = list.getSelectedIndex();
		
		if (index >= 0) {
			deleteCardButton.setEnabled(true);
		}
		else {
			deleteCardButton.setEnabled(false);
		}
	}	

	private class CardRenderer extends JLabel implements ListCellRenderer<Card> {
		private static final long serialVersionUID = 1L;

		public CardRenderer() {
			setOpaque(true);
		}
		
		@Override
		public Component getListCellRendererComponent(JList<? extends Card> list, 
				Card card, int index, boolean isSelected, boolean cellHasFocus) {

			setText(getFormatText(card));
			
			if (isSelected) {
			    setBackground(list.getSelectionBackground());
			    setForeground(list.getSelectionForeground());
			} else {
			    setBackground(list.getBackground());
			    setForeground(list.getForeground());
			}
			
			return this;
		}
		
		private String getFormatText(Card card) {
			StringBuilder builder = new StringBuilder();
			String text;
			
			builder.append("<html>");
			text = "<h1>" + card.getName() + " (" +  
					(card.getStudied() ? "O" : "X") +   
					")</h1>";
			builder.append(text);
			text = "<p>" + card.getDescription() + "</p>";
			builder.append(text);
			builder.append("</html>");
			
			return builder.toString();
		}		
	}	
}

