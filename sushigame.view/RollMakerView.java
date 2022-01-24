package sushigame.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401.sushi.AvocadoPortion;
import comp401.sushi.CrabPortion;
import comp401.sushi.EelPortion;
import comp401.sushi.IngredientPortion;
import comp401.sushi.RicePortion;
import comp401.sushi.Roll;
import comp401.sushi.SalmonPortion;
import comp401.sushi.SeaweedPortion;
import comp401.sushi.ShrimpPortion;
import comp401.sushi.Sushi;


public class RollMakerView extends JPanel implements ActionListener {

	private Sushi sushi;
	private String sushiName;
	public boolean hasInputSushiName = false;
	public boolean atLeatOneItemSelected = false;
	
	HashMap<String, Double> map = new HashMap<>();
	public RollMakerView() {
		String [] ingredients = { "Avocado", "Crab", "Eel", "Rice", "Salmon", "Seaweed", "Shrimp"};
		//create JMenuCheckBox
		JMenuBar file = new JMenuBar();
		JMenu chooseIngredients = new JMenu ("Roll");

		JCheckBoxMenuItem name = new JCheckBoxMenuItem("Give your roll a name");
		
		name.setActionCommand("name");
		name.addActionListener(this);
		
		JCheckBoxMenuItem avocado = new JCheckBoxMenuItem("Avocado");
		avocado.setActionCommand("avocado");
		avocado.addActionListener(this);
		
		JCheckBoxMenuItem crab = new JCheckBoxMenuItem("Crab");
		crab.setActionCommand("crab");
		crab.addActionListener(this);

		JCheckBoxMenuItem eel = new JCheckBoxMenuItem("Eel");
		eel.setActionCommand("eel");
		eel.addActionListener(this);

		JCheckBoxMenuItem rice = new JCheckBoxMenuItem("Rice");
		rice.setActionCommand("rice");
		rice.addActionListener(this);

		JCheckBoxMenuItem salmon = new JCheckBoxMenuItem("Salmon");
		salmon.setActionCommand("salmon");
		salmon.addActionListener(this);

		JCheckBoxMenuItem seaweed = new JCheckBoxMenuItem("Seaweed");
		seaweed.setActionCommand("seaweed");
		seaweed.addActionListener(this);
		
		JCheckBoxMenuItem shrimp = new JCheckBoxMenuItem("Shrimp");
		shrimp.setActionCommand("shrimp");
		shrimp.addActionListener(this);
		


		chooseIngredients.add(name);
		chooseIngredients.add(avocado);
		chooseIngredients.add(crab);
		chooseIngredients.add(eel);
		chooseIngredients.add(rice);
		chooseIngredients.add(salmon);
		chooseIngredients.add(seaweed);
		chooseIngredients.add(shrimp);
		
		file.add(chooseIngredients);
		add(file);
		

		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		JCheckBoxMenuItem cbox = (JCheckBoxMenuItem) e.getSource();
		switch(str) {
		case "name":
			String input = JOptionPane.showInputDialog("What do you wanna name your Roll? ");
			sushiName = input;
			hasInputSushiName = true;
			break;
		case "avocado":
		case "crab":
		case "eel":
		case "rice":
		case "salmon":
		case "seaweed":
		case "shrimp":
			atLeatOneItemSelected = true;
			String input1 = JOptionPane.showInputDialog("How much do you want? ");
			try {
				Double amount = Double.parseDouble(input1);
				if(amount < 1.5) {
					map.put(str, amount);
				} else {
					JOptionPane.showMessageDialog(null, "Max amount allowed is 1.5!");
				}				
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Please enter a decimal");

			}
		}
		
		cbox.setSelected(false);		
	}


	/*Using the 
	 * 
	 */
	public Sushi getSushi() {
		String str = map.toString();
		IngredientPortion[] ingredientsToUse = new IngredientPortion [map.size()];
		int i = 0;
		if(map.containsKey("avocado") && i < ingredientsToUse.length) {
			IngredientPortion avocadoIngredient = new AvocadoPortion(map.get("avocado"));
			ingredientsToUse[i] = avocadoIngredient;
			i++;
		} if(map.containsKey("crab") && i < ingredientsToUse.length) {
			IngredientPortion crabIngredient = new CrabPortion(map.get("crab"));
			ingredientsToUse[i] = crabIngredient;
			i++;
		} if (map.containsKey("eel") && i < ingredientsToUse.length) {
			IngredientPortion eelIngredient = new EelPortion(map.get("eel"));
			ingredientsToUse[i] = eelIngredient;
			i++;
		} if (map.containsKey("rice") && i < ingredientsToUse.length) {
			IngredientPortion riceIngredient = new RicePortion(map.get("rice"));
			ingredientsToUse[i] = riceIngredient;
			i++;
		} if(map.containsKey("salmon") && i < ingredientsToUse.length) {
			IngredientPortion salmonIngredient = new SalmonPortion(map.get("salmon"));
			ingredientsToUse[i] = salmonIngredient;
			i++;
		} if(map.containsKey("seaweed") && i < ingredientsToUse.length) {
			IngredientPortion seaweedIngredient = new SeaweedPortion(map.get("seaweed"));
			ingredientsToUse[i] = seaweedIngredient;
			i++;
		} if (map.containsKey("shrimp")&& i < ingredientsToUse.length) {
			IngredientPortion shrimpIngredient = new ShrimpPortion(map.get("shrimp"));
			ingredientsToUse[i] = shrimpIngredient;
			i++;
		}
		
		sushi = new Roll (sushiName, ingredientsToUse);
		
		map.clear();

		return sushi;
	}
	public String getSushiName() {
		return sushiName;
	}
	public void setSushiName(String s) {
		sushiName = s;
		
	}



	
}
