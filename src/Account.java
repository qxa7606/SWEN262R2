import java.util.Date;

public abstract class Account {
	abstract String returnType();
	
	private String name;
	private float initialAmount;
	private float currentAmount;
	private Date dateAdded;

	public Account(String name, float initialAmount) {
		super();
		this.name = name;
		this.initialAmount = initialAmount;
		this.currentAmount = initialAmount;
		this.dateAdded = new Date();
	}
	
	public boolean Deposit(float amount){
		this.currentAmount += amount;
		return true;
	}
	
	public boolean Withdraw(float amount){
		if (this.currentAmount >= amount){
			this.currentAmount -= amount;
			return true;
		}
		
		return false;
	}
	
	public String getName() {
		return name;
	}

	public float getInitialAmount() {
		return initialAmount;
	}

	public float getCurrentAmount() {
		return currentAmount;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setCurrentAmount(float currentAmount) {
		this.currentAmount = currentAmount;
	}
}
