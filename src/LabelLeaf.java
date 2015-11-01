import javax.swing.JLabel;

public class LabelLeaf implements ViewComponent {
	JLabel label = new JLabel();

	public LabelLeaf() {
		System.out.println("You should add parameters to the label instantiation");
	}
	
	public LabelLeaf(String title, int x, int y, int width, int height, boolean isVisible) {
		this.setTitle(title);
		this.bounds(x, y, width, height);
		this.visible(isVisible);
	}
	
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		label.setName(title);
		label.setText(title);
		
	}

	@Override
	public void bounds(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		label.setBounds(x,y,width,height);
		
	}

	@Override
	public void visible(boolean isVisible) {
		// TODO Auto-generated method stub
		label.setVisible(isVisible);
		
	}

	@Override
	public ViewComponent getComponent() {
		// TODO Auto-generated method stub
		return this;
	}
}
