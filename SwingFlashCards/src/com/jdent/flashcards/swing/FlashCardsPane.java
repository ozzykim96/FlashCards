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
import javax.swing.JTextArea;
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
		
		LOGGER.info("create flashcards pane");
		
		// create UI pane
		createPane();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {

	}
	
	@Override
	public void setContext(Object obj) {
		cardSetList = (CardSetList)obj;
		
		loadContents();
	}
	
	private void createPane() {
		// create the list and put it in a scroll pane.
		list = createDisplayCardsetList();
		
		// scroll pane
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setPreferredSize(
				new Dimension(Constants.DEFAULT_LIST_WIDTH, Constants.DEFAULT_LIST_HEIGHT));
		
		// create a panel for buttons
		JPanel buttonPane = createControlButtonPane();

		// add to pane
		GridBagConstraints gbc = FlashCardsUtil.getDefaultGridBagConstraints();
		GridBagConstraintsBuilder builder = new GridBagConstraintsBuilder(gbc);
		
		add(new JLabel("Select cards:"), 
				builder.reset().grid(0, 0).get());
		add(listScrollPane, 
				builder.reset().grid(0, 1).weight(1.0, 1.0).get());
		add(buttonPane, 
				builder.reset().grid(0, 2).get());
	}
	
	private void loadContents() {
		// remove all elements
		listModel.removeAllElements();
		
		// add cards
		for (int i = 0; i < cardSetList.getList().size(); i++) {
			CardSet cards = cardSetList.getCards(i);
			listModel.addElement(cards.getTitle());
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
		list.setVisibleRowCount(5);
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