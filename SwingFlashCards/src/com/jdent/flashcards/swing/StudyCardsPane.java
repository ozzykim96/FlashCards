package com.jdent.flashcards.swing;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jdent.flashcards.card.Card;
import com.jdent.flashcards.card.CardSet;
import com.jdent.flashcards.swing.ui.tool.GridBagConstraintsToolBuilder;

public class StudyCardsPane extends JPanel implements CardUIContext {
	private static final long serialVersionUID = 1L;
	private CardSet cardSet;
	private Card currentCard;
	
	private StudyFlashCard flashCard;
	
	public StudyCardsPane() {
		super(new GridBagLayout());
		
		createUI();
	}

	private void createUI() {		
		flashCard = new StudyFlashCard();
		JPanel controlPanel = createControlPanel();

		GridBagConstraintsToolBuilder builder = 
				FlashCardsUtil.makeDefaultGridBagConstraintsBuilder();
		
		add(flashCard, builder.build().grid(0, 0).weight(1, 1));
		add(controlPanel,
				builder.build().grid(0, 1));
	}
		
	private JPanel createControlPanel() {
		JButton checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentCard.setStudied(true);
				currentCard = cardSet.nextStudy();
				if (currentCard != null) {
					flashCard.setNewQuestionAndAnswer(currentCard.getName(), 
							currentCard.getDescription());
				}
				else {
					JOptionPane.showMessageDialog(null, "No cards to study.");
					FlashCardsFrame.getInstance().switchPaneTo(Constants.DISPLAYCARDS_PANE, null);
				}				
			}
		});
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentCard = cardSet.nextStudy();
				if (currentCard != null) {
					flashCard.setNewQuestionAndAnswer(currentCard.getName(), 
							currentCard.getDescription());
				}
				else {
					JOptionPane.showMessageDialog(null, "No cards to study.");
					FlashCardsFrame.getInstance().switchPaneTo(Constants.DISPLAYCARDS_PANE, null);
				}				
			}
		});
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPaneTo(Constants.DISPLAYCARDS_PANE, null);
			}
		});	
		
		JPanel pnl = new JPanel();
		pnl.add(checkButton);
		pnl.add(nextButton);
		pnl.add(backButton);
		
		return pnl;
	}

	@Override
	public void setContext(Object obj) {
		cardSet = (CardSet)obj;
		cardSet.reset();
		currentCard = cardSet.nextStudy();
		
		flashCard.setNewQuestionAndAnswer(currentCard.getName(), 
				currentCard.getDescription());
	}
	
	@Override
	public void refreshContents() {
		
	}
	
}
