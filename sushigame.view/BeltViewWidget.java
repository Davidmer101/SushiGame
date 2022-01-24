package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import comp401.sushi.Plate;
import sushigame.model.Belt;


public class BeltViewWidget extends JPanel  {
	
	private String textSushi; //has sushi and kind
	private String chef;
	private int age;
	private comp401.sushi.Plate.Color color;
	private JLabel sushiTextLabel;
	private Plate p;
	
	public BeltViewWidget(Belt belt) {
		

		setLayout(new BorderLayout());
		if ((p == null)) {
			 sushiTextLabel = new JLabel("I'm NULL");
		} else {
			 sushiTextLabel = new JLabel(p.toString());
		}
		
		add(sushiTextLabel);
		
		JButton button = new JButton("more info");
		
		add(button, BorderLayout.SOUTH);
		
		
		
	}

	public void setPlate(Plate p) {
		// TODO Auto-generated method stub
		this.p = p;
		
	}
	
	
}
