package com.venediktVictoria.graphBuilder;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.venediktVictoria.graphBuilder.exceptions.GBParseException;

public class GBMainFrame extends JFrame {
	//private GBFunction function_;
	private JTextField textField_;
	private JButton buttonBuild_;
	private JLabel labelFX_;
	private JLabel errLabel_;
	
	private void realignItems() {
		textField_.setSize(getWidth() - 60, 26);
		buttonBuild_.setLocation(getWidth() - 60, 46);
		errLabel_.setSize(getWidth() - 20 - buttonBuild_.getWidth(), 20);
	}
	private void buildGraph() {
		GBFunction func = null;
		try {
			if (textField_.getText().length() == 0) //В текстовом поле пусто
				errLabel_.setText("There is no expression in text field");
			else {
				func = new GBFunction(textField_.getText()); //Парсим текстовое поле
				errLabel_.setText("f(0) = " + String.valueOf(func.value(0)) + "     f(1) = " + String.valueOf(func.value(1))/* + "     f(" + String.valueOf(func.bisect(-1, 1, 0.00000001)) + ") = 0"*/);
			}
		}
		catch (GBParseException exc) {		
			errLabel_.setText(exc.getMessage()); //Вывод сообщения об ошибке
			if (!textField_.hasFocus()) 		 //Выделить текстовое поле для исправления ошибки
				textField_.grabFocus();
			textField_.setCaretPosition(exc.token().pos()); //Перевести курсор textField_ в место ошибки
		}
		if (func != null) { //Функция задана корректно, проблем при чтении не возникло
			/* Построение графика...
			 * Чтобы узнать значение функции в точке x, использовать func.value(x) 
			 * ...
			 * */
			
		}
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
		textField_.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') //Если нажали Enter, рисуем график
					buildGraph();
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		buttonBuild_ = new JButton("Build");
		buttonBuild_.setSize(50, 20);
		buttonBuild_.setLocation(width - 60, 46);
		buttonBuild_.setVisible(true);
		buttonBuild_.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buildGraph();
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
}
