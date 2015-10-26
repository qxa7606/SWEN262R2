import java.util.Date;

public class Log {
	
	private String type;
	private int num;
	private String ticker;
	private float ammount;
	private Date date;



	private String accountName;
	
	public Log(int num, String ticker, String account, String type, float ammount) {
		super();
		this.ammount = ammount;
		this.num = num;
		this.ticker = ticker;
		this.accountName = account;
		this.type = type;
		this.date = new Date();
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

	public Date getDate() {
		return date;
	}

	public String getAccountName() {
		return accountName;
	}
	public String getType() {
		return type;
	}
	
}
