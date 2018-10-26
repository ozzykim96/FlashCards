package com.jdent.flashcards.swing;

import java.awt.Dimension;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jdent.flashcards.card.CardSet;
import com.jdent.flashcards.card.CardSetList;

public class FlashCardsPane extends JPanel 
		implements ListSelectionListener, CardUIContext {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FlashCardsPane.class.getName());
	
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private CardSetList cardSetList;

	public FlashCardsPane() {
		super(new GridBagLayout());
		
		LOGGER.info("create FlashCards pane.");
		
		createUI();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {

	}
	
	@Override
	public void setContext(Object obj) {
		cardSetList = (CardSetList)obj;
		
		loadContents();
	}
	
	private void createUI() {
		// create the list and put it in a scroll pane.
		list = createDisplayCardsetList();
		
		// scroll pane
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setPreferredSize(
				new Dimension(Constants.DEFAULT_LIST_WIDTH, Constants.DEFAULT_LIST_HEIGHT));
		
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
			String name = cards.getTitle() + "(" + cards.getStudiedCount() + 
					"/" + cards.getCount() + ")";
			listModel.addElement(name);
		}		
	}
	
	@SuppressWarnings("unchecked")
	private JList<String> createDisplayCardsetList() {
		listModel = new DefaultListModel<>();
						
		// create the list and put it in a scroll pane.
		JList<String> list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(30);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<String> list = (JList<String>)evt.getSource();					
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					
					if (index >= 0) {
						CardSet cards = cardSetList.getCards(index);
						
						FlashCardsFrame.getInstance()
								.switchPane(Constants.DISPLAYCARDS_PANE, cards);						
					}
				}
			}
		});
		
		return list;
	}
	
	private JPanel createControlButtonPane() {
		// create buttons
		JButton createButton = new JButton("Create a set of cards");
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
		JButton deleteButton = new JButton("Delete a set of cards");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				
				if (index >= 0) {
					int dialogResult = JOptionPane.showConfirmDialog(
							null, "Would you like to delete this cards?", "Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_NO_OPTION) {
						cardSetList.remove(index);	
						
						loadContents();
					}
				}
			}
		});
		
		// create a panel for buttons
		JPanel buttonPane = new JPanel();
		buttonPane.add(createButton);
		buttonPane.add(deleteButton);
		
		return buttonPane;
	}
}
