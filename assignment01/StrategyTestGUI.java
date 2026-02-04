package assignment01;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class StrategyTestGUI {
	private JFrame frame = new JFrame("Encoding, Decoding Test");
	private JTextArea source = new JTextArea(5, 30); // 5 rows, 30 columns
	private JTextArea encode = new JTextArea(5, 30); // 5 rows, 30 columns
	private JTextArea decode = new JTextArea(5, 30); // 5 rows, 30 columns
	private final Font FONT = new Font("SansSerif", Font.PLAIN, 18);
	private Code version1 = new Version1();
	private Code version2 = new Version2();
	private Code version3 = new Version3();
	private Code strategy;
	// These next fields are here so they are accessible to a lambda expression embedded in one of the methods 
	private JComboBox<String> combo = new JComboBox<>(
			new String[] {"Choose Code Strategy", "Strategy 1", "Strategy 2", "Strategy 3"});
	private JButton button = new JButton("Encode/Decode Test");

	private void makeTextArea(String label, JPanel panel, JTextArea textArea) {
		textArea.setFont(FONT);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		TitledBorder border = BorderFactory.createTitledBorder(label);
		border.setTitleFont(FONT);
		textArea.setBorder(border);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT); // Align properly in BoxLayout
		panel.add(scrollPane);
	}

	private void makeEncodeButtons(JPanel panel) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,0));
		button.setEnabled(false);
		button.setFont(FONT);
		button.addActionListener(e -> {
			encode.setText(strategy.encode(source.getText()));
			decode.setText(strategy.decode(encode.getText()));
		});		
		JComboBox<String> combo = new JComboBox<>(
				new String[] {"Choose Code Strategy", "Strategy 1", "Strategy 2", "Strategy 3"});
		combo.setFont(FONT);
		combo.addActionListener(e -> {
			switch(combo.getSelectedIndex()) {
			case 0: 
				JOptionPane.showMessageDialog(frame, "Must select a strategy");
				button.setEnabled(false);
				break;
			case 1:
				strategy = version1;
				button.setEnabled(true);
				break;
			case 2:
				strategy = version2;
				button.setEnabled(true);
				break;
			case 3:
				strategy = version3;
				button.setEnabled(true);
			}
		});
		buttonPanel.add(combo);
		buttonPanel.add(button);
		panel.add(buttonPanel);
	}

	private void createAndShowGUI() {
		// Create the main frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);

		// Create a panel with vertical BoxLayout
		JPanel panel = new JPanel();
		panel.setFont(FONT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Create and add three JTextAreas with scroll panes
		makeTextArea("Source", panel, source);
		panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between areas				
		makeEncodeButtons(panel);
		panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between areas				
		makeTextArea("Encoded", panel, encode);
		panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between areas				
		makeTextArea("Decoded", panel, decode);
		panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between areas				

		// Add panel to frame
		frame.getContentPane().add(panel);

		// Center and display
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Always start Swing apps on the Event Dispatch Thread
		SwingUtilities.invokeLater(() -> {
			new StrategyTestGUI().createAndShowGUI();
		});
	}
}