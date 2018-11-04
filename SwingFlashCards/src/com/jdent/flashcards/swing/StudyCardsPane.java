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
import com.jdent.flashcards.swing.ui.tool.GridBagConstraintsBuilder;

public class StudyCardsPane extends JPanel implements CardUIContext {
	private static final long serialVersionUID = 1L;
	private CardSet cardSet;
	private Card currentCard;
	
	private JTextPane questionText;
	private JTextPane answerText;
	
	public StudyCardsPane() {
		super(new GridBagLayout());
		
		createUI();
	}

	@Override
	public void setContext(Object obj) {
		cardSet = (CardSet)obj;
		cardSet.reset();
		
		currentCard = cardSet.next();
		
		setQuestionText(currentCard.getName());
		setAnswerText("");
	}
	
	@Override
	public void refreshContents() {
		
	}
	
	private void createUI() {
		questionText = createQuestionPane();
		JScrollPane questionScrollPane = new JScrollPane(questionText);
		
		JPanel yesNoPane = createYesNoButtonPane();

		answerText = createAnswerPane();
		JScrollPane answerScrollPane = new JScrollPane(answerText);
				
		JPanel nextPane = createControlButtonPane();

		GridBagConstraints gbc = FlashCardsUtil.getDefaultGridBagConstraints();
		GridBagConstraintsBuilder builder = new GridBagConstraintsBuilder(gbc);

		add(new JLabel("Question:"), 
				builder.build().grid(0, 0));
		add(questionScrollPane, 
				builder.build().grid(0, 1).weight(1, 0.3));
		add(yesNoPane, 
				builder.build().grid(0, 2));
		add(new JLabel("Answer:"), 
				builder.build().grid(0, 3));		
		add(answerScrollPane, 
				builder.build().grid(0, 4).weight(1, 0.7));
		add(nextPane, 
				builder.build().grid(0, 5));		
	}
	
	private JTextPane createQuestionPane() {
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font(getFont().getName(), Font.BOLD, 
				Constants.QUESTION_PANE_TEXT_FONT_SIZE));
		textPane.setCaretPosition(0);
		textPane.setEditable(false);
		
		return textPane;
	}
	
	private JTextPane createAnswerPane() {
		JTextPane textPane = new JTextPane();		
		textPane.setFont(new Font(getFont().getName(), Font.BOLD, 
				Constants.ANSWER_PANE_TEXT_FONT_SIZE));
		textPane.setCaretPosition(0);
		textPane.setEditable(false);
		
		return textPane;		
	}
	
	private void setQuestionText(String text) {
		questionText.setText(text);
	}
	
	private void setAnswerText(String text) {
		answerText.setText(text);
	}

	private JPanel createYesNoButtonPane() {
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setAnswerText(currentCard.getDescription());
				currentCard.setStudied(true);
			}
		});
		JButton noButton = new JButton("No");
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setAnswerText(currentCard.getDescription());
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(yesButton);
		panel.add(noButton);
		
		return panel;
	}
	
	private JPanel createControlButtonPane() {
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentCard = cardSet.nextStudy();
				if (currentCard != null) {
					setQuestionText(currentCard.getName());
					setAnswerText("");
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
