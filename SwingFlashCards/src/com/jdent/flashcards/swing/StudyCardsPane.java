package com.jdent.flashcards.swing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.jdent.flashcards.card.Card;
import com.jdent.flashcards.card.CardSet;
import com.jdent.flashcards.swing.ui.tool.GridBagConstraintsToolBuilder;

public class StudyCardsPane extends JPanel implements CardUIContext {
	private static final long serialVersionUID = 1L;
	private CardSet cardSet;
	private Card currentCard;
	
	private FlashCard flashCard;
	
	public StudyCardsPane() {
		super(new GridBagLayout());
		
		createUI();
	}

	@Override
	public void setContext(Object obj) {
		cardSet = (CardSet)obj;
		cardSet.reset();
		
		currentCard = cardSet.next();
		
		flashCard.setQuestionAndAnswer(currentCard.getName(), 
				currentCard.getDescription());
		flashCard.setShowQuestionAndAnswer(true, false);		
	}
	
	@Override
	public void refreshContents() {
		
	}
	
	private void createUI() {		
		flashCard = new FlashCard();
						
		JPanel nextPane = createControlButtonPane();

		GridBagConstraints gbc = FlashCardsUtil.getDefaultGridBagConstraints();
		GridBagConstraintsToolBuilder builder = new GridBagConstraintsToolBuilder(gbc);
		
		add(flashCard, builder.build().grid(0, 0).weight(1, 1));
		add(nextPane,
				builder.build().grid(0, 1));
	}
		
	private JPanel createControlButtonPane() {
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentCard = cardSet.nextStudy();
				if (currentCard != null) {
					flashCard.setQuestionAndAnswer(currentCard.getName(), 
							currentCard.getDescription());
					flashCard.setShowQuestionAndAnswer(true, false);
				}
				else {
					JOptionPane.showMessageDialog(null, "No cards to study.");
					FlashCardsFrame.getInstance().switchPane(Constants.DISPLAYCARDS_PANE, null);
				}
				
			}
		});
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPane(Constants.DISPLAYCARDS_PANE, null);
			}
		});	
		
		JPanel nextPane = new JPanel();
		nextPane.add(nextButton);
		nextPane.add(backButton);
		
		return nextPane;
	}
}
