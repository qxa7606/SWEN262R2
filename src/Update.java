import java.io.IOException;
import java.util.TimerTask;

import javax.xml.stream.XMLStreamException;

public class Update extends TimerTask {

	@Override
	public void run() {
		try {
			Main.ImportWebService(Main.getEquities());
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

}
