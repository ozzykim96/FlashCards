package com.jdent.flashcards.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import com.jdent.flashcards.card.CardSet;
import com.jdent.flashcards.card.CardSetList;
import com.jdent.flashcards.swing.ui.FlashCardsLabel;
import com.jdent.flashcards.swing.ui.tool.GridBagConstraintsBuilder;

public class FlashCardsPane extends JPanel 
		implements ListSelectionListener, CardUIContext {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FlashCardsPane.class.getName());
	
	private JList<CardSet> list;
	private DefaultListModel<CardSet> listModel;
	private CardSetList cardSetList;
	private int previousIndex = -1;
	
	private JButton selectButton;
	private JButton deleteButton;

	public FlashCardsPane() {
		super(new GridBagLayout());
		
		LOGGER.info("create FlashCards pane.");
		
		createUI();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		refreshUI();
	}
	
	@Override
	public void setContext(Object obj) {
		cardSetList = (CardSetList)obj;
		
		loadContents();
	}
	
	@Override
	public void refreshContents() {
		loadContents();
		
		int index = list.getSelectedIndex();
		if (index >= 0)
			previousIndex = index;
		
		refreshUI();
	}
	
	private void createUI() {
		// create the list and put it in a scroll pane.
		list = createDisplayCardsetList();
		
		// scroll pane
		JScrollPane listScrollPane = new JScrollPane(list);
		
		// create a panel for buttons
		JPanel controlButtonPane = createControlButtonPane();

		// add to pane
		GridBagConstraints gbc = FlashCardsUtil.getDefaultGridBagConstraints();
		GridBagConstraintsBuilder builder = new GridBagConstraintsBuilder(gbc);
		
		add(new JLabel("Select cards:"), 
				builder.build().grid(0, 0));
		add(listScrollPane, 
				builder.build().grid(0, 1).weight(1.0, 1.0));
		add(controlButtonPane, 
				builder.build().grid(0, 2));
	}
	
	private void loadContents() {
		// remove all elements
		listModel.removeAllElements();
		
		// add cards
		for (int i = 0; i < cardSetList.getList().size(); i++) {
			CardSet cards = cardSetList.getCards(i);
			listModel.addElement(cards);
		}
	}
	
	private void refreshUI() {
		int index = list.getSelectedIndex();
		boolean selected = false;
		
		if (index < 0) {
			if (previousIndex >= 0 && previousIndex < cardSetList.getList().size()) {
				list.setSelectedIndex(previousIndex);			
				selected = true;
			}
			else if (cardSetList.getList().size() > 0) {
				list.setSelectedIndex(0);
				selected = true;
			}
		}
		else {
			selected = true;
		}
		
		if (selected) {
			selectButton.setEnabled(true);
			deleteButton.setEnabled(true);						
		}
		else {
			selectButton.setEnabled(false);
			deleteButton.setEnabled(false);				
		}		
	}
	
	@SuppressWarnings("unchecked")
	private JList<CardSet> createDisplayCardsetList() {
		listModel = new DefaultListModel<>();
						
		// create the list and put it in a scroll pane.
		JList<CardSet> list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new CardSetRenderer());
		list.setSelectedIndex(0);
		
		list.addListSelectionListener(this);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<String> list = (JList<String>)evt.getSource();					
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					
					if (index >= 0) {
						CardSet cardSet = cardSetList.getCards(index);
						
						FlashCardsFrame.getInstance()
								.switchPane(Constants.DISPLAYCARDS_PANE, cardSet);						
					}
				}
			}
		});
		
		return list;
	}
	
	private JPanel createControlButtonPane() {
		selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				
				if (index >= 0) {
					previousIndex = index;
					
					CardSet cardSet = cardSetList.getCards(index);
					FlashCardsFrame.getInstance()
						.switchPane(Constants.DISPLAYCARDS_PANE, cardSet);
				}				
			}
		});
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// show dialog to enter name
				String name = JOptionPane.showInputDialog("Enter the name of the cards?");
				
				if (name != null) {
					// add a new card set
					CardSet cardSet = new CardSet(name);
					cardSetList.add(cardSet);
					
					loadContents();
				}
			}			
		});
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				
				if (index >= 0) {
					int dialogResult = JOptionPane.showConfirmDialog(
							null, "Would you like to delete this cards?", "Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_NO_OPTION) {
						cardSetList.remove(index);	
						
						refreshContents();
					}
				}
			}
		});
		
		// create a panel for buttons
		JPanel buttonPane = new JPanel();
		buttonPane.add(selectButton);
		buttonPane.add(createButton);
		buttonPane.add(deleteButton);
		
		return buttonPane;
	}
	
	private class CardSetRenderer extends JLabel implements ListCellRenderer<CardSet> {
		private static final long serialVersionUID = 1L;

		public CardSetRenderer() {
			setOpaque(true);
		}
		
		@Override
		public Component getListCellRendererComponent(JList<? extends CardSet> list, 
				CardSet cardSet, int index, boolean isSelected, boolean cellHasFocus) {
			
			setText(getFormattedText(cardSet));
			
			if (isSelected) {
			    setBackground(list.getSelectionBackground());
			    setForeground(list.getSelectionForeground());
			} else {
			    setBackground(list.getBackground());
			    setForeground(list.getForeground());
			}
			
			return this;
		}

		
		private String getFormattedText(CardSet cardSet) {
			// refer to "https://way2java.com/swing/jlabel-multiline-text/"
			String text = "<html><h1>"+ cardSet.getTitle() + " (" + cardSet.getStudiedCount() + 
					"/" + cardSet.getCount() + ")</h1></html>";	
			
			return text;
		}
	}
}
