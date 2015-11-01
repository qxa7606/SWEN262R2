import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainWindow implements ViewComponent {
	private Main mainToUse = null;
	
	LabelLeaf viewTitle = new LabelLeaf("FPTS", 202, 11, 397, 68, true);
	
	LabelLeaf usernameLabel = new LabelLeaf("Username: ", 210, 175, 75, 14, true);
	TextFieldLeaf usernameField = new TextFieldLeaf("username", 323, 171, 135, 23, true);
	
	LabelLeaf passwordLabel = new LabelLeaf("Password: ", 210, 212, 75, 14, true);
	PasswordFieldLeaf passwordField = new PasswordFieldLeaf("", 323, 205, 135, 23, true);
	
	ButtonLeaf signIn = new ButtonLeaf("Sign In", 352, 350, 89, 23, true);
	ButtonLeaf newUser = new ButtonLeaf("New User", 352, 375, 89, 23, true);
	
	JFrame frame = new JFrame();
	
	public MainWindow() {
		System.out.println("You should add parameters to the frame instantiation");
	}
	
	public MainWindow(String title, int x, int y, int width, int height, boolean isVisible) {
		this.setTitle(title);
		this.bounds(x, y, width, height);
		this.visible(isVisible);
	}
	
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		frame.setName(title);
		
	}

	@Override
	public void bounds(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		frame.setBounds(x,y,width,height);
		
	}

	@Override
	public void visible(boolean isVisible) {
		// TODO Auto-generated method stub
		frame.setVisible(isVisible);
		
	}

	@Override
	public ViewComponent getComponent() {
		// TODO Auto-generated method stub
		setupComponents();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		viewTitle.label.setHorizontalAlignment(SwingConstants.CENTER);
		viewTitle.label.setFont(new Font("Tahoma", Font.PLAIN, 31));
		frame.getContentPane().add(viewTitle.label);
		frame.getContentPane().add(usernameLabel.label);
		frame.getContentPane().add(usernameField.field);
		frame.getContentPane().add(passwordLabel.label);
		frame.getContentPane().add(passwordField.password);
		frame.getContentPane().add(signIn.button);
		frame.getContentPane().add(newUser.button);
		frame.setVisible(true);
		
		return this;
	}
	
	@SuppressWarnings("static-access")
	public void setupComponents() {
		if (getSystem() == null) {
			setSystem(new Main());
			getSystem().setMainWindow(this);
		}
		
		usernameField.field.setText("");
		passwordField.password.setText("");
		
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

		newUser.button.addActionListener(new ActionListener() {
			String title = "Enter a username and password below.";

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
		
		signIn.button.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Main.getPortfolios().get(usernameField.field.getText()) != null
						&& Main.getPortfolios().get(usernameField.field.getText()).getPass().equals(passwordField.password.getText())) {
					frame.setVisible(false);
					mainToUse.setPortfolioView(
							new PortfolioView(Main.getPortfolios().get(usernameField.field.getText()), mainToUse));
				} else {
					JOptionPane.showMessageDialog(null,
							usernameField.field.getText() + "\n" + passwordField.password.getText() + "\nis an invalid combination.");

				}
			}
		});
		
	}
	
	public void setSystem(Main system) {
		this.mainToUse = system;
	}

	public Main getSystem() {
		return this.mainToUse;
	}
}
