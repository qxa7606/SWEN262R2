
public class MarketAccount extends Account{

	public MarketAccount(String name, float initialAmount) {
		super(name, initialAmount);
	}

	@Override
	String returnType() {
		return "Market";
	}

}
