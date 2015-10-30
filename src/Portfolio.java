import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Portfolio {
	private String user;
	private String pass;
	
	private Map<String, Integer> ownedEquities = new HashMap<String, Integer>();
	private Map<String,Account> accounts = new HashMap<String,Account>();
	private List<String> watchlist = new ArrayList<String>();
	
	private List<Log> logs = new ArrayList<Log>();
	private List<AccountLog> accountlogs = new ArrayList<AccountLog>();
	private List<TransferLog> tranferlogs = new ArrayList<TransferLog>();
	
	public Portfolio(String user, String pass, Account acc){
		this.user = user;
		this.pass = pass;
		this.accounts.put(acc.getName(), acc);
	}
	
	public Portfolio() {}

	public boolean buyEquity(String tic, int num, String acc, float price){
		
		if (accounts.get(acc).getCurrentAmount()>= (num*price)){
			float old = accounts.get(acc).getCurrentAmount();
			float cost = num*price;
			//float n = old-(num*price);
			accounts.get(acc).setCurrentAmount(accounts.get(acc).getCurrentAmount()-cost);
			if (ownedEquities.containsKey(tic)){
				int o = ownedEquities.get(tic);
				ownedEquities.put(tic, o+num);
				logs.add(new Log(num,tic,acc,"Buy",num*price));
				return true;
			}
			else{
				ownedEquities.put(tic, num);
				logs.add(new Log(num,tic,acc,"Buy",num*price));
				return true;
			}
		}
		return false;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setOwnedEquities(Map<String, Integer> ownedEquities) {
		this.ownedEquities = ownedEquities;
	}

	public void setAccounts(Map<String, Account> accounts) {
		this.accounts = accounts;
	}

	public void setWatchlist(List<String> watchlist) {
		this.watchlist = watchlist;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public void setAccountlogs(List<AccountLog> accountlogs) {
		this.accountlogs = accountlogs;
	}

	public void setTranferlogs(List<TransferLog> tranferlogs) {
		this.tranferlogs = tranferlogs;
	}

	public boolean sellEquity(String tic, int num, String acc, float price){
		if (ownedEquities.get(tic)>=num){
			int old = ownedEquities.get(tic);
			int n = old-num;
			ownedEquities.put(tic, n);
			accounts.get(acc).setCurrentAmount(accounts.get(acc).getCurrentAmount()+(num*price));
			logs.add(new Log(num,tic,acc,"Sell",num*price));
			if (ownedEquities.get(tic) == 0){
				ownedEquities.remove(tic);
			}
			return true;
		}
		return false;
	}
	
	public boolean transfer(String fromAcc, String toAcc, float amount){
		if (fromAcc.equals(toAcc)){
			return false;
		}
		if (accounts.get(fromAcc).getCurrentAmount()>=amount){
			accounts.get(fromAcc).setCurrentAmount(accounts.get(fromAcc).getCurrentAmount()-amount);
			accounts.get(toAcc).setCurrentAmount(accounts.get(toAcc).getCurrentAmount()+amount);
			tranferlogs.add(new TransferLog(fromAcc,toAcc,amount));
			return true;
		}
		return false;
	}
	
	public boolean addAccount(String type,String name, float amm){
		if (accounts.containsKey(name)){
			return false;
		}
		if (type.equals("Bank")){
			accounts.put(name, new BankAccount(name,amm));
			return true;
		}
		else if (type.equals("Market")){
			accounts.put(name, new MarketAccount(name,amm));
			return true;
		}
		return false;
	}
	
	public boolean deposit(String acc, float amm){
		if (accounts.get(acc).Deposit(amm)){
			accountlogs.add(new AccountLog("Deposit",acc,amm));
			return true;
		}
		return false;
	}
	
	public boolean withdraw(String acc, float amm){
		if (accounts.get(acc).Withdraw(amm)){
			accountlogs.add(new AccountLog("Withdraw",acc,amm));
			return true;
		}
		return false;
	}
	
	public boolean addToWatchlist(String eq){
		watchlist.add(eq);
		return true;
	}
	
	public boolean removeFromWatchlist(String tic){
		watchlist.remove(tic);
		return true;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public Map<String, Integer> getOwnedEquities() {
		return ownedEquities;
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public List<String> getWatchlist() {
		return watchlist;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public List<AccountLog> getAccountlogs() {
		return accountlogs;
	}

	public List<TransferLog> getTransferlogs() {
		return tranferlogs;
	}
	
	public List<Object> getRecent(List<Object> l){
		
		List<Object> lst = l;
		BeanComparator bc = new BeanComparator(Object.class, "getDate");
		Collections.sort(lst, bc);
		
		return lst;
	}

}
