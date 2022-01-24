package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver, ActionListener{

	private SushiGameModel game_model;
	private JLabel display;
	//creating a panel ranker
	JPanel ranker = new JPanel();
	//compare based on balance the first time
	Comparator<Chef> base = new HighToLowBalanceComparator();
	//keep the previous button in mind to change its color back to null
	JButton previousButton = new JButton("hi"); 

	public ScoreboardWidget(SushiGameModel gm) {
		ranker.setLayout(new BorderLayout());
		JLabel ranking = new JLabel("   Ranking");
		ranking.setPreferredSize(new Dimension(50, 50));
		ranker.add(ranking, BorderLayout.NORTH);
		
		JButton foodSpoiled = new JButton("Food spoiled");
		foodSpoiled.setActionCommand("spoiled");
		foodSpoiled.addActionListener(this);
		ranker.add(foodSpoiled, BorderLayout.WEST);
		
		JButton balance = new JButton("balance");
		balance.setActionCommand("balance");
		balance.addActionListener(this);
		ranker.add(balance, BorderLayout.CENTER);
		
		JButton foodConsumed = new JButton ("Food Consumed");
		foodConsumed.setActionCommand("consumed");
		foodConsumed.addActionListener(this);
		ranker.add(foodConsumed, BorderLayout.EAST);
		
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);
		
		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardHTML(base));
		add(ranker, BorderLayout.NORTH);
	}

	private String makeScoreboardHTML(Comparator<Chef> base) {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		Arrays.sort(chefs, base);
		
		for (Chef c : chefs) {
			sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
		}
		return sb_html;
	}

	public void refresh() {
		display.setText(makeScoreboardHTML(base));		
	}
	
	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(e.getActionCommand().equals("spoiled")) {
			base = new HighToLowSpoilComparator();
			makeScoreboardHTML(base);
			refresh();
			previousButton.setBackground(null);
			previousButton = button;
			button.setBackground(Color.ORANGE);
		} else if (e.getActionCommand().equals("balance")) {
			base = new HighToLowBalanceComparator();
			makeScoreboardHTML(base);
			previousButton.setBackground(null);
			previousButton = button;
			button.setBackground(Color.ORANGE);
			refresh();
		} else if (e.getActionCommand().equals("consumed")) {
			base = new HighToLowConsumeComparator();
			makeScoreboardHTML(base);
			previousButton.setBackground(null);
			previousButton = button;
			button.setBackground(Color.ORANGE);
			refresh();
		}
		
	}

}
