package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import comp401.sushi.Nigiri;
import comp401.sushi.Sashimi;
import comp401.sushi.Sushi;

public class PlayerViewUnlimited extends JPanel implements ActionListener{

	private String sushi = "none";
	private String sushiType = "none";
	private int position;
	private String colorChoice = "Blue";
	
	ButtonGroup group = new ButtonGroup();
	ButtonGroup groupType = new ButtonGroup();
	
	//roll
	private RollMakerView rollMaker;
	
	public PlayerViewUnlimited() {
		setLayout(new GridLayout(3,1));
	
		//creating main panel
		JPanel mainPanel = new JPanel();
		
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbcChooseSushi = new GridBagConstraints();
		
		//creating panels
		JPanel sushiRadioPanel = new JPanel();
		sushiRadioPanel.setLayout(new GridLayout(1, 3));
		
		
		JLabel sushiLabel = new JLabel("Choose Sushi");
		sushiLabel.setFont(new Font("Serif", Font.BOLD,16));
		sushiLabel.setForeground(Color.red);
		
		JRadioButton sashimi = new JRadioButton("Sashimi");
		sashimi.setActionCommand("sashimi");
		sashimi.addActionListener(this);
		sushiRadioPanel.add(sashimi);
//		sashimi.setSelected(true);
		
		JRadioButton nigiri = new JRadioButton("Nigiri");
		nigiri.setActionCommand("nigiri");
		nigiri.addActionListener(this);
		sushiRadioPanel.add(nigiri);
		
		/*JRadioButton roll = new JRadioButton ("Roll");
		roll.setActionCommand("roll");
		roll.addActionListener(this);*/
		rollMaker = new RollMakerView();
		sushiRadioPanel.add(rollMaker);
		//placing in the upper middle
		gbcChooseSushi.gridx = 1;
		gbcChooseSushi.gridy = 0;
		gbcChooseSushi.gridwidth = 2;
		gbcChooseSushi.gridheight = 1;
		
		
		mainPanel.add(sushiLabel, gbcChooseSushi);
		
		//placing below the label
		gbcChooseSushi.gridx = 0;
		gbcChooseSushi.gridy = 3;
		gbcChooseSushi.gridwidth = 3;
		gbcChooseSushi.gridheight = 5;
//		gbcChooseSushi.weightx = 0.5;
		
		mainPanel.add(sushiRadioPanel, gbcChooseSushi);
		add(mainPanel);
		
		
		group.add(sashimi);
		group.add(nigiri);
//		group.add(roll);
		
		//color chooser radio Jcombox
		
		String [] color = {"Blue", "Red", "Green", "Gold"};
		JComboBox plateColor = new JComboBox(color);
		
		plateColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox  cb = (JComboBox) e.getSource();
				String selectedColor = (String) cb.getSelectedItem();
				colorChoice = selectedColor;
				
			}
			
		});
		
		
		//color panel
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		JLabel lableColor = new JLabel(" Color");
		lableColor.setFont(new Font("Serif", Font.BOLD,16));
		lableColor.setForeground(Color.red);
		colorPanel.add(lableColor, gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		colorPanel.add(plateColor, gbc);
		
		add(colorPanel);
		
		//creating the types radioButtons
		JPanel mainPanelType = new JPanel();
		mainPanelType.setLayout(new GridBagLayout());
		JPanel sushiRadioPanelType = new JPanel();
		sushiRadioPanelType.setLayout(new GridLayout(1, 5));

		JLabel chooseType = new JLabel ("Choose Type of Sushi");
		chooseType.setFont(new Font("Serif", Font.BOLD,16));
		chooseType.setForeground(Color.RED);
		gbcChooseSushi.gridx = 1;
		gbcChooseSushi.gridy = 0;
		gbcChooseSushi.gridwidth = 2;
		gbcChooseSushi.gridheight = 1;
		mainPanelType.add(chooseType, gbcChooseSushi);
		
		JRadioButton tuna = new JRadioButton("Tuna");
		tuna.setActionCommand("tuna");
		tuna.addActionListener(this);
		sushiRadioPanelType.add(tuna);
//		tuna.setSelected(true);
		
		JRadioButton salmon = new JRadioButton("Salmon");
		salmon.setActionCommand("salmon");
		salmon.addActionListener(this);
		sushiRadioPanelType.add(salmon);
		
		JRadioButton eel = new JRadioButton("Eel");
		eel.setActionCommand("eel");
		eel.addActionListener(this);
		sushiRadioPanelType.add(eel);
		
		JRadioButton crab = new JRadioButton("Crab");
		crab.setActionCommand("crab");
		crab.addActionListener(this);
		sushiRadioPanelType.add(crab);
		
		JRadioButton shrimp = new JRadioButton("Shrimp");
		shrimp.setActionCommand("shrimp");
		shrimp.addActionListener(this);
		sushiRadioPanelType.add(shrimp);
		
		//placing below the label
		gbcChooseSushi.gridx = 0;
		gbcChooseSushi.gridy = 3;
		gbcChooseSushi.gridwidth = 3;
		gbcChooseSushi.gridheight = 5;
		
		mainPanelType.add(sushiRadioPanelType, gbcChooseSushi);
		add(mainPanelType);
		groupType.add(tuna);
		groupType.add(salmon);
		groupType.add(eel);
		groupType.add(crab);
		groupType.add(shrimp);

		//
		JPanel positionChooserPanel = new JPanel();
		positionChooserPanel.setLayout(new GridBagLayout());
		
		
		JLabel positionLabel = new JLabel ("Choose a postion and press Enter:   ");
		positionLabel.setFont(new Font("Serif", Font.BOLD,14));
		positionLabel.setForeground(Color.RED);
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 20;
		c.gridx = 0; 
		c.gridy = 1;
		positionChooserPanel.add(positionLabel, c);
		
		c.gridx = 2;
		c.gridy =1;
		String[] number = { "1", "2", "3"};
		JTextField choosePosition = new JTextField("integer");
		choosePosition.setEditable(true);
		choosePosition.setPreferredSize(new Dimension(50,20));
		choosePosition.setBackground(Color.cyan);
		positionChooserPanel.add(choosePosition, c);
		//place holder
		
		choosePosition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField textField = (JTextField) e.getSource();

				position = Integer.parseInt(textField.getText());
			}
		});

		add(positionChooserPanel);
		//try
		JPanel spicyPanel = new JPanel();
		spicyPanel.setLayout(new GridBagLayout());
		GridBagConstraints spicy = new GridBagConstraints();
		
		JLabel newLabel = new JLabel("put image or plate info here");
		spicy.gridx = 0;
		spicy.gridy = 0;
		spicyPanel.add(newLabel, spicy);
		
		JLabel jl = new JLabel();
		jl.setIcon(new ImageIcon("C:\\Users\\maberihe\\Desktop\\T0lwiBK8_400x400.jpg"));
		jl.setMaximumSize(new Dimension(20,20));
		spicy.gridx = 1;
		spicy.gridy = 0;
		spicyPanel.add(jl, spicy);
		
		
//		add(spicyPanel);
	   
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JRadioButton btn = (JRadioButton)e.getSource();
		String str = btn.getActionCommand();
		switch(str) {
		case "sashimi":
			sushi = str;
			break;
		
		case "nigiri":
			sushi = str;
			break;

			
		case "tuna":
			sushiType = "tuna";
			break;
		case "salmon" :
			sushiType = "salmon";
			break;
		case "eel":
			sushiType = "eel";
			break;
		case "crab":
			sushiType = "crab";
			break;
		case "shrimp":
			sushiType = "shrimp";
			break;
			
		}
	}
	public Sushi makeSushi() {
		Sushi bakeSushi;
		if(sushi.equals("sashimi")) {
			//so not you don't remake without selection
			sushi = "none";
			switch(sushiType) {
			case "tuna":
				sushiType = "none";
				bakeSushi = new Sashimi(Sashimi.SashimiType.TUNA);
				return bakeSushi;
			case "salmon":
				sushiType = "none";
				bakeSushi = new Sashimi(Sashimi.SashimiType.SALMON);
				return bakeSushi;
			case "eel":
				sushiType = "none";

				bakeSushi = new Sashimi(Sashimi.SashimiType.EEL);
				return bakeSushi;
			case "crab":
				sushiType = "none";

				bakeSushi = new Sashimi(Sashimi.SashimiType.CRAB);
				return bakeSushi;
			case "shrimp":
				sushiType = "none";

				bakeSushi = new Sashimi(Sashimi.SashimiType.SHRIMP);
				return bakeSushi;

			}
			
		} else if (sushi.equals("nigiri")) {
			sushi = "none";
			switch(sushiType) {
			case "tuna":
				sushiType = "none";

				bakeSushi = new Nigiri(Nigiri.NigiriType.TUNA);
				return bakeSushi;
			case "salmon":
				sushiType = "none";

				bakeSushi = new Nigiri(Nigiri.NigiriType.SALMON);
				return bakeSushi;
			case "eel":
				sushiType = "none";

				bakeSushi = new Nigiri(Nigiri.NigiriType.EEL);
				return bakeSushi;
			case "crab":
				sushiType = "none";

				bakeSushi = new Nigiri(Nigiri.NigiriType.CRAB);
				return bakeSushi;
			case "shrimp":
				sushiType = "none";

				bakeSushi = new Nigiri(Nigiri.NigiriType.SHRIMP);
				return bakeSushi;
			
			}
		} else if (rollMaker.hasInputSushiName && rollMaker.atLeatOneItemSelected) {
			//make roll
			bakeSushi = rollMaker.getSushi();
			rollMaker.setSushiName("null");
			rollMaker.hasInputSushiName = false;
			rollMaker.atLeatOneItemSelected = false;

			return bakeSushi;
			
		} else {
			JOptionPane.showMessageDialog(null, "Make sure you name your sushi and add at least one ingredient.");
		}
		
		
		return null;
		
	}
	
	public int getPosition() {
		return position;
	}
	
	public String getColorChoice() {
		return colorChoice;
	}
	

}
