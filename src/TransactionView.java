import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class TransactionView extends JFrame {
	JFrame frame = new JFrame();
	Portfolio portfolio;
	Main mainToUse;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public TransactionView(Portfolio portfolio, Main system) {
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
		frameTitle.setText("Transaction Information");
		frameTitle.setEditable(false);
		frame.getContentPane().add(frameTitle);
		frameTitle.setColumns(10);

		JLabel lblTransactionType = new JLabel("<html>Transaction<br>Type</html>");
		lblTransactionType.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransactionType.setBounds(178, 107, 76, 40);
		frame.getContentPane().add(lblTransactionType);

		JLabel lblFromAccount = new JLabel("<html>From<br>Account</html>");
		lblFromAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblFromAccount.setBounds(230, 110, 104, 34);
		frame.getContentPane().add(lblFromAccount);

		JLabel lblToAccount = new JLabel("<html>To<br>Account</html>");
		lblToAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblToAccount.setBounds(300, 110, 115, 34);
		frame.getContentPane().add(lblToAccount);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setBounds(480, 123, 106, 14);
		frame.getContentPane().add(lblAmount);

		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(565, 123, 83, 14);
		frame.getContentPane().add(lblDate);

		frame.getContentPane().add(backButton(portfolio));
		frame.getContentPane().add(transactionTable(portfolio));

		JLabel lblEquity = new JLabel("Equity");
		lblEquity.setBounds(407, 123, 46, 14);
		frame.getContentPane().add(lblEquity);

	}

	public JScrollPane transactionTable(Portfolio portfolio) {
		List<Log> trans = portfolio.getLogs();
		int rows = trans.size();
		table = new JTable(rows, 6);
		table.setShowGrid(true);
		JPanel container = new JPanel();
		container.add(table);

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < rows; i++) {
			System.out.println(trans.get(i).getDate());

		}
		JScrollPane jsp = new JScrollPane(container);
		// table.setBounds(80, 138, 608, 276);
		jsp.setBounds(158, 150, 504, 240);

		return jsp;
	}

	public JButton backButton(Portfolio portfolio) {
		JButton viewBankAccount = new JButton("Back");
		viewBankAccount.setBounds(279, 435, 221, 23);
		viewBankAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				mainToUse.getAccountView();
				mainToUse.getAccountView().frame.setVisible(true);
			}
		});
		return viewBankAccount;
	}
}
