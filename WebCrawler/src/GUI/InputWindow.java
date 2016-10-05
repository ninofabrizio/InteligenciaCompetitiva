package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainPackage.MainReader;

public class InputWindow extends JFrame implements ActionListener {

	private JPanel inputPanel;
	
	private JButton getMaxLinksNum;
	
	private JTextField linkInput;
	
	private int maxLinksNum;
	
	private int DEFAULT_WIDTH = 300;
	private int DEFAULT_HEIGTH = 120;

	public InputWindow() {
		
		setTitle("Input Window");
		
		createFrameDimension();
		setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.GRAY);
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		getContentPane().add(inputPanel);
		
		JLabel label = new JLabel("Enter maximum number of links to visit:");
		inputPanel.add(label, BorderLayout.BEFORE_FIRST_LINE);
		
		getMaxLinksNum = new JButton("OK");
		inputPanel.add(getMaxLinksNum, BorderLayout.AFTER_LAST_LINE);
		getMaxLinksNum.addActionListener(this);
		
		linkInput = new JTextField();
		//linkInput.setText("Write link here");
		inputPanel.add(linkInput, BorderLayout.CENTER);
	}
	
	// Method to specify the dimensions of the frame
	private void createFrameDimension() {

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		int xPos = screenWidth / 2 - DEFAULT_WIDTH / 2;
		int yPos = screenHeight / 2 - DEFAULT_HEIGTH / 2;

		setBounds(xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setResizable(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == getMaxLinksNum
				&& linkInput.getText() != null
				&& linkInput.getText().equals("") == false
				&& linkInput.getText().contains(" ") == false
				&& linkInput.getText().contains("\t") == false
				&& linkInput.getText().contains("-") == false
				&& isNumeric(linkInput.getText())) {

				MainReader.setMaxLinksNum(maxLinksNum);
				//linkInput.setText("");
				
				setVisible(false);
			}
	}
	
	// Method to see if string is a valid number and retrieve it's value
	private boolean isNumeric(String str) {
		
	  try {
		  
		  maxLinksNum = Integer.parseInt(str);
	  } catch(NumberFormatException nfe) {
		  
	    return false;  
	  }  
	  
	  return true;  
	}
}