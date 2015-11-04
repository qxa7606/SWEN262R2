import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExportOne implements Export{

	private Portfolio pp;
	private String fix=null;
	public ExportOne(String file, Portfolio p) {
		this.fix = file;
		//super(file);
		this.pp = p;
	}

	public void export() {
		String filename = fix+".xml";
		SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		Document doc = docBuilder.newDocument();
    		Element rootElement = doc.createElement("Portfolios");
    		doc.appendChild(rootElement);
    		

    			Element port = doc.createElement("Portfolio");
    			rootElement.appendChild(port);
    			
    			Element user = doc.createElement("Username");
    			user.appendChild(doc.createTextNode(pp.getUser()));
    			port.appendChild(user);
    			
    			Element pass = doc.createElement("Password");
    			pass.appendChild(doc.createTextNode(pp.getPass()));
    			port.appendChild(pass);
    			
    			Element own = doc.createElement("OwnedEquities");
    			//own.appendChild(doc.createTextNode(((Portfolio) pair.getValue()).getPass()));
    			Iterator i1 = pp.getOwnedEquities().entrySet().iterator();
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

    			Iterator i5 = pp.getAccounts().entrySet().iterator();
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
    			for (String s : pp.getWatchlist()){
    				//Element value = doc.createElement("Equity");
    				
    				Element name = doc.createElement("Ticker");
    				name.appendChild(doc.createTextNode(s));
    				
    				//value.appendChild(name);
    				
    				watch.appendChild(name);
    			}
    			port.appendChild(watch);
    			
    			Element logs = doc.createElement("Logs");
    			for (EquityLog lg : pp.geteLogs()){
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
    			for (AccountLog lg : (pp.getAccountlogs())){
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
    			for (TransferLog lg : pp.getTransferlogs()){
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
    		
    		
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);
    		if(filename!=null){
        		StreamResult result = new StreamResult(new File(filename));
        		transformer.transform(source, result);

    		}
    		
    		//StreamResult re =  new StreamResult(System.out);
            
        } catch (Throwable t) {
            t.printStackTrace ();
        }
		
	}

}
