import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class AccountView extends JFrame {
	JFrame frame = new JFrame();
	Portfolio portfolio;
	Main mainToUse;
	private JTextField portfolioName;
	private JTextField bInitialAmount;
	private JTextField bCurrentAmount;
	private JTextField bDateAdded;
	private JTextField accountType;
	private JComboBox comboBox;

	/**
	 * Create the frame.
	 */
	public AccountView(Portfolio portfolio, Main system) {
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
		frameTitle.setText("Account Information");
		frameTitle.setEditable(false);
		frame.getContentPane().add(frameTitle);
		frameTitle.setColumns(10);

		JLabel lblPortfolioName = new JLabel("Portfolio Name:");
		lblPortfolioName.setBounds(279, 140, 102, 34);
		frame.getContentPane().add(lblPortfolioName);

		portfolioName = new JTextField();
		portfolioName.setEditable(false);
		portfolioName.setBounds(405, 147, 131, 20);
		frame.getContentPane().add(portfolioName);
		portfolioName.setText(portfolio.getUser());
		portfolioName.setColumns(10);

		JLabel lblBankAccount = new JLabel("Account Info");
		lblBankAccount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBankAccount.setBounds(335, 178, 91, 23);
		frame.getContentPane().add(lblBankAccount);

		JLabel lblInitialAmount = new JLabel("Initial Amount");
		lblInitialAmount.setBounds(279, 262, 83, 14);
		frame.getContentPane().add(lblInitialAmount);

		JLabel lblCurrentAmount = new JLabel("Current Amount");
		lblCurrentAmount.setBounds(279, 287, 91, 14);
		frame.getContentPane().add(lblCurrentAmount);

		JLabel lblCreationDate = new JLabel("Creation Date");
		lblCreationDate.setBounds(279, 312, 91, 14);
		frame.getContentPane().add(lblCreationDate);

		bInitialAmount = new JTextField();
		bInitialAmount.setEditable(false);
		bInitialAmount.setBounds(388, 256, 112, 20);
		frame.getContentPane().add(bInitialAmount);
		bInitialAmount.setColumns(10);

		bCurrentAmount = new JTextField();
		bCurrentAmount.setEditable(false);
		bCurrentAmount.setColumns(10);
		bCurrentAmount.setBounds(388, 281, 112, 20);
		frame.getContentPane().add(bCurrentAmount);

		bDateAdded = new JTextField();
		bDateAdded.setEditable(false);
		bDateAdded.setColumns(10);
		bDateAdded.setBounds(388, 306, 112, 20);
		frame.getContentPane().add(bDateAdded);

		comboBox = new JComboBox();
		comboBox.setBounds(388, 208, 112, 20);
		frame.getContentPane().add(comboBox);
		for (String key : portfolio.getAccounts().keySet()) {
			comboBox.addItem(key);
		}

		AutoCompletion.enable(comboBox);
		
		JLabel lblAccount = new JLabel("Account");
		lblAccount.setBounds(279, 211, 83, 14);
		frame.getContentPane().add(lblAccount);

		JLabel lblAccountType = new JLabel("Account Type");
		lblAccountType.setBounds(279, 237, 83, 14);
		frame.getContentPane().add(lblAccountType);

		accountType = new JTextField();
		accountType.setEditable(false);
		accountType.setBounds(388, 232, 112, 20);
		frame.getContentPane().add(accountType);
		accountType.setColumns(10);

		// adds buttons to the frame to look at equities,
		// market accounts, and bank accounts
		frame.getContentPane().add(backButton(portfolio));
		frame.getContentPane().add(bAccountTransaction(portfolio));
		frame.getContentPane().add(chooseButton(portfolio));
		frame.getContentPane().add(createAccountButton(portfolio));
		frame.getContentPane().add(transferButton(portfolio));

	}

	// the function that transferring money from one account to another
	// it can only transferring money between accounts belong to the same
	// portfolio.
	public JButton transferButton(Portfolio portfolio) {
		JButton bbutton = new JButton("Transfer");
		bbutton.setBounds(279, 401, 221, 23);
		JComboBox<String> fromAccount = new JComboBox<String>();
		JComboBox<String> toAccount = new JComboBox<String>();

		JTextField moneyTransfer = new JTextField();
		Object[] message = { "Transferring money from:", fromAccount, "Transferring money to:", toAccount,
				"Amount to transfer", moneyTransfer };

		bbutton.addActionListener(new ActionListener() {

			String title = "Enter the transferring amount below.";

			public void actionPerformed(ActionEvent arg0) {
				fromAccount.removeAllItems();
				toAccount.removeAllItems();
				for (String key : portfolio.getAccounts().keySet()) {
					fromAccount.addItem(key);
					toAccount.addItem(key);
				}
				int option = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String fr = fromAccount.getSelectedItem().toString();
					String to = toAccount.getSelectedItem().toString();
					float amount = Float.parseFloat(moneyTransfer.getText());
					
					if (portfolio.transfer(fr, to, amount)){
						JOptionPane.showMessageDialog(null, "Transfer Success");
					}
					else{
						title = "Tranfer unsuccessful";
						actionPerformed(arg0);
					}
				}

			}
		});
		return bbutton;
	}

	// it creates a new account within the portfolio
	// the new account cannot have the same name as other accounts in the
	// portfolio
	public JButton createAccountButton(Portfolio portfolio) {
		JButton bbutton = new JButton("Create New Account");
		bbutton.setBounds(279, 368, 221, 23);
		JComboBox<String> acntType = new JComboBox<String>();
		acntType.addItem("Bank");
		acntType.addItem("Market");
		JTextField acntName = new JTextField();
		JTextField acntWorth = new JTextField();
		Object[] message = { "Input account type (bank or market):", acntType, "Input account name:", acntName,
				"Input deposit amount:", acntWorth };
		bbutton.addActionListener(new ActionListener() {
			String title = "Enter a new account information below.";

			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (portfolio.getAccounts().get(acntName.getText()) == null) {
						if (portfolio.addAccount(acntType.getSelectedItem().toString(), acntName.getText(), Float.parseFloat(acntWorth.getText()))){
							comboBox.addItem(acntName.getText());
							frame.setVisible(false);
							mainToUse.setAccountView(new AccountView(portfolio, mainToUse));
							//title = "Account created";
							//actionPerformed(arg0);
						}
						else{
							title = "Account creation failed";
							actionPerformed(arg0);
						}
					} else {
						title = "That account name is taken.";
						actionPerformed(arg0);
					}
				}
			}
		});
		return bbutton;
	}

	// shows all the transactions in between the accounts
	public JButton bAccountTransaction(Portfolio portfolio) {
		JButton bbutton = new JButton("Show Account Transaction");
		bbutton.setBounds(279, 337, 221, 20);
		bbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new TransactionView(portfolio, mainToUse);
			}
		});
		return bbutton;
	}

	// update the information text boxes with the chosen account
	public JButton chooseButton(Portfolio portfolio) {
		JButton btnChoose = new JButton("Choose");
		btnChoose.setBounds(510, 207, 89, 23);
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
				// bInitialAmount.repaint();
				// bCurrentAmount.setText(String.valueOf(portfolio.getAccounts().get(comboBox.getName()).getCurrentAmount()));
				try {
					DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					accountType.setText(portfolio.getAccounts().get(comboBox.getSelectedItem().toString()).getType());
					bInitialAmount.setText(String.format("%.2f",
							(portfolio.getAccounts().get(comboBox.getSelectedItem().toString()).getInitialAmount())));
					bCurrentAmount.setText(String.format("%.2f",
							(portfolio.getAccounts().get(comboBox.getSelectedItem().toString()).getCurrentAmount())));
					bDateAdded.setText(df
							.format(portfolio.getAccounts().get(comboBox.getSelectedItem().toString()).getDateAdded()));
					// accountType.setText("2");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		return btnChoose;
	}

	// going back to the portfolio view.
	public JButton backButton(Portfolio portfolio) {
		JButton viewBankAccount = new JButton("Back");
		viewBankAccount.setBounds(279, 435, 221, 23);
		viewBankAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				mainToUse.getPortfolioView();
				mainToUse.getPortfolioView().frame.setVisible(true);
			}
		});
		return viewBankAccount;
	}
}
