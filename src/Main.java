import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
	
	private static Map<String, Portfolio> portfolios 
		= new HashMap<String, Portfolio>();
	private static Map<String, Equity> equities
		= new HashMap<String, Equity>();
	
	private static MainWindow mainWindow;
	private static PortfolioView portfolioView;
	private static AccountView accountView;


	public static void main(String args[]){
		ImportEquities("equities.xml");
		ExportEquities("equities.xml");
		
		Portfolio p = new Portfolio("Qasim", "ABC123", new BankAccount("AccountFirst", 1000.0f));
		p.buyEquity("ABC", 2, "AccountFirst", 22.50f);
		p.buyEquity("EDF", 2, "AccountFirst", 33.97f);
		p.addToWatchlist("YTZ");
		p.addToWatchlist("XYZ");
		p.addAccount("Market", "AccountSecond", 3300.40f);
		p.transfer("AccountFirst", "AccountSecond", 50.0f);
		p.withdraw("AccountFirst", 20.55f);
		p.withdraw("AccountFirst", 10.55f);
		portfolios.put("Qasim", p);
		
		Portfolio p1 = new Portfolio("Asim", "MandT", new MarketAccount("firstAccount", 2500.0f));
		p1.buyEquity("ABC", 2, "firstAccount", 22.50f);
		p1.buyEquity("EDF", 2, "firstAccount", 33.97f);
		p1.addToWatchlist("TYU");
		p1.addToWatchlist("XYTZ");
		p1.addAccount("Bank", "secondAccount", 3300.40f);
		p1.sellEquity("ABC", 2, "firstAccount", 30.55f);
		portfolios.put("Asim", p1);
		
		mainWindow = new MainWindow();
		mainWindow.frame.setVisible(true);
		//ExportPortfolios("exportedPortfolios.xml");
		
		
	}
	
	public static boolean addUser(String username, String pass, String aType, String AName, float amount){
		if (aType.equals("Market")){
			Portfolio p = new Portfolio(username, pass, new MarketAccount(AName, amount));
			portfolios.put(username, p);
			return true;
		}
		else if (aType.equals("Bank")){
			Portfolio p = new Portfolio(username, pass, new BankAccount(AName, amount));
			portfolios.put(username, p);
			return true;
		}
		return false;
	}
	
	public static Map<String, Portfolio> getPortfolios() {
		return portfolios;
	}

	public static Map<String, Equity> getEquities() {
		return equities;
	}

	public static List<Equity> Search(String term){
		Iterator it = equities.entrySet().iterator();
		List<Equity> lst = new ArrayList();
		while (it.hasNext()){
			Map.Entry pair = (Map.Entry) it.next();
			Equity curr = (Equity) pair.getValue();
			if (curr.getName().contains(term) || curr.getTicker().contains(term)){
				lst.add(curr);			
			}
		}
		return lst;
	}
	
	
	
	
	public static void ImportPortfolios(String filename){
	    try {

	    	File fXmlFile = new File(filename);
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(fXmlFile);
	    			
	    	doc.getDocumentElement().normalize();
	
	    	NodeList nList = doc.getElementsByTagName("Portfolio");

	    	for (int temp = 0; temp < nList.getLength(); temp++) {

	    		Node port = nList.item(temp);
	    		String use = port.getFirstChild().getNodeValue();
	    		String pas = port.getFirstChild().getNextSibling().getNodeValue();
	    		
	    		
	    				
	    		if (port.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) port;
	    			
	    			Portfolio one = new Portfolio();
	    			
	    			String user = eElement.getElementsByTagName("User").item(0).getTextContent();
	    			String pass = eElement.getElementsByTagName("Password").item(0).getTextContent();
	    			one.setUser(user);
	    			one.setPass(pass);
	    			
	    			NodeList eq = eElement.getElementsByTagName("OwnedEquities");
	    			Map<String, Integer> eqs = new HashMap<String, Integer>();
	    			for (int i=0;i<eq.getLength();i++){
	    				Element value = (Element) eq.item(i);
	    				NodeList val = value.getElementsByTagName("Value");
	    			}
	    			
	    			
	    			NodeList bacc = eElement.getElementsByTagName("BankAccounts");
	    			NodeList macc = eElement.getElementsByTagName("MarketAccounts");
	    			NodeList weq = eElement.getElementsByTagName("WatchListEquities");
	    			
	    			NodeList log = eElement.getElementsByTagName("Logs");
	    			NodeList alog = eElement.getElementsByTagName("AccountLogs");
	    			NodeList tlog = eElement.getElementsByTagName("TransferLogs");
	    			
	    			
	    			

	    			}
	    		}
	        } catch (Exception e) {
	    	e.printStackTrace();
	        }
	}
	
	
	public static void ExportPortfolios(){
		String filename = "ExportedPortfolios.xml";
		SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		Document doc = docBuilder.newDocument();
    		Element rootElement = doc.createElement("Portfolios");
    		doc.appendChild(rootElement);
    		
    		Iterator it = portfolios.entrySet().iterator();
    		
    		while (it.hasNext()){
    			Map.Entry pair = (Map.Entry)it.next();
    			
    			//System.out.print(pair.getKey().toString().trim());
    			//System.out.println("  :  "+((Equity) pair.getValue()).getName());
    			
    			Element port = doc.createElement("Portfolio");
    			rootElement.appendChild(port);
    			
    			Element user = doc.createElement("Username");
    			user.appendChild(doc.createTextNode(pair.getKey().toString().trim()));
    			port.appendChild(user);
    			
    			Element pass = doc.createElement("Password");
    			pass.appendChild(doc.createTextNode(((Portfolio) pair.getValue()).getPass()));
    			port.appendChild(pass);
    			
    			Element own = doc.createElement("OwnedEquities");
    			//own.appendChild(doc.createTextNode(((Portfolio) pair.getValue()).getPass()));
    			Iterator i1 = ((Portfolio) pair.getValue()).getOwnedEquities().entrySet().iterator();
    			while (i1.hasNext()){
    				Map.Entry oown = (Map.Entry)i1.next();
    				
    				Element value = doc.createElement("Value");
    				String s = oown.getKey().toString()+","+oown.getValue().toString();
    				
    				value.appendChild(doc.createTextNode(s));
    				
    				own.appendChild(value);
    			}
    			port.appendChild(own);
    			
    			Element bank = doc.createElement("BankAccounts");
    			Element market = doc.createElement("MarketAccounts");

    			Iterator i5 = ((Portfolio) pair.getValue()).getAccounts().entrySet().iterator();
    			while (i5.hasNext()){
    				Map.Entry c = (Map.Entry)i5.next();
    				
    				if (((Account) c.getValue()) instanceof BankAccount){
    	    			Element acc = doc.createElement("BankAccount");
    	    			
    	    			Element name = doc.createElement("Name");
    	    			Element initial = doc.createElement("InitialAmount");
    	    			Element current = doc.createElement("CurrentAmount");
    	    			Element date = doc.createElement("DateCreated");
    	    			
    	    			name.appendChild(doc.createTextNode(c.getKey().toString()));
    	    			initial.appendChild(doc.createTextNode(Float.toString(((Account) c.getValue()).getInitialAmount())));
    	    			current.appendChild(doc.createTextNode(Float.toString(((Account) c.getValue()).getCurrentAmount())));
    	    			date.appendChild(doc.createTextNode(((Account) c.getValue()).getDateAdded().toString()));
    	    			
    	    			acc.appendChild(name);
    	    			acc.appendChild(initial);
    	    			acc.appendChild(current);
    	    			acc.appendChild(date);
    	    			
    	    			bank.appendChild(acc);

    				}
    				else if (((Account) c.getValue()) instanceof MarketAccount){
    	    			Element acc = doc.createElement("MarketAccount");
    	    			
    	    			Element name = doc.createElement("Name");
    	    			Element initial = doc.createElement("InitialAmount");
    	    			Element current = doc.createElement("CurrentAmount");
    	    			Element date = doc.createElement("DateCreated");
    	    			
    	    			name.appendChild(doc.createTextNode(c.getKey().toString()));
    	    			initial.appendChild(doc.createTextNode(Float.toString(((Account) c.getValue()).getInitialAmount())));
    	    			current.appendChild(doc.createTextNode(Float.toString(((Account) c.getValue()).getCurrentAmount())));
    	    			date.appendChild(doc.createTextNode(((Account) c.getValue()).getDateAdded().toString()));
    	    			
    	    			acc.appendChild(name);
    	    			acc.appendChild(initial);
    	    			acc.appendChild(current);
    	    			acc.appendChild(date);
    	    			
    	    			market.appendChild(acc);
    				}
    			}
    			
    			port.appendChild(bank);
    			port.appendChild(market);
    			
    			Element watch = doc.createElement("WatchListEquities");
    			for (String s : ((Portfolio) pair.getValue()).getWatchlist()){
    				//Element value = doc.createElement("Equity");
    				
    				Element name = doc.createElement("Ticker");
    				name.appendChild(doc.createTextNode(s));
    				
    				//value.appendChild(name);
    				
    				watch.appendChild(name);
    			}
    			port.appendChild(watch);
    			
    			Element logs = doc.createElement("Logs");
    			for (Log lg : ((Portfolio) pair.getValue()).getLogs()){
    				Element log = doc.createElement("Log");
    				
    				Element type = doc.createElement("Type");
    				Element tic = doc.createElement("Ticker");
	    			Element num = doc.createElement("Number");
	    			Element acc = doc.createElement("Account");
	    			Element amm = doc.createElement("Ammount");
	    			Element date = doc.createElement("Date");
	    			
	    			type.appendChild(doc.createTextNode(lg.getType()));
	    			tic.appendChild(doc.createTextNode(lg.getTicker()));
	    			num.appendChild(doc.createTextNode(Integer.toString(lg.getNum())));
	    			amm.appendChild(doc.createTextNode(Float.toString(lg.getAmmount())));
	    			acc.appendChild(doc.createTextNode(lg.getAccountName()));
	    			date.appendChild(doc.createTextNode(lg.getDate().toString()));
	    			
	    			log.appendChild(type);
	    			log.appendChild(tic);
	    			log.appendChild(num);
	    			log.appendChild(acc);
	    			log.appendChild(amm);
	    			log.appendChild(date);
	    			
	    			logs.appendChild(log);
    			}
    			port.appendChild(logs);
    			
    			
    			Element alogs = doc.createElement("AccountLogs");
    			for (AccountLog lg : ((Portfolio) pair.getValue()).getAccountlogs()){
    				Element alog = doc.createElement("AccountLog");
    				
    				Element type = doc.createElement("Type");
    				Element aname = doc.createElement("AccountName");
	    			Element amount = doc.createElement("Amount");
	    			Element date = doc.createElement("Date");
	    			
	    			type.appendChild(doc.createTextNode(lg.getType()));
	    			aname.appendChild(doc.createTextNode(lg.getAccountName()));
	    			amount.appendChild(doc.createTextNode(Float.toString(lg.getAmmount())));
	    			date.appendChild(doc.createTextNode(lg.getDate().toString()));
	    			
	    			
	    			alog.appendChild(type);
	    			alog.appendChild(aname);
	    			alog.appendChild(amount);
	    			alog.appendChild(date);
	    			
	    			alogs.appendChild(alog);
    			}
    			port.appendChild(alogs);
    			
    			
    			Element tlogs = doc.createElement("TransferLogs");
    			for (TransferLog lg : ((Portfolio) pair.getValue()).getTransferlogs()){
    				Element tlog = doc.createElement("TransferLog");
    				
    				Element fr = doc.createElement("FromAccount");
    				Element to = doc.createElement("ToAccount");
	    			Element am = doc.createElement("Amount");
	    			Element date = doc.createElement("Date");
	    			
	    			fr.appendChild(doc.createTextNode(lg.getFromAccount()));
	    			to.appendChild(doc.createTextNode(lg.getToAccount()));
	    			am.appendChild(doc.createTextNode(Float.toString(lg.getAmmount())));
	    			date.appendChild(doc.createTextNode(lg.getDate().toString()));
	    			
	    			tlog.appendChild(fr);
	    			tlog.appendChild(to);
	    			tlog.appendChild(am);
	    			tlog.appendChild(date);
	    			
	    			tlogs.appendChild(tlog);
    			}
    			port.appendChild(tlogs);
    		}
    		
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);
    		StreamResult result = new StreamResult(new File(filename));
    		
    		//StreamResult re =  new StreamResult(System.out);
    		transformer.transform(source, result);
            
        } catch (Throwable t) {
            t.printStackTrace ();
        }
	}
	
	
	
	public static void ImportEquities (String filename){
	    try {

	    	File fXmlFile = new File(filename);
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(fXmlFile);
	    			
	    	doc.getDocumentElement().normalize();
	
	    	NodeList nList = doc.getElementsByTagName("Equity");

	    	for (int temp = 0; temp < nList.getLength(); temp++) {

	    		Node nNode = nList.item(temp);

	    				
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;
	    			
	    			String tick = eElement.getElementsByTagName("Ticker").item(0).getTextContent();
	    			String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
	    			float price = Float.parseFloat(eElement.getElementsByTagName("Price").item(0).getTextContent());
	    			String secs = eElement.getElementsByTagName("Sector").item(0).getTextContent();
	    			
	    			List<String> indices = new ArrayList<String>();
	    			String[] s = secs.split(",");
	    			for (String in : s){
	    				indices.add(in);
	    			}
	    			
	    			equities.put(tick, new Equity(tick,name,price,indices));
	    			
	    			}
	    		}
	        } catch (Exception e) {
	    	e.printStackTrace();
	        }
	}
	
	
	
	public static void ExportEquities(String filename){
		SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		Document doc = docBuilder.newDocument();
    		Element rootElement = doc.createElement("Equities");
    		doc.appendChild(rootElement);
    		
    		Iterator it = equities.entrySet().iterator();
    		
    		while (it.hasNext()){
    			Map.Entry pair = (Map.Entry)it.next();
    			
    			//System.out.print(pair.getKey().toString().trim());
    			//System.out.println("  :  "+((Equity) pair.getValue()).getName());
    			
    			Element equit = doc.createElement("Equity");
    			rootElement.appendChild(equit);
    			
    			Element tic = doc.createElement("Ticker");
    			tic.appendChild(doc.createTextNode(pair.getKey().toString().trim()));
    			equit.appendChild(tic);
    			
    			Element name = doc.createElement("Name");
    			name.appendChild(doc.createTextNode(((Equity) pair.getValue()).getName()));
    			equit.appendChild(name);
    			
    			Element price = doc.createElement("Price");
    			price.appendChild(doc.createTextNode(Float.toString(((Equity) pair.getValue()).getPrice())));
    			equit.appendChild(price);
    			
    			String secs = "";
    			for (String s : ((Equity) pair.getValue()).getIndices()){
    				secs = secs+s+",";
    			}
    			Element inde = doc.createElement("Sector");
    			inde.appendChild(doc.createTextNode(secs.substring(0, secs.length()-1)));
    			equit.appendChild(inde);
    		}
    		
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);
    		StreamResult result = new StreamResult(new File(filename));
    		
    		//StreamResult re =  new StreamResult(System.out);
    		transformer.transform(source, result);
            
        } catch (Throwable t) {
            t.printStackTrace ();
        }
	}
	
	
	public static void setMainWindow(MainWindow mainWindow) {
		Main.mainWindow = mainWindow;
	}

	public static void setPortfolioView(PortfolioView portfolioView) {
		Main.portfolioView = portfolioView;
	}

	public static MainWindow getMainWindow() {
		return mainWindow;
	}

	public static PortfolioView getPortfolioView() {
		return portfolioView;
	}

	public static AccountView getAccountView() {
		return accountView;
	}

	public static void setAccountView(AccountView accountView) {
		Main.accountView = accountView;
	}
	
}
