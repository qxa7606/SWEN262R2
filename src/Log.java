import java.util.Date;

public abstract class Log {
	private Date date;
	public Log(Date d){
		this.date = d;
	}
	public Date getDate() {
		return date;
	}
	
}
