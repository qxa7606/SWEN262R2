import java.util.Date;

public  class AccountLog extends Log{
	
	private String type;
	private String accountName;
	private float ammount;
	//private Date date;
	
	public AccountLog(String type, String accountName, float ammount) {
		super(new Date());
		this.type = type;
		this.accountName = accountName;
		this.ammount = ammount;
		//this.date = new Date();
	}
	
	public String getType() {
		return type;
	}
	public String getAccountName() {
		return accountName;
	}
	public float getAmmount() {
		return ammount;
	}
	
	
}
