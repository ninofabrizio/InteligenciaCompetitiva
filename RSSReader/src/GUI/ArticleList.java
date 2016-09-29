package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	private JButton refreshList;
	private JButton goBack;

	private JScrollPane rollingBar;
	
	private int DEFAULT_WIDTH = 700;
	private int DEFAULT_HEIGTH = 500;
	
	public ArticleList(FeedList fl, String url) {
		
		feedList = fl;
		feedURL = url;
		feed = MainReader.getFeed(feedURL);
		
		setTitle("Choose an Article URL from " + feed.getTitle());
		
		createFrameDimension();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.GRAY);

		optionPanel = new JPanel();
		optionPanel.setLayout(new BorderLayout());
		getContentPane().add(optionPanel);

		populateTable();
		
		rollingBar = new JScrollPane(table);
		//rollingBar.getViewport().add(feedList);
		optionPanel.add(rollingBar, BorderLayout.CENTER);

		//pack();
		setVisible(true);
		
		createExtraPanel();
	}

	// Method to populate my table with the articles info
	private void populateTable() {
		
		table = new JTable(new DefaultTableModel(new Object[]{"TITLE", "AUTHOR", 
															"CATEGORY", "PUBLICATION DATE"}, 0));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		for(FeedMessage message : feed.getMessages()) {
			
			String data[]= {message.getTitle(), message.getAuthor(),
				message.getCategory(), message.getPublicationDate()};

			model.addRow(data);
		}
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
		
		refreshList = new JButton("Refresh List");
		extraPanel.add(refreshList, BorderLayout.PAGE_END);
		refreshList.addActionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}