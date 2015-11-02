
public abstract class Export {
	String filename;
	public Export(String file){
		this.filename = file;
	}
	public String getfilename() {
		return filename;
	}
	abstract void export();
}
