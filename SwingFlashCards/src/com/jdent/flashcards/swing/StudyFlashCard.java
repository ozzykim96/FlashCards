package com.jdent.flashcards.swing;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jdent.flashcards.swing.ui.tool.GridBagConstraintsToolBuilder;

public class StudyFlashCard extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel questionLabel;
	private JLabel answerLabel;
	
	private String question;
	private String answer;
	
	private boolean showQuestion = true;
	private boolean showAnswer = true;
	
	public StudyFlashCard() {
		super(new GridBagLayout());
		
		createUI();
	}
	
	private void createUI() {
		questionLabel = createQuestionLabel();
		JScrollPane questionScrollPane = new JScrollPane(questionLabel);
		
		answerLabel = createAnswerLabel();
		JScrollPane answerScrollPane = new JScrollPane(answerLabel);
				
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
	}

	private JLabel createQuestionLabel() {
		JLabel label = new JLabel("Question");		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showQuestion = !showQuestion;
				updateLabels();
			}
		});
		
		return label;
	}

	private JLabel createAnswerLabel() {
		JLabel label = new JLabel("Answer");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showAnswer = !showAnswer;
				updateLabels();
			}
		});
		
		return label;
	}
	
	public void setNewQuestionAndAnswer(String question, String answer) {
		this.question = question;
		this.answer = answer;
	
		// hide answer
		showQuestion = true;
		showAnswer = false;
		
		updateLabels();
	}
		
	private void updateLabels() {
		if (showQuestion) {
			questionLabel.setText("<html><h1>" + question + "</h1></html>");
		}
		else {
			questionLabel.setText("<html><h1>( Question )</h1></html>");
		}
		
		if (showAnswer) {
			answerLabel.setText("<html><h1>" + answer + "</h1></html>");
		}
		else {
			answerLabel.setText("<html><h1>( Answer )</h1></html>");
		}
	}
}
