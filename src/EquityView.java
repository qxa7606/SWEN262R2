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

		JLabel lblBankAccount = new JLabel("Equities");
		lblBankAccount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBankAccount.setBounds(335, 178, 91, 23);
		frame.getContentPane().add(lblBankAccount);

		JLabel lblInitialAmount = new JLabel("Price");
		lblInitialAmount.setBounds(279, 262, 83, 14);
		frame.getContentPane().add(lblInitialAmount);

		JLabel lblCurrentAmount = new JLabel("Indices");
		lblCurrentAmount.setBounds(279, 287, 91, 14);
		frame.getContentPane().add(lblCurrentAmount);

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

		comboBox = new JComboBox();
		comboBox.setBounds(388, 208, 112, 20);
		frame.getContentPane().add(comboBox);
		for (String key : Main.getEquities().keySet()) {
			comboBox.addItem(key);
		}

		JLabel lblAccount = new JLabel("Equity");
		lblAccount.setBounds(279, 211, 83, 14);
		frame.getContentPane().add(lblAccount);

		JLabel lblAccountType = new JLabel("Equity Name");
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
		bbutton.setBounds(279, 401, 221, 23);

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
		bbutton.setBounds(279, 369, 221, 20);
		bbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fromAccount.removeAllItems();
				for (String key : portfolio.getAccounts().keySet()) {
					fromAccount.addItem(key);
				}
				accountType.setText(Main.getEquities().get(comboBox.getSelectedItem().toString()).getName());
				int option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String title = "Press Ok if everything looks correct.";
					funds.setText(Float.toString(
							portfolio.getAccounts().get(fromAccount.getSelectedItem().toString()).getCurrentAmount()));
					funds.setEditable(false);
					cost.setText(
							Float.toString(Main.getEquities().get(comboBox.getSelectedItem().toString()).getPrice()
									* Integer.parseInt(desiredAmount.getText())));
					cost.setEditable(false);
					Object[] message = { "Cost:", cost, "Account funds:", funds, };
					option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						portfolio.sellEquity(Main.getEquities().get(comboBox.getSelectedItem().toString()).getTicker(), 
								Integer.parseInt(desiredAmount.getText()), 
								fromAccount.getSelectedItem().toString(), 
								Main.getEquities().get(comboBox.getSelectedItem().toString()).getPrice());

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
		bbutton.setBounds(279, 337, 221, 20);
		bbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fromAccount.removeAllItems();
				for (String key : portfolio.getAccounts().keySet()) {
					fromAccount.addItem(key);
				}
				accountType.setText(Main.getEquities().get(comboBox.getSelectedItem().toString()).getName());
				int option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String title = "Press Ok if everything looks correct.";
					funds.setText(Float.toString(
							portfolio.getAccounts().get(fromAccount.getSelectedItem().toString()).getCurrentAmount()));
					funds.setEditable(false);
					cost.setText(
							Float.toString(Main.getEquities().get(comboBox.getSelectedItem().toString()).getPrice()
									* Integer.parseInt(desiredAmount.getText())));
					cost.setEditable(false);
					Object[] message = { "Cost:", cost, "Account funds:", funds, };
					option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						portfolio.buyEquity(Main.getEquities().get(comboBox.getSelectedItem().toString()).getTicker(), 
								Integer.parseInt(desiredAmount.getText()), 
								fromAccount.getSelectedItem().toString(), 
								Main.getEquities().get(comboBox.getSelectedItem().toString()).getPrice());

					}
				}
			}
		});
		return bbutton;
	}

	public JButton chooseButton(Portfolio portfolio) {
		JButton btnChoose = new JButton("Choose");
		btnChoose.setBounds(510, 207, 89, 23);
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
					accountType.setText(Main.getEquities().get(comboBox.getSelectedItem().toString()).getName());
					bInitialAmount.setText(String.format("%.2f",
							(Main.getEquities().get(comboBox.getSelectedItem().toString()).getPrice())));
					// bCurrentAmount.setText(String.format("%.2f",(mainToUse.sEquities.get(comboBox.getSelectedItem().toString()).getIndices().toString())));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		return btnChoose;
	}

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