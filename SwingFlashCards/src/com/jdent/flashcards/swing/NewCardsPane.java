package com.jdent.flashcards.swing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	
	private void createUI() {
		questionText = createTextPane();
		JScrollPane questionScrollPane = new JScrollPane(questionText);
		questionScrollPane.setPreferredSize(
				new Dimension(Constants.DEFAULT_LIST_WIDTH, Constants.DEFAULT_LIST_HEIGHT));
		
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
		add(new JLabel("Answer:"), 
				builder.build().grid(0, 2));
		
		add(answerScrollPane, 
				builder.build().grid(0, 3).weight(1, 0.5));
		add(nextPane, 
				builder.build().grid(0, 4));	
	}
	
	private JTextPane createTextPane() {
		JTextPane textPane = new JTextPane();
		textPane.setCaretPosition(0);
		textPane.setMargin(new Insets(5, 5, 5, 5));
		
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
		
		JButton previousMenuButton = new JButton("Previous Menu");
		previousMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlashCardsFrame.getInstance().switchPane(Constants.DISPLAYCARDS_PANE, cardSet);
			}
		});	

		JPanel nextPane = new JPanel();
		nextPane.add(saveButton);
		nextPane.add(previousMenuButton);
		
		return nextPane;
	}	
}
