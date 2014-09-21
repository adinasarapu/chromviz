package edu.ucsd.genome.chromviz.testExamples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DemoPopupMenu extends JApplet {

	private static final long serialVersionUID = 1L;
	JPopupMenu popupMenu;
	JMenuItem saveItem, cutItem, copyItem, pasteItem, helpItem;
	JTextArea textArea;
	Container container;

	public void init() {

		// 1. Get a handle on the frame's content pane.

		container = this.getContentPane();

		// 2. Create and add the text area to the content pane.

		textArea = new JTextArea("Click the mouse right button inside "
				+ "\nthe frame.");
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

		container.add(textArea);

		// 3. Create a pop-up menu.

		popupMenu = new JPopupMenu("Test Popup Menu");

		// 4. Create menu items and add them to the pop-up menu.
		// Also add some separators as shown in the code.

		saveItem = new JMenuItem("Save");
		popupMenu.add(saveItem);

		popupMenu.addSeparator();

		cutItem = new JMenuItem("Cut");
		popupMenu.add(cutItem);

		copyItem = new JMenuItem("Copy");
		popupMenu.add(copyItem);

		pasteItem = new JMenuItem("Paste");
		popupMenu.add(pasteItem);

		popupMenu.addSeparator();

		helpItem = new JMenuItem("Help");
		popupMenu.add(helpItem);

		// 5. Add the mouse listener to the content pane.

		PopupMenuListener pml = new PopupMenuListener();
		textArea.addMouseListener(pml);
	}

	// 7. Mouse listener class.

	class PopupMenuListener extends MouseAdapter {
		public void mousePressed(MouseEvent me) {
			showPopup(me);
		}

		public void mouseReleased(MouseEvent me) {
			showPopup(me);
		}

		private void showPopup(MouseEvent me) {
			if (me.isPopupTrigger()) {
				popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}
		}
	}
}
