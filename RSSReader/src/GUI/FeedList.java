package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mainPackage.MainReader;

@SuppressWarnings("serial")
public class FeedList extends JFrame implements ActionListener, ListSelectionListener {

	private JPanel optionPanel;
	
	@SuppressWarnings("rawtypes")
	private JList<Vector> feedList;
	@SuppressWarnings("rawtypes")
	private Vector list;
	
	private JButton addRSSLink;
	private JButton removeRSSLink;
	private JButton chooseRSSFeed;
	private JButton refreshList;
	
	private JTextField feedURLInput;
	private JScrollPane rollingBar;

	private int DEFAULT_WIDTH = 700;
	private int DEFAULT_HEIGTH = 500;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FeedList() {

		setTitle("Choose a Feed URL or put a new one in the list");

		createFrameDimension();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.GRAY);

		optionPanel = new JPanel();
		optionPanel.setLayout(new BorderLayout());
		getContentPane().add(optionPanel);

		list = MainReader.getList();

		feedList = new JList<Vector>(list);
		feedList.addListSelectionListener(this);

		rollingBar = new JScrollPane();
		rollingBar.getViewport().add(feedList);
		optionPanel.add(rollingBar, BorderLayout.CENTER);

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
		optionPanel.add(extraPanel, BorderLayout.SOUTH);

		chooseRSSFeed = new JButton("Choose Feed");
		extraPanel.add(chooseRSSFeed, BorderLayout.WEST);
		chooseRSSFeed.addActionListener(this);

		addRSSLink = new JButton("Add Feed URL");
		extraPanel.add(addRSSLink, BorderLayout.EAST);
		addRSSLink.addActionListener(this);

		removeRSSLink = new JButton("Remove Feed URL");
		extraPanel.add(removeRSSLink, BorderLayout.PAGE_END);
		removeRSSLink.addActionListener(this);
		
		refreshList = new JButton("Refresh List");
		extraPanel.add(refreshList, BorderLayout.CENTER);
		refreshList.addActionListener(this);
		
		feedURLInput = new JTextField();
		feedURLInput.setText("Write Feed URL here");
		extraPanel.add(feedURLInput, BorderLayout.NORTH);
	}

	// Method to treat actions from the buttons and text field
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == addRSSLink
			&& feedURLInput.getText() != null
			&& feedURLInput.getText().equals("") == false
			&& feedURLInput.getText().contains(" ") == false
			&& feedURLInput.getText().contains("\t") == false) {

			String url = feedURLInput.getText();
			boolean verificator = true;

			// Checking if the URL was already in the list
			for(int i = 0; i < list.size(); i++)
				if(url != null && url.equals(list.get(i).toString()))
					verificator = false;

			if(verificator) {

				MainReader.addURL(url);
				feedURLInput.setText("");
				list = MainReader.getList();
				refresh();
			}
		}

		else if(event.getSource() == removeRSSLink) {

			int feedSelected = feedList.getSelectedIndex();

			// Making sure the index given is valid (user chose a link)
			if (feedSelected >= 0) {

				// Had to trim the string to remove line jumping put inside it
				String url = list.elementAt(feedSelected).toString().trim();
				System.out.println(list.elementAt(feedSelected).toString());
				System.out.println(feedList.getSelectedIndex());

				MainReader.removeURL(url);
				list = MainReader.getList();
				refresh();
			}
		}
		
		else if(event.getSource() == chooseRSSFeed) {

			int feedSelected = feedList.getSelectedIndex();

			// Making sure the index given is valid (user chose a link)
			if (feedSelected >= 0) {

				String url = list.elementAt(feedSelected).toString();

				this.setVisible(false);
				new ArticleList(this, url);
			}
		}

		else if(event.getSource() == refreshList) {

			list = MainReader.getList();
			refresh();
		}
	}

	@SuppressWarnings("unchecked")
	private void refresh() {
		
		feedList.setListData(list);
		rollingBar.revalidate();
		rollingBar.repaint();
	}
	
	// This method is declared because this class implements the one responsible for this method
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}
}