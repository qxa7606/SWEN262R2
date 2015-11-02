import java.util.Date;

public class EquityLog extends Log{
	
	private String type;
	private int num;
	private String ticker;
	private float ammount;
	//private Date date;
	private String accountName;
	
	public EquityLog(int num, String ticker, String account, String type, float ammount) {
		super(new Date());
		this.ammount = ammount;
		this.num = num;
		this.ticker = ticker;
		this.accountName = account;
		this.type = type;
		//this.date = new Date();
	}

	public float getAmmount() {
		return ammount;
	}
	public int getNum() {
		return num;
	}

	public String getTicker() {
		return ticker;
	}

	public String getAccountName() {
		return accountName;
	}
	public String getType() {
		return type;
	}
	
}
