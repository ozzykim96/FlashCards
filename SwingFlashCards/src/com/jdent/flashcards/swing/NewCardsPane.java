package com.jdent.flashcards.swing;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.jdent.flashcards.card.CardSet;
import com.jdent.flashcards.swing.ui.tool.GridBagConstraintsToolBuilder;

public class NewCardsPane extends JPanel implements CardUIContext {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(NewCardsPane.class.getName());
	
	private JTextPane questionText;
	private JTextPane answerText;
	private CardSet cardSet;
	
	public NewCardsPane() {
		super(new GridBagLayout());
		LOGGER.info("create NewCards pane.");
		
		createUI();
	}
	
	@Override
	public void setContext(Object obj) {
		cardSet = (CardSet)obj;
	}
	
	@Override
	public void refreshContents() {
		
	}
	
	private void createUI() {
		questionText = createQuestionPane(); //createTextPane();
		JScrollPane questionScrollPane = new JScrollPane(questionText);
		
		answerText = createAnswerPane(); //createTextPane();
		JScrollPane answerScrollPane = new JScrollPane(answerText);
		
		JPanel nextPane = createControlButtonPane();

		GridBagConstraintsToolBuilder builder = 
				FlashCardsUtil.makeDefaultGridBagConstraintsBuilder();
		
		add(new JLabel("Question:"), 
		builder.build().grid(0, 0));
		
		add(questionScrollPane, 
				builder.build().grid(0, 1).weight(1, 0.3));
		add(new JLabel("Answer:"), 
				builder.build().grid(0, 2));
		
		add(answerScrollPane, 
				builder.build().grid(0, 3).weight(1, 0.7));
		add(nextPane, 
				builder.build().grid(0, 4));	
	}
	
	private JTextPane createQuestionPane() {
		JTextPane textPane = new JTextPane();
		textPane.setCaretPosition(0);
		
		textPane.setFont(new Font(getFont().getName(), Font.BOLD, 
				Constants.QUESTION_PANE_TEXT_FONT_SIZE));
		return textPane;
	}
	
	private JTextPane createAnswerPane() {
		JTextPane textPane = new JTextPane();
		textPane.setCaretPosition(0);
		
		textPane.setFont(new Font(getFont().getName(), Font.BOLD, 
				Constants.ANSWER_PANE_TEXT_FONT_SIZE));
		return textPane;		
	}
	
	private JPanel createControlButtonPane() {
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String question = questionText.getText();
				String answer = answerText.getText();
				
				LOGGER.log(Level.FINE, "add {0}:{1} to CardSet", new Object[] {question, answer});
				
				cardSet.add(question, answer);
				
				questionText.setText("");
				answerText.setText("");
			}
		});
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPane(Constants.DISPLAYCARDS_PANE, cardSet);
			}
		});	

		JPanel nextPane = new JPanel();
		nextPane.add(saveButton);
		nextPane.add(backButton);
		
		return nextPane;
	}	
}
