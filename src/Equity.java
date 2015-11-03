import java.util.ArrayList;
import java.util.List;

public class Equity {
	private String ticker;
	private String name;
	private float price;
	private List<String> indices = new ArrayList<String>();

	public Equity(String ticker, String name, float price, List<String> indices) {
		this.ticker = ticker;
		this.name = name;
		this.price = price;
		this.indices = indices;
	}

	public Equity() {
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setIndices(List<String> indices) {
		this.indices = indices;
	}

	public String getTicker() {
		return ticker;
	}
	public String getName() {
		return name;
	}
	public float getPrice() {
		return price;
	}
	public List<String> getIndices() {
		return indices;
	}
}