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

public class EquityView implements ViewComponent{
	Main mainToUse;
	Portfolio portfolio;

	JFrame frame = new JFrame();
	
	LabelLeaf lblEquities = new LabelLeaf("Equities", 335, 178, 91, 23, true);
	LabelLeaf lblPrice = new LabelLeaf("Price", 279, 262, 83, 14, true);
	LabelLeaf lblIndices = new LabelLeaf("Indices", 279, 287, 91, 14, true);
	LabelLeaf lblPortfolioName = new LabelLeaf("Portfolio Name: ", 279, 140, 102, 34, true);
	LabelLeaf lblAccount = new LabelLeaf("Equity", 279, 211, 83, 14, true);
	LabelLeaf lblAccountType = new LabelLeaf("Equity Name", 279, 237, 83, 14, true);
	LabelLeaf own = new LabelLeaf("Owned", 279, 312, 91, 14, true);
	
	TextFieldLeaf portfolioName = new TextFieldLeaf("Portfolio ", 405, 147, 131, 20, true);
	TextFieldLeaf bPrice = new TextFieldLeaf("Initial Amount", 388, 256, 112, 20, true);
	TextFieldLeaf bIndices = new TextFieldLeaf("Current Amount", 388, 281, 112, 20, true);
	TextFieldLeaf bDateAdded = new TextFieldLeaf("Date Added", 388, 306, 112, 20, true);
	TextFieldLeaf accountType = new TextFieldLeaf("Account Type", 388, 232, 112, 20, true);
	TextFieldLeaf owns = new TextFieldLeaf("Owned", 388, 307, 112, 20, true);

	ComboBoxLeaf comboBox = new ComboBoxLeaf("ComboBox", 388, 208, 112, 20, true);

	public EquityView() {
		System.out.println("You should add parameters to the frame instantiation");
	}
		
	/**
	 * Create the frame.
	 */
	public EquityView(Portfolio portfolio, Main system) {
		this.mainToUse = system;
		this.portfolio = portfolio;
		bounds(100, 100, 824, 546);
		visible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		setTitle(portfolio.getUser() + "'s Account View");
		
		frame.getContentPane().add(lblPortfolioName.label);
		frame.getContentPane().add(lblEquities.label);
		frame.getContentPane().add(lblPrice.label);
		frame.getContentPane().add(lblIndices.label);
		frame.getContentPane().add(lblAccount.label);
		frame.getContentPane().add(lblAccountType.label);
		frame.getContentPane().add(own.label);
		
		portfolioName.field.setEditable(false);
		portfolioName.field.setText(portfolio.getUser());
		portfolioName.field.setColumns(10);
		frame.getContentPane().add(portfolioName.field);
		
		bPrice.field.setEditable(false);
		bPrice.field.setColumns(10);
		frame.getContentPane().add(bPrice.field);
		
		bIndices.field.setEditable(false);
		bIndices.field.setColumns(10);
		frame.getContentPane().add(bIndices.field);
		
		accountType.field.setEditable(false);
		accountType.field.setColumns(10);
		frame.getContentPane().add(accountType.field);
		
		owns.field.setEditable(false);
		owns.field.setColumns(10);
		frame.getContentPane().add(owns.field);
		
		
		frame.getContentPane().add(comboBox.comboBox);
		for (String key : Main.getEquities().keySet()) {
			if (portfolio.getOwnedEquities().containsKey(key)){
				comboBox.comboBox.addItem("<html><font color=\"red\">"+key+"</font></html>");
			}
			else{
				comboBox.comboBox.addItem(key);
			}
			
		}

		JTextField frameTitle = new JTextField();
		frameTitle.setBounds(202, 11, 397, 68);
		frameTitle.setBackground(Color.LIGHT_GRAY);
		frameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		frameTitle.setFont(new Font("Tahoma", Font.PLAIN, 31));
		frameTitle.setText("Equities");
		frameTitle.setEditable(false);
		frame.getContentPane().add(frameTitle);
		frameTitle.setColumns(10);

		// adds buttons to the frame to look at equities,
		// market accounts, and bank accounts
		frame.getContentPane().add(backButton(portfolio));
		frame.getContentPane().add(buyEquity(portfolio));
		frame.getContentPane().add(chooseButton(portfolio));
		frame.getContentPane().add(createAccountButton(portfolio));
		frame.getContentPane().add(algorithmsButton(portfolio));
		
	}
	
	public EquityView(String title, int x, int y, int width, int height, boolean isVisible) {
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
	
	// button to run algorithms
	public JButton algorithmsButton(Portfolio portfolio) {

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

		ButtonLeaf bbutton = new ButtonLeaf("Run Simulation", 279, 401, 221, 23, true);

		bbutton.button.addActionListener(new ActionListener() {
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

		return bbutton.button;
	}
	
	//sell equity
	public JButton createAccountButton(Portfolio portfolio) {
		JTextField desiredAmount = new JTextField();
		JTextField cost = new JTextField();
		JTextField funds = new JTextField();
		JComboBox<String> fromAccount = new JComboBox<String>();

		Object[] message = { "Quantity:", desiredAmount, "Select Account:", fromAccount, };
		String title = "Enter information below.";
		ButtonLeaf bbutton = new ButtonLeaf("Sell", 279, 369, 221, 20, true);
		bbutton.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fromAccount.removeAllItems();
				for (String key : portfolio.getAccounts().keySet()) {
					fromAccount.addItem(key);
				}
				accountType.field.setText(Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getName());
				int option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String title = "Press Ok if everything looks correct.";
					funds.setText(Float.toString(
							portfolio.getAccounts().get(fromAccount.getSelectedItem().toString()).getCurrentAmount()));
					funds.setEditable(false);
					cost.setText(
							Float.toString(Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getPrice()
									* Integer.parseInt(desiredAmount.getText())));
					cost.setEditable(false);
					Object[] message = { "Cost:", cost, "Account funds:", funds, };
					option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						portfolio.sellEquity(Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getTicker(), 
								Integer.parseInt(desiredAmount.getText()), 
								fromAccount.getSelectedItem().toString(), 
								Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getPrice());

					}
				}
			}
		});
		return bbutton.button;
	}
	
	// buy equity
	public JButton buyEquity(Portfolio portfolio) {
		JTextField desiredAmount = new JTextField();
		JTextField cost = new JTextField();
		JTextField funds = new JTextField();
		JComboBox<String> fromAccount = new JComboBox<String>();

		Object[] message = { "Quantity:", desiredAmount, "Select Account:", fromAccount, };
		String title = "Enter information below.";
		ButtonLeaf bbutton = new ButtonLeaf("Buy", 279, 337, 221, 20, true);
		bbutton.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fromAccount.removeAllItems();
				for (String key : portfolio.getAccounts().keySet()) {
					fromAccount.addItem(key);
				}
				accountType.field.setText(Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getName());
				int option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String title = "Press Ok if everything looks correct.";
					funds.setText(Float.toString(
							portfolio.getAccounts().get(fromAccount.getSelectedItem().toString()).getCurrentAmount()));
					funds.setEditable(false);
					cost.setText(
							Float.toString(Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getPrice()
									* Integer.parseInt(desiredAmount.getText())));
					cost.setEditable(false);
					Object[] message = { "Cost:", cost, "Account funds:", funds, };
					option = JOptionPane.showConfirmDialog(desiredAmount, message, title, JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						portfolio.buyEquity(Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getTicker(), 
								Integer.parseInt(desiredAmount.getText()), 
								fromAccount.getSelectedItem().toString(), 
								Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getPrice());

					}
				}
			}
		});
		return bbutton.button;
	}
	
	// update the information text boxes with the chosen account
	public JButton chooseButton(Portfolio portfolio) {
		ButtonLeaf btnChoose = new ButtonLeaf("Choose", 510, 207, 89, 23, true);
		btnChoose.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					accountType.field.setText(Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getName());
					bPrice.field.setText(String.format("%.2f",
							(Main.getEquities().get(comboBox.comboBox.getSelectedItem().toString()).getPrice())));
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
					if (comboBox.comboBox.getSelectedItem().toString().contains("color")){
						String temp = comboBox.comboBox.getSelectedItem().toString();
						String s = "";
						for (int y = 0; y < temp.length(); y++){
							if (Character.isUpperCase(temp.charAt(y))){
								s = s+temp.charAt(y);
							}
						}
						sym = s;
					}
					else{
						sym = comboBox.comboBox.getSelectedItem().toString();
					}// bCurrentAmount.setText(String.format("%.2f",(mainToUse.sEquities.get(comboBox.getSelectedItem().toString()).getIndices().toString())));
					String iis = "";
					for (String s : Main.getEquities().get(sym).getIndices()){
						iis = iis+s+",";
					}
					if (iis.length()>0){
						bIndices.field.setText(iis.substring(0, iis.length()-1));
					}
					accountType.field.setText(Main.getEquities().get(sym).getName());
					if (portfolio.getOwnedEquities().get(sym) != null){
						owns.field.setText(portfolio.getOwnedEquities().get(sym).toString());
					}
					else{
						owns.field.setText("0");
					}
					bPrice.field.setText(String.format("%.2f",
							(Main.getEquities().get(sym).getPrice())));
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
