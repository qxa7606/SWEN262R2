import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AccountView implements ViewComponent {
	Main mainToUse;
	Portfolio portfolio;

	JFrame frame = new JFrame();
	
	LabelLeaf lblBankAccount = new LabelLeaf("Account Info", 335, 178, 91, 23, true);
	LabelLeaf lblInitialAmount = new LabelLeaf("Initial Amount", 279, 262, 83, 14, true);
	LabelLeaf lblCurrentAmount = new LabelLeaf("Current Amount", 279, 287, 91, 14, true);
	LabelLeaf lblCreationDate = new LabelLeaf("Creation Date", 279, 312, 91, 14, true);
	LabelLeaf lblPortfolioName = new LabelLeaf("Portfolio Name: ", 279, 140, 102, 34, true);
	LabelLeaf lblAccount = new LabelLeaf("Account", 279, 211, 83, 14, true);
	LabelLeaf lblAccountType = new LabelLeaf("Account Type", 279, 237, 83, 14, true);
	
	TextFieldLeaf portfolioName = new TextFieldLeaf("Portfolio ", 405, 147, 131, 20, true);
	TextFieldLeaf bInitialAmount = new TextFieldLeaf("Initial Amount", 388, 256, 112, 20, true);
	TextFieldLeaf bCurrentAmount = new TextFieldLeaf("Current Amount", 388, 281, 112, 20, true);
	TextFieldLeaf bDateAdded = new TextFieldLeaf("Date Added", 388, 306, 112, 20, true);
	TextFieldLeaf accountType = new TextFieldLeaf("Account Type", 388, 232, 112, 20, true);

	ComboBoxLeaf comboBox = new ComboBoxLeaf("ComboBox", 388, 208, 112, 20, true);

	public AccountView() {
		System.out.println("You should add parameters to the frame instantiation");
	}
	
	public AccountView(Portfolio portfolio, Main system) {
		this.mainToUse = system;
		this.portfolio = portfolio;
		bounds(100, 100, 824, 546);
		visible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		setTitle(portfolio.getUser() + "'s Account View");
		
		frame.getContentPane().add(lblPortfolioName.label);
		frame.getContentPane().add(lblBankAccount.label);
		frame.getContentPane().add(lblInitialAmount.label);
		frame.getContentPane().add(lblCurrentAmount.label);
		frame.getContentPane().add(lblCreationDate.label);
		frame.getContentPane().add(lblAccount.label);
		frame.getContentPane().add(lblAccountType.label);
		
		portfolioName.field.setEditable(false);
		portfolioName.field.setText(portfolio.getUser());
		portfolioName.field.setColumns(10);
		frame.getContentPane().add(portfolioName.field);
		
		bInitialAmount.field.setEditable(false);
		bInitialAmount.field.setColumns(10);
		frame.getContentPane().add(bInitialAmount.field);
		
		bCurrentAmount.field.setEditable(false);
		bCurrentAmount.field.setColumns(10);
		frame.getContentPane().add(bCurrentAmount.field);
		
		bDateAdded.field.setEditable(false);
		bDateAdded.field.setColumns(10);
		frame.getContentPane().add(bDateAdded.field);
		
		accountType.field.setEditable(false);
		accountType.field.setColumns(10);
		
		frame.getContentPane().add(comboBox.comboBox);
		for (String key : portfolio.getAccounts().keySet()) {
			comboBox.comboBox.addItem(key);
		}
		
		// adds buttons to the frame to look at equities,
		// market accounts, and bank accounts
		frame.getContentPane().add(backButton(portfolio));
		frame.getContentPane().add(bAccountTransaction(portfolio));
		frame.getContentPane().add(chooseButton(portfolio));
		frame.getContentPane().add(createAccountButton(portfolio));
		frame.getContentPane().add(transferButton(portfolio));
		
	}
	
	public AccountView(String title, int x, int y, int width, int height, boolean isVisible) {
		this.setTitle(title);
		this.bounds(x, y, width, height);
		this.visible(isVisible);
	}
	
	@Override
	public void setTitle(String title) {
		frame.setName(title);	
		TextFieldLeaf frameTitle = new TextFieldLeaf(title, 202, 11, 397, 68, true);
		frameTitle.field.setBackground(Color.LIGHT_GRAY);
		frameTitle.field.setHorizontalAlignment(SwingConstants.CENTER);
		frameTitle.field.setFont(new Font("Tahoma", Font.PLAIN, 31));
		frameTitle.field.setText("Account Information");
		frameTitle.field.setEditable(false);
		frame.getContentPane().add(frameTitle.field);
		frameTitle.field.setColumns(10);
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
	
	// the function that transferring money from one account to another
	// it can only transferring money between accounts belong to the same
	// portfolio.
	public JButton transferButton(Portfolio portfolio) {
		ButtonLeaf bbutton = new ButtonLeaf("Transfer", 279, 401, 221, 23, true);
		JComboBox<String> fromAccount = new JComboBox<String>();
		JComboBox<String> toAccount = new JComboBox<String>();

		JTextField moneyTransfer = new JTextField();
		Object[] message = { "Transferring money from:", fromAccount, "Transferring money to:", toAccount,
				"Amount to transfer", moneyTransfer };

		bbutton.button.addActionListener(new ActionListener() {

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
		return bbutton.button;
	}
	
	// it creates a new account within the portfolio
	// the new account cannot have the same name as other accounts in the
	// portfolio
	public JButton createAccountButton(Portfolio portfolio) {
		ButtonLeaf bbutton = new ButtonLeaf("Create New Account", 279, 368, 221, 23, true);
		JComboBox<String> acntType = new JComboBox<String>();
		acntType.addItem("Bank");
		acntType.addItem("Market");
		JTextField acntName = new JTextField();
		JTextField acntWorth = new JTextField();
		Object[] message = { "Input account type (bank or market):", acntType, "Input account name:", acntName,
				"Input deposit amount:", acntWorth };
		bbutton.button.addActionListener(new ActionListener() {
			String title = "Enter a new account information below.";

			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (portfolio.getAccounts().get(acntName.getText()) == null) {
						if (portfolio.addAccount(acntType.getSelectedItem().toString(), acntName.getText(), Float.parseFloat(acntWorth.getText()))){
							comboBox.comboBox.addItem(acntName.getText());
							comboBox.comboBox.addItem(acntName.getText());
							title = "Account created";
							actionPerformed(arg0);
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
		return bbutton.button;
	}
	
	// shows all the transactions in between the accounts
	public JButton bAccountTransaction(Portfolio portfolio) {
		ButtonLeaf bbutton = new ButtonLeaf("Show Account Transaction", 279, 337, 221, 20, true);
		bbutton.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new TransactionView(portfolio, mainToUse);
			}
		});
		return bbutton.button;
	}
	
	// update the information text boxes with the chosen account
	public JButton chooseButton(Portfolio portfolio) {
		ButtonLeaf btnChoose = new ButtonLeaf("Choose", 510, 207, 89, 23, true);
		btnChoose.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
				// bInitialAmount.repaint();
				// bCurrentAmount.setText(String.valueOf(portfolio.getAccounts().get(comboBox.getName()).getCurrentAmount()));
				try {
					DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					accountType.field.setText(portfolio.getAccounts().get(comboBox.comboBox.getSelectedItem().toString()).getType());
					bInitialAmount.field.setText(String.format("%.2f",
							(portfolio.getAccounts().get(comboBox.comboBox.getSelectedItem().toString()).getInitialAmount())));
					bCurrentAmount.field.setText(String.format("%.2f",
							(portfolio.getAccounts().get(comboBox.comboBox.getSelectedItem().toString()).getCurrentAmount())));
					bDateAdded.field.setText(df
							.format(portfolio.getAccounts().get(comboBox.comboBox.getSelectedItem().toString()).getDateAdded()));
					// accountType.setText("2");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		return btnChoose.button;
	}
	
	// going back to the portfolio view.
	public JButton backButton(Portfolio portfolio) {
		ButtonLeaf backButton = new ButtonLeaf("Back", 279, 435, 221, 23, true);
		backButton.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				mainToUse.getPortfolioView();
				mainToUse.getPortfolioView().frame.setVisible(true);
			}
		});
		return backButton.button;
	}
	
	
	
}