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
	
	private List<EquityLog> elogs = new ArrayList<EquityLog>();
	private List<AccountLog> accountlogs = new ArrayList<AccountLog>();
	private List<TransferLog> tranferlogs = new ArrayList<TransferLog>();
	
	public Portfolio(String user, String pass, Account acc){
		this.user = user;
		this.pass = pass;
		this.accounts.put(acc.getName(), acc);
		
	}
	
	public Portfolio() {}

	public List<Log> getLogs() {
		return logs;
	}

	public boolean buyEquity(String tic, int num, String acc, float price){
		
		if (accounts.get(acc).getCurrentAmount()>= (num*price)){
			float old = accounts.get(acc).getCurrentAmount();
			float cost = num*price;
			//float n = old-(num*price);
			accounts.get(acc).setCurrentAmount(accounts.get(acc).getCurrentAmount()-cost);
			if (ownedEquities.containsKey(tic)){
				int o = ownedEquities.get(tic);
				ownedEquities.put(tic, o+num);
				elogs.add(new EquityLog(num,tic,acc,"Buy",num*price));
				logs.add(new EquityLog(num,tic,acc,"Buy",num*price));
				Main.pExport(new ExportAll());
				return true;
			}
			else{
				ownedEquities.put(tic, num);
				elogs.add(new EquityLog(num,tic,acc,"Buy",num*price));
				logs.add(new EquityLog(num,tic,acc,"Buy",num*price));
				Main.pExport(new ExportAll());
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
		Main.pExport(new ExportAll());
	}

	public void setAccounts(Map<String, Account> accounts) {
		this.accounts = accounts;
		Main.pExport(new ExportAll());
	}

	public void setWatchlist(List<String> watchlist) {
		this.watchlist = watchlist;
		Main.pExport(new ExportAll());
	}

	public void seteLogs(List<EquityLog> logs) {
		this.elogs = logs;
		Main.pExport(new ExportAll());
	}
	
	public void setLogs(List<Log> arrayList) {
		this.logs = arrayList;
	}

	public void setAccountlogs(List<AccountLog> accountlogs) {
		this.accountlogs = accountlogs;
		Main.pExport(new ExportAll());
	}

	public void setTranferlogs(List<TransferLog> tranferlogs) {
		this.tranferlogs = tranferlogs;
		Main.pExport(new ExportAll());
	}

	public boolean sellEquity(String tic, int num, String acc, float price){
		if (ownedEquities.get(tic)>=num){
			int old = ownedEquities.get(tic);
			int n = old-num;
			ownedEquities.put(tic, n);
			accounts.get(acc).setCurrentAmount(accounts.get(acc).getCurrentAmount()+(num*price));
			elogs.add(new EquityLog(num,tic,acc,"Sell",num*price));
			logs.add(new EquityLog(num,tic,acc,"Sell",num*price));
			if (ownedEquities.get(tic) == 0){
				ownedEquities.remove(tic);
			}
			Main.pExport(new ExportAll());
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
			logs.add(new TransferLog(fromAcc,toAcc,amount));
			Main.pExport(new ExportAll());
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
			Main.pExport(new ExportAll());
			return true;
		}
		else if (type.equals("Market")){
			accounts.put(name, new MarketAccount(name,amm));
			Main.pExport(new ExportAll());
			return true;
		}
		return false;
	}
	
	public boolean deposit(String acc, float amm){
		if (accounts.get(acc).Deposit(amm)){
			accountlogs.add(new AccountLog("Deposit",acc,amm));
			logs.add(new AccountLog("Deposit",acc,amm));
			Main.pExport(new ExportAll());
			return true;
		}
		return false;
	}
	
	public boolean withdraw(String acc, float amm){
		if (accounts.get(acc).Withdraw(amm)){
			accountlogs.add(new AccountLog("Withdraw",acc,amm));
			logs.add(new AccountLog("Withdraw",acc,amm));
			Main.pExport(new ExportAll());
			return true;
		}
		return false;
	}
	
	public boolean addToWatchlist(String eq){
		if (! watchlist.contains(eq)){
			watchlist.add(eq);
			Main.pExport(new ExportAll());
			return true;
		}
		return false;
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

	public List<EquityLog> geteLogs() {
		return elogs;
	}

	public List<AccountLog> getAccountlogs() {
		return accountlogs;
	}

	public List<TransferLog> getTransferlogs() {
		return tranferlogs;
	}
	

}
