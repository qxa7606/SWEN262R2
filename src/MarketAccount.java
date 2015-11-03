
public class MarketAccount extends Account{

	public MarketAccount(String name, float initialAmount) {
		super(name, initialAmount);
	}

	public MarketAccount() {
		// TODO Auto-generated constructor stub
	}

	@Override
	String getType() {
		return "Market";
	}

}
