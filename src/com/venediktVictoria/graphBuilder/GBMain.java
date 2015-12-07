package com.venediktVictoria.graphBuilder;

import javax.swing.SwingUtilities;

public class GBMain implements Runnable {
	private static final String VERSION = "0.1";
	public static void main(String args[]) {
		GBMain  gb = new GBMain();
		SwingUtilities.invokeLater(gb);
	}
	public void run() {
		GBMainFrame frame = new GBMainFrame(800, 600, "Graph Builder v " + VERSION);
		/*
		 * Just for debug
		 *
		GBFunction f = null;
		try {
			f = new GBFunction("cos x/tg x");
			double a = -.001, b = .001;
			boolean boo = f.isSuspiciousForHavingVerticalAsymptoteIn(a, b);
			System.out.println(String.valueOf(boo));
			if (boo)
				System.out.println(String.valueOf(f.hasVerticalAsymptoteIn(a, b)));
		}
		catch (Exception e) {
			System.out.println("Error((9");
		}
		 */ 
	}
}
