package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressWindow extends JFrame {

	private JPanel progressPanel;
	
	JProgressBar progBar;
	private int barMinimum;
	private int barMaximum;
	
	private int DEFAULT_WIDTH = 300;
	private int DEFAULT_HEIGTH = 80;
	
	public ProgressWindow(int max) {
		
		barMinimum = 0;
		barMaximum = max;
		
		setTitle("Crawl in progress");
		
		createFrameDimension();
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.GRAY);
		
		progressPanel = new JPanel();
		progressPanel.setLayout(new BorderLayout());
		setContentPane(progressPanel);
		
		JLabel label = new JLabel("Crawling...");
		progressPanel.add(label, BorderLayout.NORTH);
		
	    progBar = new JProgressBar();
	    progBar.setMinimum(barMinimum);
	    progBar.setMaximum(barMaximum);
	    progBar.setBackground(Color.BLUE);
	    progressPanel.add(progBar, BorderLayout.SOUTH);
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
		setResizable(false);
	}
	
	// Method to update my bar
	public void updateBar(int newValue) {
		
		progBar.setValue(newValue);
	}

	// Method to warn about crawling interruption through lack of more sites
	public void showMessage(int status) {
		
		JFrame message = new JFrame();
		String msg, title;
		
		if(status < barMaximum) {
			title = "Crawling interrupted";
			msg = "There are no more sites to crawl!\nCheck the files created.";
		}
		else {
			title = "Crawling finished";
			msg = "The maximum number of sites was reached!\nCheck the files created.";
		}
		
		JOptionPane.showMessageDialog(message, msg, title, JOptionPane.PLAIN_MESSAGE);
		System.exit(0);
	}
}