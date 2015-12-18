package com.venediktVictoria.graphBuilder;

//import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.geom.Point2D;

import com.venediktVictoria.graphBuilder.exceptions.GBParseException;

public class GBMainFrame extends JFrame {
	private static final int DEFAULT_MIN_WIDTH = 300;
	private static final int DRFAULT_MIN_HEIGHT = 200;
	private static final int DEFAULT_GAP = 10;
	private static final int DEFAULT_CONTROL_PANEL_HEIGHT = 85;
	private static final Color DEFAULT_CONTROL_PANEL_COLOR = new Color(205, 205, 205);
	private static final Color DEFAULT_GRAPH_PANEL_COLOR = new Color(255, 255, 255);

	private int columnWidth_;		//Width of column in JTextField (in pixels)
	private JTextField textField_;
	private JButton buildButton_;
	private JLabel labelFX_;
	private JLabel errLabel_;
	private JLabel labelA_, labelB_;
	private JTextField textFieldA_, textFieldB_;
	private JPanel controlPanel_;
	private GBGraphPanel graphPanel_;

	public GBMainFrame(int width, int height, String title) {
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(DEFAULT_MIN_WIDTH, DRFAULT_MIN_HEIGHT));
		setTitle(title);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		controlPanel_ = new JPanel();
		controlPanel_.setBackground(DEFAULT_CONTROL_PANEL_COLOR);
		controlPanel_.setSize(getWidth(), DEFAULT_CONTROL_PANEL_HEIGHT);
		graphPanel_ = new GBGraphPanel(getWidth(), getHeight() - controlPanel_.getHeight());
		//graphPanel_.setSize(getWidth(), getHeight() - controlPanel_.getHeight());
		graphPanel_.setLocation(0, DEFAULT_CONTROL_PANEL_HEIGHT);
		graphPanel_.setBackground(DEFAULT_GRAPH_PANEL_COLOR);
		labelFX_ = new JLabel("f(x) = ");
		textField_ = new JTextField(50);
		buildButton_ = new JButton("Build");
		errLabel_ = new JLabel();
		labelA_ = new JLabel("from");
		textFieldA_ = new JTextField(4);
		labelB_ = new JLabel("to");
		textFieldB_ = new JTextField(4);
		GroupLayout layout = new GroupLayout(controlPanel_);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(DEFAULT_GAP)
					.addComponent(labelFX_)
					.addComponent(textField_, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(buildButton_)
					.addGap(DEFAULT_GAP)
				)
				.addGroup(layout.createSequentialGroup()
					.addGap(DEFAULT_GAP)
					.addComponent(errLabel_, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(labelA_)
					.addComponent(textFieldA_, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(labelB_)
					.addComponent(textFieldB_, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(DEFAULT_GAP)
				)
		);
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGap(DEFAULT_GAP)
				.addGroup(layout.createParallelGroup()
					.addComponent(labelFX_)
					.addComponent(textField_, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(buildButton_)
				)
				.addGap(DEFAULT_GAP)
				.addGroup(layout.createParallelGroup()
					.addComponent(errLabel_)
					.addComponent(labelA_)
					.addComponent(textFieldA_, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(labelB_)
					.addComponent(textFieldB_, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				) 
				.addGap(DEFAULT_GAP)
		);
		controlPanel_.setLayout(layout);
		add(controlPanel_);
		add(graphPanel_);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
		columnWidth_ = textField_.getWidth() / textField_.getColumns();
		if (columnWidth_ == 0)
			columnWidth_ = 5;
		
		KeyListener textFieldKeyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') { //Build graph if 'Enter' key pressed 
					obtainGraphData(); 
					graphPanel_.repaint();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
		};
		textField_.addKeyListener(textFieldKeyListener);
		textFieldA_.addKeyListener(textFieldKeyListener);
		textFieldB_.addKeyListener(textFieldKeyListener);
		
		buildButton_.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { //Build graph on mouse click
				obtainGraphData(); 
				graphPanel_.repaint();
				graphPanel_.grabFocus();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				realignItems();
				graphPanel_.setGraphBounds(graphPanel_.getLeftGraphBound(), graphPanel_.getRightGraphBound());
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
	}
	private void realignItems() {
		controlPanel_.setSize(getContentPane().getWidth(), DEFAULT_CONTROL_PANEL_HEIGHT);
		graphPanel_.setSize(getContentPane().getWidth(), getContentPane().getHeight() - controlPanel_.getHeight());
	}
	private void obtainGraphData() {
		GBFunction func = null;
		Double a = null, b = null;
		try {
			errLabel_.setText("");
			func = new GBFunction(textField_.getText()); //Parse text field
			a = new Double(textFieldA_.getText());
			try {
				b = new Double(textFieldB_.getText());
			}
			catch (NumberFormatException exc) {
				if (textFieldB_.getText().length() > 0) {
					errLabel_.setText("Incorrect numeric input");
					if (!textFieldB_.hasFocus())
						textFieldB_.grabFocus();
				}
			}
		}
		catch (NumberFormatException exc) {
			if (textFieldA_.getText().length() > 0) {
				errLabel_.setText("Incorrect numeric input");
				if (!textFieldA_.hasFocus())
					textFieldA_.grabFocus();
			}
		}
		catch (GBParseException exc) {	
			errLabel_.setText(exc.getMessage()); //Output error message to label
			if (!textField_.hasFocus()) 		 //Select text field for mistake correction
				textField_.grabFocus();
			if (exc.token() != null)
				textField_.setCaretPosition(exc.token().pos() + exc.token().length()); //Move cursor in text field to position, where parse error occured
		}
		graphPanel_.setFunction(func);
		graphPanel_.setGraphBounds(a, b);
	}
}
