package com.venediktVictoria.graphBuilder;

//import java.awt.event.ActionEvent;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GBMainFrame extends JFrame {
	private GBFunction function_;
	private JTextField textField_;
	private JButton buttonBuild_;
	private JLabel labelFX_;
	private JLabel errLabel_;
	
	private void realignItems() {
		textField_.setSize(getWidth() - 60, 26);
		buttonBuild_.setLocation(getWidth() - 60, 46);
		errLabel_.setSize(getWidth() - 20 - buttonBuild_.getWidth(), 20);
	}
	public GBMainFrame(int width, int height, String title) {
		setSize(width, height);
		setLocationRelativeTo(null);
		setTitle(title);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		textField_ = new JTextField();
		textField_.setSize(width - 60, 26);
		textField_.setLocation(50, 10);
		textField_.setVisible(true);
		buttonBuild_ = new JButton("Build");
		buttonBuild_.setSize(50, 20);
		buttonBuild_.setLocation(width - 60, 46);
		buttonBuild_.setVisible(true);
		buttonBuild_.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					function_ = new GBFunction(textField_.getText());
					errLabel_.setText("f(0) = " + String.valueOf(function_.value(0)) + "     f(1) = " + String.valueOf(function_.value(1)));
				}
				catch (Exception exc) {
					errLabel_.setText(exc.getMessage());
				}
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
		labelFX_ = new JLabel("f(x) = ");
		labelFX_.setLocation(10, 12);
		labelFX_.setSize(40, 20);
		labelFX_.setVisible(true);
		errLabel_ = new JLabel();
		errLabel_.setSize(width - 20 - buttonBuild_.getWidth(), 20);
		errLabel_.setLocation(10, 46);
		add(textField_);
		add(buttonBuild_);
		add(labelFX_);
		add(errLabel_);
		setVisible(true);
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				realignItems();
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
	}
	public static void main(String args[]) {
		final String VERSION = "0.0";
		GBMainFrame mainFrame = new GBMainFrame(800, 600, "Graph Builder " + VERSION);
	}
}
