import java.util.Date;

public class BankAccount extends Account{

	public BankAccount(String name, float initialAmount) {
		super(name, initialAmount);
	}

	@Override
	String getType() {
		return "Bank";
	}


}
