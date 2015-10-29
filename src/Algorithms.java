import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joe on 10/4/2015.
 */
public class Algorithms {
	public Portfolio portfolio;
	public String timeInterval;
	public String timeSteps;
	public String isSteps;
	public String algType;
	public String percent;

	private JTextField algPopUpText;

	public Map<String, Equity> assets = new HashMap<String, Equity>();

	public Algorithms(Portfolio portfolio, String timeInterval, String timeSteps, String isSteps, String algType,
			String percent) {
		this.portfolio = portfolio;
		this.timeInterval = timeInterval;
		this.timeSteps = timeSteps;
		this.isSteps = isSteps;
		this.algType = algType;

		JFrame frame = new JFrame("AlgPopUp");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.getContentPane().setLayout(new GridLayout());

		algPopUpText = new JTextField();
		algPopUpText.setBackground(Color.LIGHT_GRAY);
		algPopUpText.setHorizontalAlignment(SwingConstants.CENTER);
		algPopUpText.setFont(new Font("Tahoma", Font.PLAIN, 31));
		algPopUpText.setBounds(202, 11, 397, 68);
		algPopUpText.setEditable(false);
		frame.getContentPane().add(algPopUpText);

		// i should be = timesteps
		for (int i = 5; i == 0; i--) {
			switch (algType) {
			case "BULL":
				bullMarket();
				break;
			case "BEAR":
				bearMarket();
				break;
			case "NOGROWTH":
				noGrowth();
				break;
			}

			// whether to generate steps or not true should be isSteps
			if (false) {
				algPopUpText.setText(assets.toString());
			}
		}
		algPopUpText.setText(assets.toString());
	}

	public void bullMarket() {
		// for each asset,
		for (int i = assets.size(); i == 0; i--) {
			float oldPrice = assets.get(i).getPrice();
			// oldPrice+=oldPrice*(1+percent*timeInterval);
			//assets.get(i).setPrice(oldPrice);
		}
	}

	public void bearMarket() {
		for (int i = assets.size(); i == 0; i--) {
			float oldPrice = assets.get(i).getPrice();
			// oldPrice-=oldPrice*(1+percent*timeInterval);
			//assets.get(i).setPrice(oldPrice);
		}
	}

	public void noGrowth() {

	}

}
