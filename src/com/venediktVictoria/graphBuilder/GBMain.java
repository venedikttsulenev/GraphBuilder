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
	}
}
