
import javax.swing.JButton;

public class ButtonLeaf implements ViewComponent{
	JButton button = new JButton();

	public ButtonLeaf() {
		System.out.println("You should add parameters to the button instantiation");
	}
	
	public ButtonLeaf(String title, int x, int y, int width, int height, boolean isVisible) {
		this.setTitle(title);
		this.bounds(x, y, width, height);
		this.visible(isVisible);
	}
	
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		button.setName(title);
		button.setText(title);
		
	}

	@Override
	public void bounds(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		button.setBounds(x,y,width,height);
		
	}

	@Override
	public void visible(boolean isVisible) {
		// TODO Auto-generated method stub
		button.setVisible(isVisible);
		
	}

	@Override
	public ViewComponent getComponent() {
		// TODO Auto-generated method stub
		return this;
	}

}
