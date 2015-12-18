package com.venediktVictoria.graphBuilder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.Cursor;

import javax.swing.JPanel;

//import com.sun.javafx.cursor.*;



import sun.security.util.SecurityConstants.AWT;

public class GBGraphPanel extends JPanel {
	private static double MAX_GRAPH_BOUNDS_DIFF = 1000000000;
	private static double MIN_GRAPH_BOUNDS_DIFF = 0.0001; 
	private static double INITIAL_LEFT_GRAPH_BOUND = -5;
	private static double INITIAL_RIGHT_GRAPH_BOUND = 5;
	
	private GBFunction func_;
	private double leftGraphBound_, rightGraphBound_;
	private double bottomGraphBound_, topGraphBound_;
	private Point2D.Double mid_;
	private Point2D.Double diff_;
	private boolean mouseDragging_;
	private Point dragStart_;
	private Point2D.Double dragStartMid_;
	/*
	private Point2D.Double pixToCoords(Point p) {
		return new Point2D.Double(mid_.x + 0.001*(p.x - getWidth()/2)*diff_.x/getWidth(), mid_.y + 0.001*(p.y - getHeight()/2)*diff_.y/getHeight());
	}*/
	private Point coordsToPix(double x, double y) {
		return new Point(getWidth()/2 + (int)((x - mid_.x)*((double)getWidth()/diff_.x)) , getHeight()/2 - (int)((y - mid_.y)*((double)getHeight()/diff_.y)));
	}
	
	public GBGraphPanel(int width, int height) {
		super();
		func_ = null;
		leftGraphBound_ = INITIAL_LEFT_GRAPH_BOUND;
		rightGraphBound_ = INITIAL_RIGHT_GRAPH_BOUND;
		diff_ = new Point2D.Double(10, 10*width/height);
		mid_ = new Point2D.Double(0, 0);
		bottomGraphBound_ = mid_.y - diff_.y/2;
		topGraphBound_ = mid_.y + diff_.y/2;
		mouseDragging_ = false;
		dragStart_ = new Point(0, 0);
		//setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseDragging_ = true;
				dragStart_.x = e.getX();
				dragStart_.y = getHeight() - e.getY();
				dragStartMid_ = (Point2D.Double) mid_.clone();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (mouseDragging_ == true) {
					mouseDragging_ = false;
					Point p = e.getPoint();
					p.setLocation(p.getX(), getHeight() - p.y);
					Point2D.Double newMid = new Point2D.Double(
							dragStartMid_.x - diff_.x*(p.x - dragStart_.x)/(double)getWidth(),
							dragStartMid_.y - diff_.y*(p.y - dragStart_.y)/(double)getHeight()
							);
					setMid(newMid);
					repaint();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {}
			@Override
			public void mouseDragged(MouseEvent e) {
				if (mouseDragging_ == true) {
					//mouseDragging_ = false;
					Point p = e.getPoint();
					p.setLocation(p.getX(), getHeight() - p.y);
					Point2D.Double newMid = new Point2D.Double(
							dragStartMid_.x - diff_.x*(p.x - dragStart_.x)/(double)getWidth(),
							dragStartMid_.y - diff_.y*(p.y - dragStart_.y)/(double)getHeight()
							);
					setMid(newMid);
					repaint();
				}
			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int clicks = e.getWheelRotation();
				final double clickZoom = 1.25;
				double finalZoom = Math.pow(clickZoom, clicks);
				zoomGraph(finalZoom);
				//setGraphBounds(mid_.x - diff_.x*finalZoom*0.5, mid_.x + diff_.x*finalZoom*0.5);
				repaint();
			}
		});
	}
	public void setGraphBounds(Double left, Double right) {
		if (left == null && right == null) {
			leftGraphBound_ = INITIAL_LEFT_GRAPH_BOUND;
			rightGraphBound_ = INITIAL_RIGHT_GRAPH_BOUND;
			mid_.x = 0;
		}
		else if (right == null) {
			leftGraphBound_ = left.doubleValue() - 5;
			rightGraphBound_ = left.doubleValue() + 5;
			mid_.x = left.doubleValue();
		}
		else if (left == null) {
			leftGraphBound_ = right.doubleValue() - 5;
			rightGraphBound_ = right.doubleValue() + 5;
			mid_.x = right.doubleValue();
		}
		else {
			leftGraphBound_ = Math.min(left.doubleValue(), right.doubleValue());
			rightGraphBound_ = Math.max(left.doubleValue(), right.doubleValue());
			//mid_.x = (leftGraphBound_ + rightGraphBound_) / 2;
			diff_.x = rightGraphBound_ - leftGraphBound_;
			if (diff_.x > MAX_GRAPH_BOUNDS_DIFF || diff_.x < MIN_GRAPH_BOUNDS_DIFF) {
				double scale = (diff_.x > MAX_GRAPH_BOUNDS_DIFF) ? MAX_GRAPH_BOUNDS_DIFF / diff_.x : MIN_GRAPH_BOUNDS_DIFF / diff_.x;
				leftGraphBound_ = mid_.x + scale*(leftGraphBound_ - mid_.x);
				rightGraphBound_ = mid_.x + scale*(rightGraphBound_ - mid_.x);
				diff_.x = rightGraphBound_ - leftGraphBound_; //for correct calculation of bottom and top bounds
			}
		}
		diff_.y = diff_.x*getHeight() / getWidth();
		bottomGraphBound_ = mid_.y - diff_.y/2;
		topGraphBound_ = mid_.y + diff_.y/2;
	}
	public void setMid(Point2D.Double p) {
		mid_.x = p.x;
		mid_.y = p.y;
		leftGraphBound_ = mid_.x - diff_.x/2;
		rightGraphBound_ = leftGraphBound_ + diff_.x;
		bottomGraphBound_ = mid_.y - diff_.y/2;
		topGraphBound_ = bottomGraphBound_ + diff_.y;
	}
	public void zoomGraph(double times) {
		diff_.x *= times;
		diff_.y *= times;
		leftGraphBound_ = mid_.x - diff_.x/2;
		rightGraphBound_ = mid_.x + diff_.x/2;
		bottomGraphBound_ = mid_.y - diff_.y/2;
		topGraphBound_ = mid_.y + diff_.y/2;
	}
	public void dragGraph() {
		//TODO
	}
	public void setFunction(GBFunction func) { 
		func_ = func;
		mid_.x = 0;
		mid_.y = 0;
	}
//----------------------------------------	
	public double getLeftGraphBound() {
		return leftGraphBound_;
	}
	public double getRightGraphBound() {
		return rightGraphBound_;
	}
	public double getTopGraphBound() {
		return topGraphBound_;
	}
	public double getBottomGraphBound() {
		return bottomGraphBound_;
	}
//----------------------------------------	
	@Override
	public void paintComponent(Graphics g) {
		final double pixelsPerStep = 0.1;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.drawLine(0, 0, getWidth(), 0);
		g2d.setStroke(new BasicStroke(2));
		if (bottomGraphBound_ < 0 && topGraphBound_ > 0) {
			Point xAxisBeg = coordsToPix(leftGraphBound_, 0);
			Point xAxisEnd = coordsToPix(rightGraphBound_, 0);
			g2d.drawLine(xAxisBeg.x, xAxisBeg.y, xAxisEnd.x, xAxisEnd.y); 	//draw X-axis
		}	
		if (leftGraphBound_ < 0 && rightGraphBound_ > 0) {
			Point yAxisBeg = coordsToPix(0, bottomGraphBound_);
			Point yAxisEnd = coordsToPix(0, topGraphBound_);
			g2d.drawLine(yAxisBeg.x, yAxisBeg.y, yAxisEnd.x, yAxisEnd.y);	//draw Y-axis
		}
		g2d.setStroke(new BasicStroke(1));
		if (func_ != null) {
			final double step = pixelsPerStep * diff_.x / getWidth();
			g.setColor(Color.BLACK);
			for (double d = leftGraphBound_; d < rightGraphBound_; d += step) {
				if (!func_.isSuspiciousForHavingVerticalAsymptoteIn(d, d + step) && !func_.hasVerticalAsymptoteIn(d, d + step)) {
					Point beg = coordsToPix(d, func_.value(d));
					Point end = coordsToPix(d + step, func_.value(d+step));
					g.drawLine(beg.x, beg.y, end.x, end.y);
				}
			}
		}
	}
}
