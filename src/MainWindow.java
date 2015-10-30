import java.awt.EventQueue;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import javax.swing.JLabel;

public class MainWindow {

	public static JFrame frame;
	private JTextField txtFpts;
	private JFormattedTextField UserName;
	private JPasswordField PassWord;
	private Main mainToUse = null;
	private JLabel lblUsername;
	private JLabel lblPassword;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() {
	 * 
	 * @Override public void run() { try { MainWindow window = new MainWindow();
	 * window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	public void setSystem(Main system) {
		this.mainToUse = system;
	}

	public Main getSystem() {
		return this.mainToUse;
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {

		if (getSystem() == null) {
			setSystem(new Main());
			getSystem().setMainWindow(this);
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 824, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtFpts = new JTextField();
		txtFpts.setBackground(Color.LIGHT_GRAY);
		txtFpts.setHorizontalAlignment(SwingConstants.CENTER);
		txtFpts.setFont(new Font("Tahoma", Font.PLAIN, 31));
		txtFpts.setText("FPTS");
		txtFpts.setBounds(202, 11, 397, 68);
		txtFpts.setEditable(false);
		frame.getContentPane().add(txtFpts);
		txtFpts.setColumns(10);

		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Main.getPortfolios().get(UserName.getText()) != null
						&& Main.getPortfolios().get(UserName.getText()).getPass().equals(PassWord.getText())) {
					frame.setVisible(false);
					mainToUse.setPortfolioView(
							new PortfolioView(Main.getPortfolios().get(UserName.getText()), mainToUse));
				} else {
					JOptionPane.showMessageDialog(null,
							UserName.getText() + "\n" + PassWord.getText() + "\nis an invalid combination.");

				}
			}
		});
		btnSignIn.setBounds(352, 350, 89, 23);
		frame.getContentPane().add(btnSignIn);

		JButton btnNewUser = new JButton("New User");
		JTextField desiredName = new JTextField();
		JPasswordField desiredPw = new JPasswordField();
		JComboBox<String> acntType = new JComboBox<String>();
		acntType.addItem("Bank");
		acntType.addItem("Market");
		JTextField acntName = new JTextField();
		JTextField acntWorth = new JTextField();
		Object[] message = { "Input desired username:", desiredName, "Input desired password:", desiredPw,
				"Input account type (bank or market):", acntType, "Input account name:", acntName,
				"Input deposit amount:", acntWorth };

		btnNewUser.addActionListener(new ActionListener() {
			String title = "Enter a username and password below.";

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(desiredPw, message, title, JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (Main.getPortfolios().get(desiredName.getText()) == null) {
						if (mainToUse.addUser(desiredName.getText(), 
								new String(desiredPw.getPassword()), 
								acntType.getSelectedItem().toString(), 
								acntName.getText(), 
								Float.parseFloat(acntWorth.getText()))){
								Main.ExportPortfolios();
								title = "Success";
								actionPerformed(arg0);
						}
						else{
							title = "Account creation failed";
							actionPerformed(arg0);
						}
						
					} else {
						title = "That username is taken.";
						actionPerformed(arg0);
					}
				}
			}
		});
		btnNewUser.setBounds(352, 375, 89, 23);
		frame.getContentPane().add(btnNewUser);

		UserName = new JFormattedTextField();
		UserName.setBounds(323, 171, 135, 23);
		frame.getContentPane().add(UserName);

		PassWord = new JPasswordField();
		PassWord.setBounds(323, 205, 135, 23);
		frame.getContentPane().add(PassWord);

		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(210, 175, 65, 14);
		frame.getContentPane().add(lblUsername);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(210, 212, 65, 14);
		frame.getContentPane().add(lblPassword);
	}
}
