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

public class PortfolioView implements ViewComponent{
	JFrame frame = new JFrame();
	Portfolio portfolio;
	Main mainToUse;

	public PortfolioView(Portfolio portfolio, Main system) {
		this.mainToUse = system;
		this.portfolio = portfolio;
		bounds(100, 100, 824, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		setTitle("Portfolio");
		
		frame.getContentPane().add(equitiesButton(portfolio));
		frame.getContentPane().add(mAccountsButton(portfolio));
		frame.getContentPane().add(ExportButton(portfolio));
		frame.getContentPane().add(logoutButton(portfolio));

		visible(true);
	}

	public JButton equitiesButton(Portfolio portfolio) {
		ButtonLeaf viewEquities = new ButtonLeaf("View Equities", 352, 300, 200, 23, true);
		viewEquities.button.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new EquityView(portfolio, mainToUse);
			}
		});
		return viewEquities.button;
	}

	public JButton ExportButton(Portfolio portfolio) {
		
		///UNIMPLEMENTED!!!
		ButtonLeaf bbutton = new ButtonLeaf("Export", 352, 330, 200, 23, true);

		bbutton.button.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog("Enter Export location");
				Main.pExport(new ExportOne(name, portfolio));
				//mainToUse.iExport(name, portfolio);
			}
		});
		return bbutton.button;
	}

	public JButton mAccountsButton(Portfolio portfolio) {
		ButtonLeaf viewMarketAccount = new ButtonLeaf("View Accounts", 352, 360, 200, 23, true);
		viewMarketAccount.button.addActionListener(new ActionListener() {
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
		return viewMarketAccount.button;
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
		ButtonLeaf viewLogout = new ButtonLeaf("Logout", 352, 390, 200, 23, true);
		viewLogout.button.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				mainToUse.getMainWindow().getComponent();
				mainToUse.getMainWindow().frame.setVisible(true);
			}
		});
		return viewLogout.button;
	}

	@Override
	public void setTitle(String title) {
		JTextField frameTitle = new JTextField();
		frameTitle.setBackground(Color.LIGHT_GRAY);
		frameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		frameTitle.setFont(new Font("Tahoma", Font.PLAIN, 31));
		frameTitle.setText(title);
		frameTitle.setBounds(202, 11, 397, 68);
		frameTitle.setEditable(false);
		frame.getContentPane().add(frameTitle);
		frameTitle.setColumns(10);		
	}

	@Override
	public void bounds(int x, int y, int width, int height) {
		frame.setBounds(x,y,width,height);
	}

	@Override
	public void visible(boolean isVisible) {
		frame.setVisible(isVisible);
	}

	@Override
	public ViewComponent getComponent() {
		return this;
	}

}