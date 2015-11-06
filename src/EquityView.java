import java.awt.Color;

import java.awt.Font;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.FileNotFoundException;

import java.io.UnsupportedEncodingException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.HashMap;

import java.util.Map;

import javax.swing.JButton;

import javax.swing.JComboBox;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JOptionPane;

import javax.swing.JPasswordField;

import javax.swing.JTextField;

import javax.swing.SwingConstants;

public class EquityView {
	JFrame frame = new JFrame();
	Portfolio portfolio;
	Main mainToUse;
	private JTextField portfolioName;
	private JTextField bInitialAmount;
	private JTextField bCurrentAmount;
	private JTextField bDateAdded;
	private JTextField accountType;
	private JComboBox comboBox;
	
	private JTextField owns;
	
	/**
	 * Create the frame.
	 */
	public EquityView(Portfolio portfolio, Main system) {
		this.mainToUse = system;
		this.portfolio = portfolio;
		frame.setVisible(true);
		frame.setBounds(100, 100, 824, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JTextField frameTitle = new JTextField();
		frameTitle.setBounds(202, 11, 397, 68);
		frameTitle.setBackground(Color.LIGHT_GRAY);
		frameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		frameTitle.setFont(new Font("Tahoma", Font.PLAIN, 31));
		frameTitle.setText("Equities");
		frameTitle.setEditable(false);
		frame.getContentPane().add(frameTitle);
		frameTitle.setColumns(10);

		// JLabel lblPortfolioName = new JLabel("Portfolio Name:");
		// lblPortfolioName.setBounds(279, 140, 102, 34);
		// frame.getContentPane().add(lblPortfolioName);
		//
		// portfolioName = new JTextField();
		// portfolioName.setEditable(false);
		// portfolioName.setBounds(405, 147, 131, 20);
		// frame.getContentPane().add(portfolioName);
		// portfolioName.setText(portfolio.getUser());
		// portfolioName.setColumns(10);

		JLabel lblBankAccount = new JLabel("");
		lblBankAccount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBankAccount.setBounds(335, 178, 91, 23);
		frame.getContentPane().add(lblBankAccount);

		JLabel lblInitialAmount = new JLabel("Price");
		lblInitialAmount.setBounds(279, 155, 83, 14);
		frame.getContentPane().add(lblInitialAmount);

		JLabel lblCurrentAmount = new JLabel("Indices");
		lblCurrentAmount.setBounds(279, 181, 91, 14);
		frame.getContentPane().add(lblCurrentAmount);

		JLabel own = new JLabel("Owned");
		own.setBounds(279, 207, 91, 14);
		frame.getContentPane().add(own);
		
		JLabel lblAccount = new JLabel("Equity");
		lblAccount.setBounds(279, 103, 83, 14);
		frame.getContentPane().add(lblAccount);

		JLabel lblAccountType = new JLabel("Equity Name");
		lblAccountType.setBounds(279, 129, 83, 14);
		frame.getContentPane().add(lblAccountType);
		
		owns = new JTextField();
		owns.setEditable(false);
		owns.setBounds(388, 204, 112, 20);
		frame.getContentPane().add(owns);
		owns.setColumns(10);
		
		bInitialAmount = new JTextField();
		bInitialAmount.setEditable(false);
		bInitialAmount.setBounds(388, 152, 112, 20);
		frame.getContentPane().add(bInitialAmount);
		bInitialAmount.setColumns(10);
		
		bCurrentAmount = new JTextField();
		bCurrentAmount.setEditable(false);
		bCurrentAmount.setColumns(10);
		bCurrentAmount.setBounds(388, 178, 112, 20);
		frame.getContentPane().add(bCurrentAmount);

		accountType = new JTextField();
		accountType.setEditable(false);
		accountType.setBounds(388, 126, 112, 20);
		frame.getContentPane().add(accountType);
		accountType.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setBounds(388, 100, 112, 20);
		frame.getContentPane().add(comboBox);
		for (String key : Main.getEquities().keySet()) {
			if (portfolio.getOwnedEquities().containsKey(key)){
				comboBox.addItem(key);
			}
			else{
				comboBox.addItem(key);
			}
			
		}

		AutoCompletion.enable(comboBox);


		
		// adds buttons to the frame to look at equities,
		// market accounts, and bank accounts
		frame.getContentPane().add(backButton(portfolio));
		frame.getContentPane().add(bAccountTransaction(portfolio));
		frame.getContentPane().add(chooseButton(portfolio));
		frame.getContentPane().add(createAccountButton(portfolio));
		frame.getContentPane().add(transferButton(portfolio));
		frame.getContentPane().add(ownEquities(portfolio));
		frame.getContentPane().add(watchlist(portfolio));
		frame.getContentPane().add(adde(portfolio));
		frame.getContentPane().add(rm(portfolio));
	}

	public JButton transferButton(Portfolio portfolio) {

		JTextField percent = new JTextField();
		JComboBox<String> timeInterval = new JComboBox<String>();
		timeInterval.addItem("Days");
		timeInterval.addItem("Months");
		timeInterval.addItem("Years");

		JComboBox<String> isSteps = new JComboBox<String>();
		isSteps.addItem("True");
		isSteps.addItem("False");

		JComboBox<String> algType = new JComboBox<String>();
		algType.addItem("Bull");
		algType.addItem("Bear");
		algType.addItem("No Growth");

		JTextField timeSteps = new JTextField();
		Object[] message = { "Input desired percent change:", percent, "Choose if desired steps:", isSteps,
				"Choose interval of time:", timeInterval, "Input number of iterations:", timeSteps };

		JButton bbutton = new JButton("Run Simulation");
		bbutton.setBounds(279, 414, 221, 23);

		bbutton.addActionListener(new ActionListener() {
			String title = "Select Algorithm Settings";

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					frame.setVisible(false);
					new Algorithms(portfolio, timeInterval.getSelectedItem().toString(), timeSteps.getText(),
							isSteps.getSelectedItem().toString(), algType.getSelectedItem().toString(),
							percent.getText());
				}
			}
		});

		return bbutton;
	}

	public JButton createAccountButton(Portfolio portfolio) {
		JTextField desiredAmount = new JTextField();
		JTextField cost = new JTextField();
		JTextField funds = new JTextField();
		JComboBox<String> fromAccount = new JComboBox<String>();

		Object[] message = { "Quantity:", desiredAmount, "Select Account:", fromAccount, };
		String title = "Enter information below.";
		JButton bbutton = new JButton("Sell");
		bbutton.setBounds(279, 264, 221, 20);
		bbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fromAccount.removeAllItems();
				for (String key : portfolio.getAccounts().keySet()) {
					fromAccount.addItem(key);
				}
				
				String sym;
				if (comboBox.getSelectedItem().toString().contains("color")){
					String temp = comboBox.getSelectedItem().toString();
					String s = "";
					for (int y = 0; y < temp.length(); y++){
						if (Character.isUpperCase(temp.charAt(y))){
							s = s+temp.charAt(y);
						}
					}
					sym = s;
				}
				else{
					sym = comboBox.getSelectedItem().toString();
				}
				accountType.setText(Main.getEquities().get(sym.toString()).getName());
				int option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String title = "Press Ok if everything looks correct.";
					funds.setText(Float.toString(
							portfolio.getAccounts().get(fromAccount.getSelectedItem().toString()).getCurrentAmount()));
					funds.setEditable(false);
					cost.setText(
							Float.toString(Main.getEquities().get(sym.toString()).getPrice()
									* Integer.parseInt(desiredAmount.getText())));
					cost.setEditable(false);
					Object[] message = { "Cost:", cost, "Account funds:", funds, };
					option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						portfolio.sellEquity(Main.getEquities().get(sym).getTicker(), 
								Integer.parseInt(desiredAmount.getText()), 
								fromAccount.getSelectedItem().toString(), 
								Main.getEquities().get(sym).getPrice());
						frame.setVisible(false);
						new EquityView(portfolio, mainToUse);

					}
				}
			}
		});
		return bbutton;
	}

	public JButton bAccountTransaction(Portfolio portfolio) {
		JTextField desiredAmount = new JTextField();
		JTextField cost = new JTextField();
		JTextField funds = new JTextField();
		JComboBox<String> fromAccount = new JComboBox<String>();

		Object[] message = { "Quantity:", desiredAmount, "Select Account:", fromAccount, };
		String title = "Enter information below.";
		JButton bbutton = new JButton("Buy");
		bbutton.setBounds(279, 234, 221, 20);
		bbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fromAccount.removeAllItems();
				for (String key : portfolio.getAccounts().keySet()) {
					fromAccount.addItem(key);
				}
				
				String sym;
				if (comboBox.getSelectedItem().toString().contains("color")){
					String temp = comboBox.getSelectedItem().toString();
					String s = "";
					for (int y = 0; y < temp.length(); y++){
						if (Character.isUpperCase(temp.charAt(y))){
							s = s+temp.charAt(y);
						}
					}
					sym = s;
				}
				else{
					sym = comboBox.getSelectedItem().toString();
				}
				
				accountType.setText(Main.getEquities().get(sym).getName());
				int option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String title = "Press Ok if everything looks correct.";
					funds.setText(Float.toString(
							portfolio.getAccounts().get(fromAccount.getSelectedItem().toString()).getCurrentAmount()));
					funds.setEditable(false);
					cost.setText(
							Float.toString(Main.getEquities().get(sym).getPrice()
									* Integer.parseInt(desiredAmount.getText())));
					cost.setEditable(false);
					Object[] message = { "Cost:", cost, "Account funds:", funds, };
					option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						portfolio.buyEquity(Main.getEquities().get(sym).getTicker(), 
								Integer.parseInt(desiredAmount.getText()), 
								fromAccount.getSelectedItem().toString(), 
								Main.getEquities().get(sym).getPrice());
						frame.setVisible(false);
						new EquityView(portfolio, mainToUse);
					}
				}
			}
		});
		return bbutton;
	}

	public JButton chooseButton(Portfolio portfolio) {
		JButton btnChoose = new JButton("Details");
		btnChoose.setBounds(510, 99, 89, 23);
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
				// bInitialAmount.repaint();
				// bCurrentAmount.setText(String.valueOf(portfolio.getAccounts().get(comboBox.getName()).getCurrentAmount()));
				try {
					// DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					// String indices = "";
					// for (String s :
					// mainToUse.sEquities.get(comboBox.getSelectedItem().toString()).getIndices())
					// {
					// indices += " - ";
					// indices += s;
					// indices += " - ";
					// }
					String sym;
					if (comboBox.getSelectedItem().toString().contains("color")){
						String temp = comboBox.getSelectedItem().toString();
						String s = "";
						for (int y = 0; y < temp.length(); y++){
							if (Character.isUpperCase(temp.charAt(y))){
								s = s+temp.charAt(y);
							}
						}
						sym = s;
					}
					else{
						sym = comboBox.getSelectedItem().toString();
					}// bCurrentAmount.setText(String.format("%.2f",(mainToUse.sEquities.get(comboBox.getSelectedItem().toString()).getIndices().toString())));
					String iis = "";
					for (String s : Main.getEquities().get(sym).getIndices()){
						iis = iis+s+",";
					}
					if (iis.length()>0){
						bCurrentAmount.setText(iis.substring(0, iis.length()-1));
					}
					accountType.setText(Main.getEquities().get(sym).getName());
					if (portfolio.getOwnedEquities().get(sym) != null){
						owns.setText(portfolio.getOwnedEquities().get(sym).toString());
					}
					else{
						owns.setText("0");
					}
					bInitialAmount.setText(String.format("%.2f",
							(Main.getEquities().get(sym).getPrice())));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		return btnChoose;
	}

	public JButton backButton(Portfolio portfolio) {
		JButton viewBankAccount = new JButton("Back");
		viewBankAccount.setBounds(279, 444, 221, 23);
		viewBankAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				mainToUse.getPortfolioView();
				mainToUse.getPortfolioView().frame.setVisible(true);
			}
		});
		return viewBankAccount;
	}
	
	public JButton ownEquities(Portfolio portfolio) {
		JButton owwns = new JButton("Owned Equities");
		owwns.setBounds(279, 384, 221, 23);
		owwns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String d  = "Ticker : Number owned\n";
				for (String s : portfolio.getOwnedEquities().keySet()){
					d = d + s + " : " +portfolio.getOwnedEquities().get(s).toString()+ "\n";
				}
				d = d.substring(0, d.length());
				//frame.setVisible(false);
				//mainToUse.getPortfolioView();
				//mainToUse.getPortfolioView().frame.setVisible(true);
				JOptionPane.showMessageDialog(null, d, "Owned equities", JOptionPane.PLAIN_MESSAGE);
			}
		});
		return owwns;
	}
	
	public JButton watchlist(Portfolio portfolio) {
		JButton bb = new JButton("Watchlist");
		bb.setBounds(279, 354, 221, 23);
		bb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String d  = "Ticker : current price\n";
				for (String s : portfolio.getWatchlist()){
					d = d + s + " : " + Float.toString(Main.getEquities().get(s).getPrice())+ "\n";
				}
				d = d.substring(0, d.length());
				//frame.setVisible(false);
				//mainToUse.getPortfolioView();
				//mainToUse.getPortfolioView().frame.setVisible(true);
				JOptionPane.showMessageDialog(null, d, "Watchlist equities", JOptionPane.PLAIN_MESSAGE);
			}
		});
		return bb;
	}
	
	public JButton adde(Portfolio portfolio) {
		JButton bb = new JButton("Add to Watchlist");
		bb.setBounds(279, 294, 221, 23);
		bb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sym;
				if (comboBox.getSelectedItem().toString().contains("color")){
					String temp = comboBox.getSelectedItem().toString();
					String s = "";
					for (int y = 0; y < temp.length(); y++){
						if (Character.isUpperCase(temp.charAt(y))){
							s = s+temp.charAt(y);
						}
					}
					sym = s;
				}
				else{
					sym = comboBox.getSelectedItem().toString();
				}
				portfolio.addToWatchlist(sym);
			}
		});
		return bb;
	}
	
	public JButton rm(Portfolio portfolio) {
		JButton bb = new JButton("Remove from Watchlist");
		bb.setBounds(279, 324, 221, 23);
		bb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sym;
				if (comboBox.getSelectedItem().toString().contains("color")){
					String temp = comboBox.getSelectedItem().toString();
					String s = "";
					for (int y = 0; y < temp.length(); y++){
						if (Character.isUpperCase(temp.charAt(y))){
							s = s+temp.charAt(y);
						}
					}
					sym = s;
				}
				else{
					sym = comboBox.getSelectedItem().toString();
				}
				portfolio.removeFromWatchlist(sym);
			}
		});
		return bb;
	}
}
