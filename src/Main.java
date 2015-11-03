import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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


	public static void main(String args[]) throws IOException, XMLStreamException{
		ImportEquities("equities.xml");
		ExportEquities("equities.xml");
		ImportWebService(equities);
		
		Timer timer = new Timer();
		timer.schedule(new Update(), 0, 30000);
		
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
	    			one.setPass(decrypt(pass));
	    			
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
