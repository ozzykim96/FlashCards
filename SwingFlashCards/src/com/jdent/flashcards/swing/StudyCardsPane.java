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
		GridBagConstraintsBuilder builder = new GridBagConstraintsBuilder(gbc);

		add(new JLabel("Question:"), 
				builder.build().grid(0, 0));
		add(questionScrollPane, 
				builder.build().grid(0, 1).weight(1, 0.5));
		add(yesNoPane, 
				builder.build().grid(0, 2));
		add(new JLabel("Answer:"), 
				builder.build().grid(0, 3));		
		add(answerScrollPane, 
				builder.build().grid(0, 4).weight(1, 0.5));
		add(nextPane, 
				builder.build().grid(0, 5));		
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
