package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import comp401.sushi.Ingredient;
import comp401.sushi.IngredientPortion;
import comp401.sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;

public class BeltView extends JPanel implements BeltObserver, ActionListener {

/* keep the array of buttons so that the pressed button is compared to find the postion of 
 * the pressed button in action performed. 
 * 
 */
	private Belt belt;
	private JButton[] belt_labels;
	JTextArea moreInfo;
	
	// adds an animation to show and unshow information of the plate pressed in the text area "moreInfor"
	Timer tm = new Timer(1000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			x = x + velX;
			if (x == 1007) {
				moreInfo.setBackground(null);
				moreInfo.setFont(null);
				moreInfo.setText("Select a plate to get more information about it. ");
				tm.stop();
				x = 1000;
			}
		}
		
	});
	int x = 1000, velX = 1;
	// a way to choose postion in the gridbaglayou
	GridBagConstraints c = new GridBagConstraints();
	//constructor
	public BeltView(Belt b) {
		
		this.belt = b;
		belt.registerBeltObserver(this);
		setLayout(new GridBagLayout());
		belt_labels = new JButton[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			JButton plabel = new JButton("");
			plabel.setActionCommand(""+ i);
			plabel.addActionListener(this);
			plabel.setMinimumSize(new Dimension(300, 20));
			plabel.setPreferredSize(new Dimension(300, 20));
			plabel.setOpaque(true);
			plabel.setBackground(Color.GRAY);
			belt_labels[i] = plabel;
		}
		
		// Index keeps track of the belt being added to the view
		// c.xxx are general to all of the contents
		int index = 0;
		c.insets = new Insets(10, 10, 10, 10);
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		//adds at postion grid (0.0) to (5, 0);
		for (int i = 0; i <= 5; i++) {
			c.gridx = i;
			c.gridy = 0;
			add(belt_labels[index], c);
			index++;
		}
		
		for (int i = 1; i <= 5; i++) {
			c.gridx = 5;
			c.gridy = i;
			add(belt_labels[index], c);
			index++;
		}
		
		for (int  i = 4; i >= 0; i--) {
			c.gridx = i;
			c.gridy = 5;
			add(belt_labels[index], c);
			index++;
		}
		
		for (int i = 4; i > 0; i--) {

			c.gridx = 0;
			c.gridy = i;
			add(belt_labels[index],c);
			index++;
		}
		
		moreInfo = new JTextArea();
		moreInfo.setEditable(false);
		moreInfo.setBackground(null);
		moreInfo.setText("Select a plate to get more information about it. ");
		
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		add(moreInfo,c);
		
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JButton plabel = belt_labels[i];

			if (p == null) {
				plabel.setText("");
				plabel.setBackground(Color.GRAY);
			} else {
				
				plabel.setText(p.toString());
				switch (p.getColor()) {
				case RED:
					plabel.setBackground(Color.RED); break;
				case GREEN:
					plabel.setBackground(Color.GREEN); break;
				case BLUE:
					plabel.setBackground(Color.BLUE); break;
				case GOLD:
					plabel.setBackground(Color.YELLOW); break;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//
		tm.stop();
		JButton button = (JButton) e.getSource();
		for(int i = 0; i < belt.getSize(); i++) {
			if(belt_labels[i] == button) {				
				Plate p = belt.getPlateAtPosition(i);
				if(!(p==null)) {
					
					String sushiName = p.toString();
					Chef chef = p.getChef();
					String chefName = chef.getName();
					String ageOfPlate = " " + belt.getAgeOfPlateAtPosition(i);
					
					int spaceIndex = sushiName.indexOf(" ");
					String afterSpace = sushiName.substring(spaceIndex +1);
					
					moreInfo.setBackground(Color.orange);
					tm.start();
					moreInfo.setFont(new Font("Plain",1,16));
					
					if(afterSpace.equals("sashimi") || afterSpace.equals("nigiri")) {

						moreInfo.setText("\n \n \n " + sushiName + " \n Chef: " + chefName + "\n  Age:  " + ageOfPlate);
	
					} else {
						IngredientPortion[] ingrP = p.getContents().getIngredients();
						ArrayList<String> ingrUsed = new ArrayList<String>();
						ArrayList<Double> amountUsed = new ArrayList<Double>();
						for(int j = 0; j < ingrP.length; j++) {
							ingrUsed.add(ingrP[j].getName());
							int value = (int) (ingrP[j].getAmount()*100);
							amountUsed.add(value/100.0);
						}
												
						switch(ingrUsed.size()) {
						case 1:
							moreInfo.setText(" \t Name of Suhi: \n" +  sushiName + "\n "
																+ " \tChef: \n" + chefName + "\n "
																+ "\tContent(amount): \n"  + ingrUsed.get(0) + "(" + amountUsed.get(0) + ")"
																+ " \n \tage of plate:  " + ageOfPlate);
							break;
						case 2:
							moreInfo.setText(" \t Name of Suhi: \n " +  sushiName + "\n "
									+ "\tChef:\n " + chefName + "\n "
									+ "\tContent(amount):\n "  + ingrUsed.get(0) + "(" + amountUsed.get(0) + "),  "
									+ ingrUsed.get(1) + "(" + amountUsed.get(1) + "),"
									+ " \n\t age of plate:  " + ageOfPlate);
							break;
						case 3:
							moreInfo.setText("\t Name of Suhi: \n" +  sushiName + "\n "
									+ "\tChef: \n" + chefName + "\n "
									+ "\tContent(amount): \n"  + ingrUsed.get(0) + "(" + amountUsed.get(0) + "),  "
									+ ingrUsed.get(1) + "(" + amountUsed.get(1) + "), \n " 
									+ ingrUsed.get(2) + "(" + amountUsed.get(2) + "),  " 
									+ " \n \tAge of plate:  " + ageOfPlate);
						case 4:
							moreInfo.setText(" \t Name of Suhi: \n" +  sushiName + "\n "
									+ "\tChef: \n " + chefName + "\n "
									+ "\tContent(amount): \n"  + ingrUsed.get(0) + "(" + amountUsed.get(0) + "),  "
									+ ingrUsed.get(1) + "(" + amountUsed.get(1) + "),  \n" 
									+ ingrUsed.get(2) + "(" + amountUsed.get(2) + "),  " 
									+ ingrUsed.get(3) + "(" + amountUsed.get(3) + ")  "
									+ " \n \t age of plate:  " + ageOfPlate);
							break;
						case 5:
							moreInfo.setText( "\t Name of Suhi: \n " +  sushiName + "\n "
									+ "\tChef: \n" + chefName + "\n "
									+ "\tContent(amount): \n" + ingrUsed.get(0) + "(" + amountUsed.get(0) + "),  "
									+ ingrUsed.get(1) + "(" + amountUsed.get(1) + "), \n " 
									+ ingrUsed.get(2) + "(" + amountUsed.get(2) + "),  " 
									+ ingrUsed.get(3) + "(" + amountUsed.get(3) + "), \n "
									+ ingrUsed.get(4) + "(" + amountUsed.get(4) + ") "
									+ " \n \tage of plate:  " + ageOfPlate);
							break;
						case 6:
							moreInfo.setText("\t Name of Suhi: \n" +  sushiName + "\n "
									+ "\tChef: \n" + chefName + "\n "
									+ "\tContent(amount): \n "  + ingrUsed.get(0) + "(" + amountUsed.get(0) + "),  "
									+ ingrUsed.get(1) + "(" + amountUsed.get(1) + "), \n " 
									+ ingrUsed.get(2) + "(" + amountUsed.get(2) + "),  " 
									+ ingrUsed.get(3) + "(" + amountUsed.get(3) + "),  \n"
									+ ingrUsed.get(4) + "(" + amountUsed.get(4) + "),  "
									+ ingrUsed.get(5) + "(" + amountUsed.get(5) + ")  "
									+ " \n \t age of plate:  " + ageOfPlate);
								break;
						case 7:
							moreInfo.setText("\t Name of Suhi: \n" +  sushiName + "\n "
									+ "\tChef:\n " + chefName + "\n "
									+ "\tContent(amount): \n" + ingrUsed.get(0) + "(" + amountUsed.get(0) + "),  "
									+ ingrUsed.get(1) + "(" + amountUsed.get(1) + "),  \n" 
									+ ingrUsed.get(2) + "(" + amountUsed.get(2) + "),  " 
									+ ingrUsed.get(3) + "(" + amountUsed.get(3) + "), \n "
									+ ingrUsed.get(4) + "(" + amountUsed.get(4) + "),  "
									+ ingrUsed.get(5) + "(" + amountUsed.get(5) + "),  \n"
									+ ingrUsed.get(6) + "(" + amountUsed.get(6) + ")  "

									+ " \n \t age of plate:  " + ageOfPlate);
							break;
						
						case 8:
							moreInfo.setText(" \tName of Suhi: \n" +  sushiName + "\n "
									+ "\tChef:\n " + chefName + "\n "
									+ "\tContent(amount): \n"  + ingrUsed.get(0) + "(" + amountUsed.get(0) + "),  "
									+ ingrUsed.get(1) + "(" + amountUsed.get(1) + "), \n " 
									+ ingrUsed.get(2) + "(" + amountUsed.get(2) + "),  " 
									+ ingrUsed.get(3) + "(" + amountUsed.get(3) + "),  \n"
									+ ingrUsed.get(4) + "(" + amountUsed.get(4) + "),  "
									+ ingrUsed.get(5) + "(" + amountUsed.get(5) + "), \n "
									+ ingrUsed.get(6) + "(" + amountUsed.get(6) + "),  "
									+ ingrUsed.get(7) + "(" + amountUsed.get(7) + ")  "

									+ " \n \t Age of plate:  " + ageOfPlate);
							
						}
						
					}
				
				}
						
			}
		}
		
		
	}
}
