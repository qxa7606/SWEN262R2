import javax.swing.JPasswordField;

public class PasswordFieldLeaf implements ViewComponent{
	JPasswordField password = new JPasswordField();

	public PasswordFieldLeaf() {
		System.out.println("You should add parameters to the password instantiation");
	}
	
	public PasswordFieldLeaf(String title, int x, int y, int width, int height, boolean isVisible) {
		this.setTitle(title);
		this.bounds(x, y, width, height);
		this.visible(isVisible);
	}
	
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		password.setName(title);
		
	}

	@Override
	public void bounds(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		password.setBounds(x,y,width,height);
		
	}

	@Override
	public void visible(boolean isVisible) {
		// TODO Auto-generated method stub
		password.setVisible(isVisible);
		
	}

	@Override
	public ViewComponent getComponent() {
		// TODO Auto-generated method stub
		return this;
	}
}
