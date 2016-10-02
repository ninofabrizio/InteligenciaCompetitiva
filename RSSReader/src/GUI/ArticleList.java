package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import mainPackage.MainReader;
import rssModel.Feed;
import rssModel.FeedMessage;

public class ArticleList extends JFrame implements ActionListener, ListSelectionListener {

	private FeedList feedList;
	private String feedURL;
	private Feed feed;
	
	private JPanel optionPanel;
	private JTable table;

	private JButton seeArticleDescription;
	private JButton openArticleLink;
	private JButton goBack;

	private JScrollPane rollingBar;
	
	private int DEFAULT_WIDTH = 700;
	private int DEFAULT_HEIGTH = 500;
	
	public ArticleList(FeedList fl, String url) {
		
		feedList = fl;
		feedURL = url;
		feed = MainReader.getFeed(feedURL);
		
		if(feed != null)
			setTitle("Choose an Article URL from " + feed.getTitle());
		else
			setTitle("Choose an Article URL");
		
		createFrameDimension(this, DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.GRAY);

		optionPanel = new JPanel();
		optionPanel.setLayout(new BorderLayout());
		getContentPane().add(optionPanel);

		populateTable();
		
		rollingBar = new JScrollPane();
		rollingBar.getViewport().add(table);
		optionPanel.add(rollingBar, BorderLayout.CENTER);

		setVisible(true);
		
		createExtraPanel();
	}

	// Method to populate my table with the articles info
	private void populateTable() {
		
		table = new JTable(new DefaultTableModel(new Object[]{"TITLE", "AUTHOR", 
															"CATEGORY", "PUBLICATION DATE"}, 0));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		for(FeedMessage message : feed.getMessages()) {
			
			System.out.println(message.getLink());
			
			String data[]= {message.getTitle(), message.getAuthor(),
				message.getCategory(), message.getPublicationDate()};

			model.addRow(data);
		}
	}

	// Method to specify the dimensions of the frame
	private void createFrameDimension(JFrame frame, int width, int heigth) {

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		int xPos = screenWidth / 2 - width / 2;
		int yPos = screenHeight / 2 - heigth / 2;

		frame.setBounds(xPos, yPos, width, heigth);
		frame.setResizable(true);
	}
	
	// Method for the panel containing the buttons
	private void createExtraPanel() {

		JPanel extraPanel = new JPanel();
		extraPanel.setLayout(new BorderLayout());
		optionPanel.add(extraPanel, BorderLayout.SOUTH);

		seeArticleDescription = new JButton("See Article Description");
		extraPanel.add(seeArticleDescription, BorderLayout.WEST);
		seeArticleDescription.addActionListener(this);

		openArticleLink = new JButton("Open Article Link");
		extraPanel.add(openArticleLink, BorderLayout.CENTER);
		openArticleLink.addActionListener(this);
		
		goBack = new JButton("Go Back to Feeds List");
		extraPanel.add(goBack, BorderLayout.EAST);
		goBack.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == seeArticleDescription) {

			int articleRow = table.getSelectedRow();
			
			// Making sure the index given is valid (user chose an article)
			if (articleRow > -1) {
			
				String articleTitle = table.getModel().getValueAt(articleRow, 0).toString();
				String articleDescription = null;
				
				// Looking for the article in the feed by the title
				for(FeedMessage message : feed.getMessages()) {
					
					if(message.getTitle().equals(articleTitle)) {
						
						articleDescription = message.getDescription();
						break;
					}
				}
				
				JFrame descriptionFrame = new JFrame();
				JPanel descriptionPanel = new JPanel();

				JTextArea description = new JTextArea();
				description.setLineWrap(true);
				description.setWrapStyleWord(true);
				description.setEditable(false);
				description.setText(articleDescription);
				
				descriptionPanel.setLayout(new BorderLayout());
				descriptionPanel.add(description, BorderLayout.CENTER);
				descriptionFrame.add(descriptionPanel);
				
				createFrameDimension(descriptionFrame, 800, 300);
				descriptionFrame.setTitle("Description of article " + articleTitle);
				descriptionFrame.setVisible(true);
			}
		}
		
		else if(event.getSource() == openArticleLink) {

			int articleRow = table.getSelectedRow();
			
			// Making sure the index given is valid (user chose an article)
			if (articleRow > -1) {
			
				String articleTitle = table.getModel().getValueAt(articleRow, 0).toString();
				String articleURL = null;
				
				// Looking for the article in the feed by the title
				for(FeedMessage message : feed.getMessages()) {
					
					if(message.getTitle().equals(articleTitle)) {
						
						articleURL = message.getLink();
						break;
					}
				}
				
				try {
					
					Desktop desktop = java.awt.Desktop.getDesktop();
					URI oURL = new URI(articleURL);
					desktop.browse(oURL);
				}
				catch (Exception e) { 
					e.printStackTrace();
					throw new RuntimeException("Couldn't open link");
				}
			}
		}
		
		else if(event.getSource() == goBack) {

			this.setVisible(false);
			feedList.setVisible(true);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}