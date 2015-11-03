import javax.swing.JComboBox;

public class ComboBoxLeaf implements ViewComponent {
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();
	
	public ComboBoxLeaf() {
		System.out.println("You should add parameters to the comboBox instantiation");
	}
	
	public ComboBoxLeaf(String title, int x, int y, int width, int height, boolean isVisible) {
		this.setTitle(title);
		this.bounds(x, y, width, height);
		this.visible(isVisible);
	}
	
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		comboBox.setName(title);		
	}

	@Override
	public void bounds(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		comboBox.setBounds(x,y,width,height);
		
	}

	@Override
	public void visible(boolean isVisible) {
		// TODO Auto-generated method stub
		comboBox.setVisible(isVisible);
		
	}

	@Override
	public ViewComponent getComponent() {
		// TODO Auto-generated method stub
		return this;
	}

}
