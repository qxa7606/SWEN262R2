
public class MarketAccount extends Account{

	public MarketAccount(String name, float initialAmount) {
		super(name, initialAmount);
	}

	@Override
	String getType() {
		return "Market";
	}

}
