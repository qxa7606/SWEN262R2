
public class BankAccount extends Account{

	public BankAccount(String name, float initialAmount) {
		super(name, initialAmount);
	}

	@Override
	String returnType() {
		return "Bank";
	}

}
