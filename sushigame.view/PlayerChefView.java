package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401.sushi.AvocadoPortion;
import comp401.sushi.EelPortion;
import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri;
import comp401.sushi.Plate;
import comp401.sushi.RedPlate;
import comp401.sushi.Roll;
import comp401.sushi.Sashimi;
import comp401.sushi.SeaweedPortion;
import comp401.sushi.Sushi;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private Sushi kmp_roll;
	private Sushi crab_sashimi;
	private Sushi eel_nigiri;
	private int belt_size;
	//me
	PlayerViewUnlimited player;
	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
/* original code
		JButton sashimi_button = new JButton("Make red plate of crab sashimi at position 3");
		sashimi_button.setActionCommand("red_crab_sashimi_at_3");
		sashimi_button.addActionListener(this);
		add(sashimi_button);

		JButton nigiri_button = new JButton("Make blue plate of eel nigiri at position 8");
		nigiri_button.setActionCommand("blue_eel_nigiri_at_8");
		nigiri_button.addActionListener(this);
		add(nigiri_button);

		JButton roll_button = new JButton("Make gold plate of KMP roll at position 5");
		roll_button.setActionCommand("gold_kmp_roll_at_5");
		roll_button.addActionListener(this);
		add(roll_button);

		kmp_roll = new Roll("KMP Roll", new IngredientPortion[] {new EelPortion(1.0), new AvocadoPortion(0.5), new SeaweedPortion(0.2)});
		crab_sashimi = new Sashimi(Sashimi.SashimiType.CRAB);
		eel_nigiri = new Nigiri(Nigiri.NigiriType.EEL);
*/
		//the select button
		JButton select = new JButton("select");
		select.setToolTipText("Make my sushi");
		select.setActionCommand("select");
		select.addActionListener(this);
		
		player = new PlayerViewUnlimited();
		player.setBackground(Color.lightGray);

		
		add(player);
		
		add(select);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String plateColor = player.getColorChoice();
		player.group.clearSelection();
		player.groupType.clearSelection();
		switch (e.getActionCommand()) {
		case "select":
			Sushi sushi = player.makeSushi();
			int position = player.getPosition() - 1;
			try {
				switch (plateColor) {
				case "Red":
					makeRedPlateRequest(sushi, position);
					
					break;
				case "Blue":
					makeBluePlateRequest(sushi, position);
					break;
				case "Green":
					makeGreenPlateRequest(sushi, position);
					break;
				case "Gold":
					if(sushi != null) {
						String input = JOptionPane.showInputDialog("GOLD Plate: set a priece between 5.00 and 10.00");
						
						double price = Double.parseDouble(input);
						if(price >= 5.00 && price <= 10.00) {
							makeGoldPlateRequest(sushi, position, price);
						} else {
							JOptionPane.showMessageDialog(null, "The price has to be in the range");
						}
					} else {
						JOptionPane.showMessageDialog(null, "please choose a sushi or make a roll before you press 'select'");
					}
			} 
			
			} catch (IllegalArgumentException exception) {
				JOptionPane.showMessageDialog(null, "please choose a sushi or make a roll before you press 'select'");
			}
			
			
			
		}
	}
}
