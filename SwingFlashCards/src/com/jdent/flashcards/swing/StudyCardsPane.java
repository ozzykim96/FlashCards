package com.jdent.flashcards.swing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.jdent.flashcards.card.Card;
import com.jdent.flashcards.card.CardSet;

public class StudyCardsPane extends JPanel implements CardUIContext {
	private static final long serialVersionUID = 1L;
	private CardSet cardSet;
	
	private JTextPane questionText;
	private JTextPane answerText;
	private String question;
	private String answer;
	
	public StudyCardsPane() {
		super(new GridBagLayout());
		
		createPane();
	}

	@Override
	public void setContext(Object obj) {
		cardSet = (CardSet)obj;
		cardSet.reset();
		
		Card card = cardSet.next();
		question = card.getName();
		answer = card.getDescription();
		
		questionText.setText(question);
		answerText.setText("");
	}
	
	private void createPane() {
		questionText = createTextPane();
		JScrollPane questionScrollPane = new JScrollPane(questionText);
		questionScrollPane.setPreferredSize(
				new Dimension(Constants.DEFAULT_LIST_WIDTH, Constants.DEFAULT_LIST_HEIGHT));
		
		JPanel yesNoPane = createYesNoButtonPane();

		answerText = createTextPane();
		JScrollPane answerScrollPane = new JScrollPane(answerText);
		answerScrollPane.setPreferredSize(
				new Dimension(Constants.DEFAULT_LIST_WIDTH, Constants.DEFAULT_LIST_HEIGHT));		
				
		JPanel nextPane = createControlButtonPane();

		GridBagConstraints gbc = FlashCardsUtil.getDefaultGridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Question:"), gbc);
		
		gbc.gridx = 0; 
		gbc.gridy = 1;
		gbc.weighty = 0.5;
		add(questionScrollPane, gbc);
		
		gbc.gridx = 0; 
		gbc.gridy = 2;
		gbc.weighty = 0;
		add(yesNoPane, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weighty = 0.5;		
		add(new JLabel("Answer:"), gbc);
		
		gbc.gridx = 0; 
		gbc.gridy = 4;
		gbc.weighty = 0;		
		add(answerScrollPane, gbc);
		gbc.gridx = 0; 
		gbc.gridy = 5;		
		add(nextPane, gbc);		
	}
	
	private JTextPane createTextPane() {
		JTextPane textPane = new JTextPane();
		textPane.setCaretPosition(0);
		textPane.setMargin(new Insets(5, 5, 5, 5));
		
		return textPane;
	}

	private JPanel createYesNoButtonPane() {
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				answerText.setText(answer);
			}
		});
		JButton noButton = new JButton("No");
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				answerText.setText(answer);
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Remember?"));
		panel.add(yesButton);
		panel.add(noButton);
		
		return panel;
	}
	
	private JPanel createControlButtonPane() {
		JButton nextButton = new JButton("Next Card");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Card card = cardSet.next();
				question = card.getName();
				answer = card.getDescription();
				
				questionText.setText(question);
				answerText.setText("");
			}
		});
		JButton previousMenuButton = new JButton("Previous Menu");
		previousMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPane(Constants.DISPLAYCARDS_PANE, null);
			}
		});	
		
		JPanel nextPane = new JPanel();
		nextPane.add(nextButton);
		nextPane.add(previousMenuButton);
		
		return nextPane;
	}
	
}
