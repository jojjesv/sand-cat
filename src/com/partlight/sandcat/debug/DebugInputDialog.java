package com.partlight.sandcat.debug;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class DebugInputDialog extends JDialog {

	public static String		lastInput = "";
	public static String		input = "";
	private final JTextField	tfInput;
	private final BoxLayout		blLayout;

	public DebugInputDialog() {
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.tfInput = new JTextField(25);
		this.tfInput.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					DebugInputDialog.this.confirmInput(DebugInputDialog.this.tfInput.getText());
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		final Container content = this.getContentPane();

		this.blLayout = new BoxLayout(content, BoxLayout.Y_AXIS);
		content.setLayout(this.blLayout);
		content.add(this.tfInput, BorderLayout.CENTER);
		this.pack();

		this.tfInput.setFocusable(true);
	}

	public void confirmInput(String input) {
		DebugInputDialog.lastInput = input;
		this.setVisible(false);
	}
}
