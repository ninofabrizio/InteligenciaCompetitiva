package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainPackage.MainCrawler;

@SuppressWarnings("serial")
public class InputWindow extends JFrame implements ActionListener {

	private JPanel inputPanel;
	
	private JButton getInfo;
	
	private JTextField linkInput;
	private JTextField numberInput;
	
	private int maxLinksNum;
	
	private int DEFAULT_WIDTH = 500;
	private int DEFAULT_HEIGTH = 140;

	public InputWindow() {
		
		setTitle("Input Window");
		
		createFrameDimension();
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.GRAY);
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		getContentPane().add(inputPanel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("Enter maximum number of links to visit:");
		inputPanel.add(label, BorderLayout.BEFORE_FIRST_LINE);
		
		numberInput = new JTextField();
		inputPanel.add(numberInput, BorderLayout.CENTER);
		
		createExtraPanel();
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

	// Method for the panel containing the buttons and text field
	private void createExtraPanel() {

		JPanel extraPanel = new JPanel();
		extraPanel.setLayout(new BorderLayout());
		inputPanel.add(extraPanel, BorderLayout.SOUTH);

		JLabel label = new JLabel("Enter link to start crawling:");
		extraPanel.add(label, BorderLayout.BEFORE_FIRST_LINE);

		linkInput = new JTextField();
		extraPanel.add(linkInput, BorderLayout.CENTER);
		
		getInfo = new JButton("OK");
		extraPanel.add(getInfo, BorderLayout.AFTER_LAST_LINE);
		getInfo.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == getInfo && checkNumberField(numberInput.getText()) 
										&& checkLinkField(linkInput.getText()))
			MainCrawler.setInfo(maxLinksNum, linkInput.getText());
	}
	
	public void setTextEmpty() {
		
		numberInput.setText("");
		linkInput.setText("");
	}
	
	// Method to see if the text inside the link field fits
	private boolean checkLinkField(String text) {
		
		if(text != null
			&& text.equals("") == false
			&& text.contains(" ") == false
			&& text.contains("\t") == false)
			return true;
		else
			return false;
	}

	// Method to see if the text inside the number field fits
	private boolean checkNumberField(String str){
		
		if(str != null
			&& str.equals("") == false
			&& str.contains(" ") == false
			&& str.contains("\t") == false
			&& str.contains("-") == false
			&& str.equals("0") == false
			&& isNumeric(str))
			return true;
		else
			return false;
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