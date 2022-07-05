package view;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.security.auth.DestroyFailedException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Player;
import engine.Game;
import model.abilities.Ability;
import model.world.Champion;

public class SelectChampionsScreen extends JPanel implements ActionListener {
	private String firstPlayerName;
	private String secondPlayerName;
	private Player firstPlayer;
	private Player secondPlayer;
	private Main m;
	private JPanel container;
	private JPanel player;
	private JPanel championButtons;
	private JPanel championPhoto;
	private JPanel championInfo;
	private JPanel align;
	private JButton ability1;
	private JButton ability2;
	private JButton ability3;
	private JPanel panel;
	private JLabel selectYourTeam;
	private JLabel playerName;
	private JLabel errors;
	private JLabel info;
	private JButton next;
	private JButton selectChampion;
	private ArrayList<JButton> champions;
	private final int MAX_SELECTIONS = 3;
	private int selected = 0;
	private int currentIndex;
	private int currentPlayer = 1;
		
	
	public SelectChampionsScreen(Main m,String firstName,String secondName) throws IOException{
		this.m = m;
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		champions = new ArrayList<>();
		
		firstPlayerName = firstName;
		secondPlayerName = secondName;
		firstPlayer = new Player(firstPlayerName);
		secondPlayer = new Player(secondPlayerName);
		
		container = new JPanel();
		container.setLayout(new FlowLayout());
		container.setOpaque(false);
		this.add(container);
	
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		container.add(panel);
		
		player = new JPanel();
		player.setOpaque(false);
		player.setPreferredSize(new Dimension(200,100));
		player.setLayout(new FlowLayout(FlowLayout.CENTER));
		player.setBackground(Color.RED);
		panel.add(player,BorderLayout.NORTH);
		
		align = new JPanel();
		align.setLayout(new GridLayout(2,1));
		align.setOpaque(false);
		player.add(align);
		
		playerName = new JLabel(firstPlayerName+',');
		playerName.setBackground(Color.BLACK);
		playerName.setForeground(Color.WHITE);
		playerName.setHorizontalAlignment(JLabel.CENTER);
		playerName.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
		align.add(playerName);
		
		selectYourTeam = new JLabel("Select your team. Select the leader first!");
		selectYourTeam.setBackground(Color.BLACK);
		selectYourTeam.setForeground(Color.WHITE);
		selectYourTeam.setHorizontalAlignment(JLabel.CENTER);
		selectYourTeam.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
		align.add(selectYourTeam);
		
		championPhoto = new JPanel();
		championPhoto.setOpaque(false);
		championPhoto.setPreferredSize(new Dimension(700,650));
		championPhoto.setBackground(Color.BLACK);
		panel.add(championPhoto,BorderLayout.WEST);
		
		championInfo = new JPanel();
		championInfo.setOpaque(false);
		championInfo.setPreferredSize(new Dimension(700,675));
		championInfo.setLayout(new FlowLayout(FlowLayout.CENTER));		
		championInfo.setBackground(Color.BLACK);
		panel.add(championInfo,BorderLayout.EAST);
		
		info = new JLabel();
		info.setPreferredSize(new Dimension(700,400));
		info.setBackground(Color.BLACK);
		info.setForeground(Color.WHITE);
		info.setHorizontalAlignment(JLabel.CENTER);
		info.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
		info.setOpaque(false);
		championInfo.add(info);
		
		ability1 = new JButton();
		ability1.setVisible(false);
		ability1.setPreferredSize(new Dimension(200,40));
		ability1.setBackground(Color.RED);
		ability1.setForeground(Color.WHITE);
		ability1.addActionListener(this);
		championInfo.add(ability1);
		
		ability2 = new JButton();
		ability2.setVisible(false);
		ability2.setPreferredSize(new Dimension(200,40));
		ability2.setBackground(Color.RED);
		ability2.setForeground(Color.WHITE);
		ability2.addActionListener(this);
		championInfo.add(ability2);
		
		ability3 = new JButton();
		ability3.setVisible(false);
		ability3.setPreferredSize(new Dimension(200,40));
		ability3.setBackground(Color.RED);
		ability3.setForeground(Color.WHITE);
		ability3.addActionListener(this);
		championInfo.add(ability3);
		
		
		selectChampion = new JButton("Select");
		selectChampion.setPreferredSize(new Dimension(100,40));
		selectChampion.setHorizontalAlignment(JLabel.CENTER);
		selectChampion.setBackground(Color.RED);
		selectChampion.setForeground(Color.WHITE);
		selectChampion.addActionListener(this);
		selectChampion.setVisible(false);
		championInfo.add(selectChampion);
				
		next = new JButton("Next");
		next.setPreferredSize(new Dimension(100,40));
		next.setHorizontalAlignment(JLabel.CENTER);
		next.setBackground(Color.RED);
		next.setForeground(Color.WHITE);
		next.addActionListener(this);
		next.setVisible(false);
		championInfo.add(next);
		
		championButtons = new JPanel();
		championButtons.setOpaque(false);
		championButtons.setLayout(new GridLayout(3,5));
		championButtons.setBackground(Color.RED);
		addChampions();
		panel.add(championButtons,BorderLayout.SOUTH);
		
		this.revalidate();
		this.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if(champions.contains(e.getSource())){
			selectChampion.setVisible(true);
			next.setVisible(true);
			int index = champions.indexOf(e.getSource());
			currentIndex = index;
			if(!firstPlayer.getTeam().contains(Game.getAvailableChampions().get(currentIndex)) && !secondPlayer.getTeam().contains(Game.getAvailableChampions().get(currentIndex)))
				selectChampion.setEnabled(true);
			else
				selectChampion.setEnabled(false);
			info.setText("<html>" + Game.getAvailableChampions().get(currentIndex).toString().replaceAll("\\n", "<br>") + "</html>");
			ability1.setVisible(true);
			ability1.setText(Game.getAvailableChampions().get(currentIndex).getAbilities().get(0).getName());
			ability2.setVisible(true);
			ability2.setText(Game.getAvailableChampions().get(currentIndex).getAbilities().get(1).getName());
			ability3.setVisible(true);
			ability3.setText(Game.getAvailableChampions().get(currentIndex).getAbilities().get(2).getName());
			
			info.setText("<html>" + Game.getAvailableChampions().get(currentIndex).toString().replaceAll("\\n", "<br>") + "</html>");
			String name = (index+1) + ".jpeg";
			ImageIcon ic = new ImageIcon(name);
			JLabel label = new JLabel(ic);
			label.setPreferredSize(new Dimension(900,700));
			championPhoto.removeAll();
			championPhoto.add(label);
			championPhoto.revalidate();
			championPhoto.repaint();
		}
		else if(e.getSource()==ability1){
			String s = Game.findAbilityByName(ability1.getText()).toString();
			showMessageDialog(null,s,ability1.getText(),JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getSource()==ability2){
			String s = Game.findAbilityByName(ability2.getText()).toString();
			showMessageDialog(null,s,ability2.getText(),JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getSource()==ability3){
			String s = Game.findAbilityByName(ability3.getText()).toString();
			showMessageDialog(null,s,ability3.getText(),JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getSource()==selectChampion){
			if(selected<MAX_SELECTIONS){
				selectChampion.setEnabled(false);
				selected++;
				if(currentPlayer==1){
					if(selected==1)
						firstPlayer.setLeader(Game.getAvailableChampions().get(currentIndex));
					firstPlayer.getTeam().add(Game.getAvailableChampions().get(currentIndex));
				}
				else{
					if(selected==1)
						secondPlayer.setLeader(Game.getAvailableChampions().get(currentIndex));
					secondPlayer.getTeam().add(Game.getAvailableChampions().get(currentIndex));
				}
			}
			else if(selected==MAX_SELECTIONS){
				showMessageDialog(null,"You can't choose more than 3 champions!","Error",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getSource()==next){
			if(selected<MAX_SELECTIONS)
				showMessageDialog(null,"You should choose a team of 3 champions!","Error",JOptionPane.INFORMATION_MESSAGE);
			else{
				if(currentPlayer==1){
					currentPlayer=2;
					selected=0;
					info.setText("");
					selectChampion.setVisible(false);
					next.setVisible(false);
					ability1.setVisible(false);
					ability2.setVisible(false);
					ability3.setVisible(false);
					playerName.setText(secondPlayerName+',');
					championPhoto.removeAll();
				}
				else{
					try{
						m.remove(this);
						m.add(new BoardScreen(m, firstPlayer, secondPlayer));
						m.revalidate();
						m.repaint();
					}
					catch (IOException ex){
						m.dispose();
					}
					
				}
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	private void addChampions() throws IOException{
		Game.loadAbilities("Abilities.csv");
		Game.loadChampions("Champions.csv");
		for(Champion c : Game.getAvailableChampions()){
			JButton button = new JButton(c.getName());
			champions.add(button);
			championButtons.add(button);
			button.addActionListener(this);	
		}
	}
}
