public interface ViewComponent {
	public void setTitle(String title);
	public void bounds(int x, int y, int width, int height);
	public void visible(boolean isVisible);
	public ViewComponent getComponent();
}
