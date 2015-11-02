import java.util.Date;

public class TransferLog extends Log{

		private String fromAccount;
		private String toAccount;
		private float ammount;
		//private Date date;
		
		public TransferLog(String fromAccount, String toAccount, float ammount) {
			super(new Date());
			this.fromAccount = fromAccount;
			this.toAccount = toAccount;
			this.ammount = ammount;
			//this.date = new Date();
		}
		
		public String getFromAccount() {
			return fromAccount;
		}
		public String getToAccount() {
			return toAccount;
		}
		public float getAmmount() {
			return ammount;
		}
		
		
		
}
