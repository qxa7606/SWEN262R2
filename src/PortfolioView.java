import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PortfolioView {
	JFrame frame = new JFrame();
	Portfolio portfolio;
	Main mainToUse;

	public PortfolioView(Portfolio portfolio, Main system) {
		this.mainToUse = system;
		this.portfolio = portfolio;
		frame = new JFrame();
		frame.setBounds(100, 100, 824, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JTextField frameTitle = new JTextField();
		frameTitle.setBackground(Color.LIGHT_GRAY);
		frameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		frameTitle.setFont(new Font("Tahoma", Font.PLAIN, 31));
		frameTitle.setText("Portfolio");
		frameTitle.setBounds(202, 11, 397, 68);
		frameTitle.setEditable(false);
		frame.getContentPane().add(frameTitle);
		frameTitle.setColumns(10);

		// adds buttons to the frame to look at equities,
		// market accounts, and bank accounts
		frame.getContentPane().add(equitiesButton(portfolio));
		frame.getContentPane().add(mAccountsButton(portfolio));
		frame.getContentPane().add(ExportButton(portfolio));
		frame.getContentPane().add(logoutButton(portfolio));

		frame.setVisible(true);
	}

	public JButton equitiesButton(Portfolio portfolio) {
		JButton viewEquities = new JButton("View Equities");
		viewEquities.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new EquityView(portfolio, mainToUse);
			}
		});
		viewEquities.setBounds(352, 300, 200, 23);
		return viewEquities;
	}

	public JButton ExportButton(Portfolio portfolio) {
		
		///UNIMPLEMENTED!!!
		JButton bbutton = new JButton("Export");

		bbutton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog("Enter Export location");
				//mainToUse.iExport(name, portfolio);
			}
		});
		bbutton.setBounds(352, 330, 200, 23);
		return bbutton;
	}

	public JButton mAccountsButton(Portfolio portfolio) {
		JButton viewMarketAccount = new JButton("View Accounts");
		viewMarketAccount.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// @SuppressWarnings("rawtypes")
				// DefaultListModel listModel = new DefaultListModel();
				/*
				 * if (!portfolio.getAccounts().isEmpty()) { for (Account value
				 * : portfolio.getAccounts().values()) { if
				 * (value.getType().equals("Market")) {
				 * listModel.addElement(value); } } JList<Equity> list = new
				 * JList<Equity>(listModel); frame.getContentPane().add(list); }
				 * else { JOptionPane.showMessageDialog(null,
				 * "Your portfolio has no market account.");
				 * 
				 * }
				 */
				frame.setVisible(false);
				mainToUse.setAccountView(new AccountView(portfolio, mainToUse));
			}
		});
		viewMarketAccount.setBounds(352, 360, 200, 23);
		return viewMarketAccount;
	}
	/*
	 * public JButton bAccountsButton(Portfolio portfolio) { JButton
	 * viewBankAccount = new JButton("View Bank Account");
	 * viewBankAccount.addActionListener(new ActionListener() {
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public void actionPerformed(ActionEvent arg0) {
	 * 
	 * @SuppressWarnings("rawtypes") DefaultListModel listModel = new
	 * DefaultListModel(); if (!portfolio.getAccounts().isEmpty()) { for
	 * (Account value : portfolio.getAccounts().values()) { if
	 * (value.getType().equals("Bank")) { listModel.addElement(value); } }
	 * JList<Equity> list = new JList<Equity>(listModel);
	 * frame.getContentPane().add(list); } else {
	 * JOptionPane.showMessageDialog(null, "Your portfolio has no bank account."
	 * );
	 * 
	 * } } }); viewBankAccount.setBounds(352, 325, 200, 23); return
	 * viewBankAccount; }
	 */

	public JButton logoutButton(Portfolio portfolio) {
		JButton viewLogout = new JButton("Logout");
		viewLogout.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				mainToUse.getMainWindow().initialize();
				mainToUse.getMainWindow().frame.setVisible(true);
			}
		});
		viewLogout.setBounds(352, 390, 200, 23);
		return viewLogout;
	}

}
