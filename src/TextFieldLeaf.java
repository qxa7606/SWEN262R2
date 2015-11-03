import javax.swing.JButton;
import javax.swing.JTextField;

public class TextFieldLeaf implements ViewComponent {
	JTextField field = new JTextField();

	public TextFieldLeaf() {
		System.out.println("You should add parameters to the button instantiation");
	}
	
	public TextFieldLeaf(String title, int x, int y, int width, int height, boolean isVisible) {
		this.setTitle(title);
		this.bounds(x, y, width, height);
		this.visible(isVisible);
	}
	
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		field.setName(title);
		
	}

	@Override
	public void bounds(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		field.setBounds(x,y,width,height);
		
	}

	@Override
	public void visible(boolean isVisible) {
		// TODO Auto-generated method stub
		field.setVisible(isVisible);
		
	}

	@Override
	public ViewComponent getComponent() {
		// TODO Auto-generated method stub
		return this;
	}
}
