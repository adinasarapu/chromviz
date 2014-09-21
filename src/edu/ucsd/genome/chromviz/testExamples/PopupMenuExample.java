package edu.ucsd.genome.chromviz.testExamples;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class PopupMenuExample extends JPanel {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	public JPopupMenu popup;
	int x = 10, y = 20;

	public PopupMenuExample() {
		popup = new JPopupMenu();
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Popup menu item ["
						+ event.getActionCommand() + "] was pressed.");
			}
		};
		/*
		 * public void setMenuLocation(int x, int y) { this.x = x; this.y = y; }
		 */

		JMenuItem item;
		popup.add(item = new JMenuItem("Left"));// , new ImageIcon("1.gif")));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		popup.add(item = new JMenuItem("Center"));// , new ImageIcon("2.gif")));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		popup.add(item = new JMenuItem("Right"));// , new ImageIcon("3.gif")));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		popup.add(item = new JMenuItem("Full"));// , new ImageIcon("4.gif")));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		popup.addSeparator();
		popup.add(item = new JMenuItem("Settings . . ."));
		item.addActionListener(menuListener);
		// popup.setLocation(10,200);
		popup.setLabel("Justification");
		popup.setBorder(new BevelBorder(BevelBorder.RAISED));
		popup.addPopupMenuListener(new PopupPrintListener());

		addMouseListener(new MousePopupListener());
	}

	Line2D.Double l = new Line2D.Double(10, 12, 34, 12);
	Point p = null;

	// An inner class to check whether mouse events are the popup trigger
	public class MousePopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {

			checkPopup(e);

		}

		public void mouseClicked(MouseEvent e) {
			p = e.getPoint();
			// if(l.contains(p)){

			checkPopup(e);
			// }
		}

		public void mouseReleased(MouseEvent e) {
			checkPopup(e);
		}

		private void checkPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popup.show(PopupMenuExample.this, e.getX(), e.getY());
				popup.setToolTipText("popup menu");
			}
		}
	}

	// An inner class to show when popup events occur
	public class PopupPrintListener implements PopupMenuListener {

		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			System.out.println("Popup menu will be visible!");
		}

		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			System.out.println("Popup menu will be invisible!");
		}

		public void popupMenuCanceled(PopupMenuEvent e) {
			System.out.println("Popup menu is hidden!");
		}
	}

	public static void main(String s[]) {
		JFrame frame = new JFrame("Popup Menu Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new PopupMenuExample());
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
