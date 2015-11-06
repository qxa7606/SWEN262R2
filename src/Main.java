import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
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


	public static void main(String args[]) throws IOException, XMLStreamException, ParseException{
		ImportEquities("equities.xml");
		ExportEquities("equities.xml");
		ImportPortfolios("exportedPortfolios.xml");
		ImportWebService(equities);
		
		Timer timer = new Timer();
		timer.schedule(new Update(), 0, 10000);
		
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
	
	
	
	
	@SuppressWarnings("null")
	public static void ImportPortfolios(String filename) throws FileNotFoundException, XMLStreamException, ParseException{
	    
		
		String tagContent = null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(new FileReader("exportedPortfolios.xml"));
		BankAccount bAccount = null;
		MarketAccount mAccount = null;
		Portfolio currPortfolio = null;
		int whichType = 0; //1 is bank, 2 is market
		while(reader.hasNext()){
			int event = reader.next();

			switch(event){
			
			case XMLStreamConstants.START_ELEMENT: 
				switch(reader.getLocalName()){
				case "Portfolio":
					currPortfolio = new Portfolio();
					currPortfolio.setOwnedEquities(new HashMap<String, Integer>());
					currPortfolio.setAccounts(new HashMap<String, Account>());
					currPortfolio.setLogs(new ArrayList<Log>());
					currPortfolio.setWatchlist(new ArrayList<String>());
					break;
					
				case "BankAccount":
					bAccount = new BankAccount();
					whichType = 1;
					break;
					
				case "MarketAccount":
					mAccount = new MarketAccount();
					whichType = 2;
					break;
				}
				break;
				
			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
				
				break;

			case XMLStreamConstants.END_ELEMENT:
				//portfolio info
				switch(reader.getLocalName()){
				case "Portfolio":
					portfolios.put(currPortfolio.getUser(), currPortfolio);
					break;
				case "Username":
					currPortfolio.setUser(tagContent);
					break;
				case "Password":
					currPortfolio.setPass(decrypt(tagContent));
					break;
				//owned equities
				case "Value":
					String[] eInfo = tagContent.split(",");
					currPortfolio.getOwnedEquities().put(eInfo[0], Integer.parseInt(eInfo[1]));
					break;
				//account info
				case "BankAccount":
					currPortfolio.getAccounts().put(bAccount.getName(), bAccount);
					break;
				case "MarketAccount":
					currPortfolio.getAccounts().put(mAccount.getName(), mAccount);
				case "Name":
					if (whichType == 1) {
						bAccount.setName(tagContent);
					} else if(whichType == 2) {
						mAccount.setName(tagContent);
					}
					break;
				case "InitialAmount":
					if (whichType == 1) {
						bAccount.setInitialAmount(Float.parseFloat(tagContent));
					} else if(whichType == 2) {
						mAccount.setInitialAmount(Float.parseFloat(tagContent));
					}
					break;
				case "CurrentAmount":
					if (whichType == 1) {
						bAccount.setCurrentAmount(Float.parseFloat(tagContent));
					} else if(whichType == 2) {
						mAccount.setCurrentAmount(Float.parseFloat(tagContent));
					}
					break;
				case "DateCreated":
					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));
					Date d = sdf.parse(tagContent);
					if (whichType == 1) {
						bAccount.setDateAdded(d);
					} else if(whichType == 2) {
						mAccount.setDateAdded(d);
					}
					break;
				// equity watchlist
				case "Ticker":
					currPortfolio.getWatchlist().add(tagContent);
					break;
					
				//transfer logs
				case "Transfer Log":
					//currPortfolio.getTransferlogs().add(currTLog);
					break;
				case "Equity Log":
					//currPortfolio.getTransferlogs().add(currTLog);
					break;
				case "Account Log":
					//currPortfolio.getTransferlogs().add(currTLog);
					break;
				}
				
				break;
			}	
		}
	}
	
	
	
	public static void pExport(Export e){
		e.export();
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
	
	public static String encrypt(String s) {
		String from = "abcdefghijklmnopqrstuvwxyz5430987621";
		String to = "qwertyuiopasdfghjklzxcvbnm0987654321";
		Map<Character, Character> map = new HashMap<Character, Character>();
		String re = "";
		for (int i = 0; i < from.length(); ++i) {
			map.put(from.charAt(i), to.charAt(i));
		}
		for (char c : s.toCharArray()) {
			re = re + map.get(c);
		}
		return re;

	}

	public static String decrypt(String s) {
		String from = "abcdefghijklmnopqrstuvwxyz5430987621";
		String to = "qwertyuiopasdfghjklzxcvbnm0987654321";
		Map<Character, Character> map = new HashMap<Character, Character>();
		String re = "";
		for (int i = 0; i < from.length(); ++i) {
			map.put(to.charAt(i), from.charAt(i));
		}
		for (char c : s.toCharArray()) {
			re = re + map.get(c);
		}
		return re;
	}
	
	public static void ImportWebService (Map<String,Equity> lst) throws IOException, XMLStreamException{
		String url = "http://query.yahooapis.com/v1/public/yql?q=select%20Symbol,LastTradePriceOnly,Name%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22AAPL,AXP,BA,CAT,CSCO,CVX,DD,DIS,GE,GS,HD,IBM,INTC,JNJ,JPM,KO,MCD,MMM,MRK,MSFT,NKE,PFE,PG,TRV,UNH,UTX,V,VZ,WMT,XOM%22%29&env=store://datatables.org/alltableswithkeys";
		BufferedWriter writer = new BufferedWriter(new FileWriter("response.xml"));

		// Create a URL and open a connection
		URL YahooURL = new URL(url);
		HttpURLConnection con = (HttpURLConnection) YahooURL.openConnection();

		// Set the HTTP Request type method to GET (Default: GET)
		con.setRequestMethod("GET");
		con.setConnectTimeout(10000);
		con.setReadTimeout(10000);

		// Created a BufferedReader to read the contents of the request.
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		// MAKE SURE TO CLOSE YOUR CONNECTION!
		in.close();

		//writing to a xml file
		try{

			// response is the contents of the XML
			writer.write(response.toString());
			//System.out.println(response.toString());
		} finally {
			if (writer != null) writer.close();
		}

		//parsing

		String tagContent = null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(new FileReader("response.xml"));

		Equity currStock = null;
		while(reader.hasNext()){
			int event = reader.next();

			switch(event){
			case XMLStreamConstants.START_ELEMENT: 
				if("quote".equals(reader.getLocalName())){
					currStock = new Equity();
				}
				break;

			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
				break;

			case XMLStreamConstants.END_ELEMENT:
				switch(reader.getLocalName()){
				case "quote":
					lst.put(currStock.getTicker(), currStock);
					break;
				case "Symbol":
					currStock.setTicker(tagContent);
					break;
				case "LastTradePriceOnly":
					currStock.setPrice(Float.parseFloat(tagContent));
					break;
				case "Name":
					currStock.setName(tagContent);
					break;
				}
				break;
			}	
		}
	}
}
