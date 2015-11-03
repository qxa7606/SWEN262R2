
public class BankAccount extends Account{

	public BankAccount(String name, float initialAmount) {
		super(name, initialAmount);
	}

	public BankAccount() {
		// TODO Auto-generated constructor stub
	}

	@Override
	String getType() {
		return "Bank";
	}

}
