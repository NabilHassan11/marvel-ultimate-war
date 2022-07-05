package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import engine.Player;
import engine.Game;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.effects.Effect;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;

public class BoardScreen extends JPanel implements ActionListener{
	private Main m;
	private Player firstPlayer;
	private Player secondPlayer;
	private Game game;
	private JPanel container;
	private JPanel panel;
	private JPanel players;
	private JPanel player1;
	private JPanel player2;
	private JPanel turnOrder;
	private JPanel championInfo;
	private JPanel board;
	private JPanel actions;
	private JPanel champion;
	private JPanel abilities;
	private JPanel championsInfo1;
	private JPanel championsInfo2;
	private JButton move;
	private JButton attack;
	private JButton endTurn;
	private JButton ability1;
	private JButton ability2;
	private JButton ability3;
	private JButton castAbility;
	private JButton useLeaderAbility;
	private JLabel championImage;
	private JLabel player1LeaderAbilityused ;
	private JLabel player2LeaderAbilityused;
	private JLabel info;
	private JLabel player1Info ;
	private JLabel player2Info;
	private JComboBox<String> currentAppliedEffects;
	private Image background;
	private ImageIcon coverImage = new ImageIcon("Cover.jpg");
	private Object[] directions = {"UP","DOWN","LEFT","RIGHT"};
	private final Direction[] d = new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
	private JButton[][] cells = new JButton[5][5];
	private boolean cast;
	private boolean singleTarget;
	private Ability a;

	public BoardScreen (Main m,Player firstPlayer,Player secondPlayer) throws IOException {
		this.m = m;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.game = new Game(firstPlayer , secondPlayer);
		this.setBackground(Color.BLACK);
		this.setVisible(true);

		background = ImageIO.read(new File("BoardBackground.jpg"));

		container = new JPanel();
		container.setLayout(new FlowLayout());
		container.setOpaque(false);
		this.add(container);

		panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		panel.setOpaque(false);
		container.add(panel);

		turnOrder = new JPanel();
		turnOrder.setOpaque(false);
		turnOrder.setPreferredSize(new Dimension(this.getWidth(),100));
		turnOrder.setBackground(Color.RED);
		putTurnOrder(turnOrder);
		panel.add(turnOrder,BorderLayout.NORTH);

		championInfo = new JPanel();
		championInfo.setLayout(new GridLayout(2,1));
		championInfo.setPreferredSize(new Dimension(250,350));
		championInfo.setOpaque(true);
		championInfo.setBackground(Color.BLACK);
		championInfo.setForeground(Color.WHITE);
		setCurrentChampion();
		panel.add(championInfo ,BorderLayout.EAST);

		board = new JPanel();
		board.setLayout(new GridLayout(5 , 5 , 2 ,2));
		board.setPreferredSize(new Dimension(950,680));
		board.setOpaque(false);
		board.setBackground(Color.WHITE);
		fillBoard();
		panel.add(board ,BorderLayout.CENTER);

		players = new JPanel();
		players.setLayout(new GridLayout(2 , 0 , 20 , 50));
		players.setPreferredSize(new Dimension(275,300));
		players.setOpaque(false);
		players.setBackground(Color.BLUE);
		panel.add(players ,BorderLayout.WEST);

		player1 = new JPanel(new BorderLayout());
		player1.setOpaque(false);
		players.add(player1);

		player1Info = new JLabel();
		player1Info.setText(firstPlayer.getName());
		player1Info.setFont(new Font("Broadway" , Font.BOLD , 15));
		player1Info.setBackground(Color.black);
		player1Info.setForeground(Color.white);
		player1Info.setOpaque(true);
		player1.add(player1Info, BorderLayout.NORTH);
		
		championsInfo1 = new JPanel();
		
		player1LeaderAbilityused = new JLabel("Leader Ability Used: " + game.isFirstLeaderAbilityUsed());
		player1LeaderAbilityused.setFont(new Font("Broadway" , Font.BOLD , 15));
		player1LeaderAbilityused.setBackground(Color.black);
		player1LeaderAbilityused.setForeground(Color.white);
		player1LeaderAbilityused.setOpaque(true);
		player1.add(player1LeaderAbilityused , BorderLayout.SOUTH);
		
		getChampions(firstPlayer, championsInfo1);
		player1.add(championsInfo1 , BorderLayout.CENTER);

		player2 = new JPanel(new BorderLayout());
		player2.setOpaque(false);
		players.add(player2);

		player2Info = new JLabel();
		player2Info.setText(secondPlayer.getName());
		player2Info.setFont(new Font("Broadway" , Font.BOLD , 15));
		player2Info.setBackground(Color.black);
		player2Info.setForeground(Color.white);
		player2Info.setOpaque(true);
		player2.add(player2Info, BorderLayout.NORTH);
		
		championsInfo2 = new JPanel();
		player2LeaderAbilityused = new JLabel("Leader Ability Used: " + game.isSecondLeaderAbilityUsed());
		player2LeaderAbilityused.setFont(new Font("Broadway" , Font.BOLD , 15));
		player2LeaderAbilityused.setBackground(Color.black);
		player2LeaderAbilityused.setForeground(Color.white);
		player2LeaderAbilityused.setOpaque(true);
		player2.add(player2LeaderAbilityused , BorderLayout.SOUTH);
		
		getChampions(secondPlayer, championsInfo2);
		player2.add(championsInfo2 , BorderLayout.CENTER);

		actions = new JPanel();
		actions.setPreferredSize(new Dimension(this.getWidth(),70));
		actions.setOpaque(false);
		actions.setBackground(Color.CYAN);
		panel.add(actions ,BorderLayout.SOUTH);

		endTurn = new JButton();
		endTurn.setText("End Turn");
		endTurn.setPreferredSize(new Dimension(150 ,50));
		endTurn.addActionListener(this);
		actions.add(endTurn);

		useLeaderAbility = new JButton();
		useLeaderAbility.setText("Use Leader Ability");
		useLeaderAbility.setPreferredSize(new Dimension(200 , 50));
		useLeaderAbility.addActionListener(this);
		actions.add(useLeaderAbility);

		castAbility = new JButton();
		castAbility.setText("Cast Ability");
		castAbility.setPreferredSize(new Dimension(100,50));
		castAbility.addActionListener(this);
		actions.add(castAbility);

		attack = new JButton();
		attack.setText("Attack");
		attack.setPreferredSize(new Dimension(100,50));
		attack.addActionListener(this);
		actions.add(attack);		

		move = new JButton();
		move.setText("Move");
		move.setPreferredSize(new Dimension(100,50));
		move.addActionListener(this);
		actions.add(move);

		this.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)(e.getSource())).getParent()==abilities){
			JButton clicked = e.getSource()==ability1?ability1:e.getSource()==ability2?ability2:ability3;
			if(cast){
				handleCasting(clicked);
			}

			else{
				String s = Game.findAbilityByName(clicked.getText()).toString();
				showMessageDialog(null,s,clicked.getText(),JOptionPane.INFORMATION_MESSAGE);
			}
		}

		else if(((JButton)(e.getSource())).getParent()==board){
			if(singleTarget){
				int[] loc = find(((JButton)(e.getSource())));

				try{
					game.castAbility(a,loc[0],loc[1]);
					update();
				}

				catch(NotEnoughResourcesException | AbilityUseException | InvalidTargetException | CloneNotSupportedException ex){
					showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}

				cast = false;
				singleTarget = false;
				a = null;
			}
		}

		else if(e.getSource()==endTurn) {
			Runnable r = new Runnable() {
				public void run() {
					putTurnOrder(turnOrder);		
				}
			};
			SwingUtilities.invokeLater(r);
			game.endTurn();
		}

		else if(e.getSource()==castAbility){
			showMessageDialog(null,"Choose one ability from your champion's abilities","Cast Ability",JOptionPane.INFORMATION_MESSAGE);
			cast = true;
		}

		else if(e.getSource()==move)
			handleMove("Move");

		else if(e.getSource()==attack)
			handleAttack("Attack");

		else if(e.getSource()==useLeaderAbility)
			handleUseLeaderAbility();

		if(game.checkGameOver()!=null){
			m.remove(this);
			try{
				m.add(new GameOverScreen(m,game.checkGameOver().getName()));
			}

			catch (IOException ex) {
				m.dispose();
			}
		}

		update();
		this.revalidate();
		this.repaint();
	}

	private void handleUseLeaderAbility(){
		try{
			game.useLeaderAbility();
			update();
		}

		catch(LeaderAbilityAlreadyUsedException | LeaderNotCurrentException ex){
			showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}

		update();
		this.revalidate();
		this.repaint();
	}

	private void handleCasting(JButton ability){
		Ability a = Game.findAbilityByName(ability.getText());
		if(a.getCastArea() == AreaOfEffect.DIRECTIONAL){
			int x = JOptionPane.showOptionDialog(null, "Choose a direction",
					"Cast Ability",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);
			if(x>=0 && x<=3){
				try{
					game.castAbility(a,d[x]);
					update();
				}

				catch(NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e){
					showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			cast = false;
		}

		else if(a.getCastArea() == AreaOfEffect.SINGLETARGET){
			showMessageDialog(null,"Choose a location on the board to cast your ability","Cast Ability",JOptionPane.INFORMATION_MESSAGE);
			singleTarget = true;
			this.a = a;
		}

		else{
			try{
				game.castAbility(a);
				update();
			}

			catch(NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e){
				showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
			cast = false;
		}

		this.revalidate();
		this.repaint();
	}

	private void handleAttack(String s){
		int x = JOptionPane.showOptionDialog(null, "Choose a direction",
				s,
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);
		if(x>=0 && x<=3){
			try{
				game.attack(d[x]);
				update();
			}

			catch(NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e){
				showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}

		this.revalidate();
		this.repaint();
	}

	private void handleMove(String s){

		int x = JOptionPane.showOptionDialog(null, "Choose a direction",
				s,
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);	
		if(x>=0 && x<=3){
			try{
				game.move(d[x]);
				update();
			}

			catch(NotEnoughResourcesException | UnallowedMovementException e){
				showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}

		this.revalidate();
		this.repaint();
	}

	private void update(){
		if(game.checkGameOver()!=null)
			return;
		Runnable r = new Runnable() {
			public void run() {
				fillBoard();
				putTurnOrder(turnOrder);
				setCurrentChampion();
				getChampions(firstPlayer,championsInfo1);
				getChampions(secondPlayer,championsInfo2);				
			}
		};
		SwingUtilities.invokeLater(r);
		this.revalidate();
		this.repaint();	
	}


	private int[] find(JButton b){
		for(int i = Game.getBoardheight()-1 ; i>=0 ; i--){
			for(int j = 0 ; j<Game.getBoardwidth() ; j++){
				if(b==cells[i][j])
					return new int[]{i,j};
			}
		}
		return null;
	}

	private void putTurnOrder(JPanel panel) {
		panel.removeAll();
		ArrayList<Champion> Champions = new ArrayList<>();

		while(!game.getTurnOrder().isEmpty())
			Champions.add((Champion) game.getTurnOrder().remove());

		JPanel ChampionsTurns = new JPanel();
		ChampionsTurns.setLayout(new FlowLayout());
		ChampionsTurns.setOpaque(false);
		boolean first = true;
		for(Champion champion : Champions) {
			if(first) {
				JLabel c = new JLabel(champion.getName());
				c.setFont(new Font("Britannic Bold" , Font.BOLD , 14));
				c.setForeground(new Color(0,200,50));
				ImageIcon icon = new ImageIcon(champion.getName() +"2.jpg");
				c.setIcon(icon);
				c.setHorizontalTextPosition(JLabel.CENTER);
				c.setVerticalTextPosition(JLabel.TOP);
				ChampionsTurns.add(c);
				first = false;
			}

			else {
				JLabel c = new JLabel(champion.getName());
				c.setFont(new Font("Britannic Bold" , Font.BOLD , 14));
				c.setForeground(new Color(200,0,50));
				ImageIcon icon = new ImageIcon(champion.getName() +"2.jpg");
				c.setIcon(icon);
				c.setHorizontalTextPosition(JLabel.CENTER);
				c.setVerticalTextPosition(JLabel.TOP);
				ChampionsTurns.add(c);
			}

		}

		while(!Champions.isEmpty())
			game.getTurnOrder().insert(Champions.remove(0));

		panel.add(ChampionsTurns);
		this.revalidate();
		this.repaint();
	}

	private void getAppliedEffects(Champion champion , JComboBox<String> appliedEffects) {
		for(Effect effect : champion.getAppliedEffects())
			appliedEffects.addItem(effect.getName() + " Duration: " + effect.getDuration());
	}

	private void getChampions(Player player , JPanel panel) {
		panel.removeAll();
		ArrayList<Champion> champions = new ArrayList<>();
		panel.setLayout(new GridLayout(3, 0 , 20 , 20));
		panel.setOpaque(false);
		for(Champion champion : player.getTeam())
			champions.add(champion);

		for(Champion champion : champions) {
			JPanel championInfo = new JPanel(new BorderLayout(20  , 2 ));
			championInfo.setOpaque(false);
			JLabel name = new JLabel();
			name.setText(champion.getName());
			name.setFont(new Font("Broadway" , Font.BOLD , 24));
			name.setOpaque(false);
			name.setForeground(Color.white);
			JProgressBar progressBar = new JProgressBar(0, champion.getMaxHP());
			progressBar.setValue(champion.getCurrentHP());
			progressBar.setString("Current HP");
			progressBar.setForeground(Color.GREEN);
			progressBar.setStringPainted(true);
			progressBar.setBackground(Color.BLACK);
			JComboBox<String> comboBox = new JComboBox<>();
			for(Effect effect : champion.getAppliedEffects()) {
				comboBox.addItem(effect.getName() + " Duration: " + effect.getDuration());
			}

			championInfo.add(name , BorderLayout.NORTH);
			championInfo.add(progressBar , BorderLayout.CENTER);
			championInfo.add(comboBox , BorderLayout.SOUTH);
			panel.add(championInfo);

			if(player == game.getFirstPlayer()){
				player1.remove(player1LeaderAbilityused);
				player1LeaderAbilityused = new JLabel("Leader Ability Used: " + game.isFirstLeaderAbilityUsed());
				player1LeaderAbilityused.setFont(new Font("Broadway" , Font.BOLD , 15));
				player1LeaderAbilityused.setBackground(Color.black);
				player1LeaderAbilityused.setForeground(Color.white);
				player1LeaderAbilityused.setOpaque(true);
				player1.add(player1LeaderAbilityused , BorderLayout.SOUTH);
			}
			else{
				player2.remove(player2LeaderAbilityused);
				player2LeaderAbilityused = new JLabel("Leader Ability Used: " + game.isSecondLeaderAbilityUsed());
				player2LeaderAbilityused.setFont(new Font("Broadway" , Font.BOLD , 15));
				player2LeaderAbilityused.setBackground(Color.black);
				player2LeaderAbilityused.setForeground(Color.white);
				player2LeaderAbilityused.setOpaque(true);
				player2.add(player2LeaderAbilityused , BorderLayout.SOUTH);
			}
		}
		this.revalidate();
		this.repaint();
	}

	private void setAbilityButtons() {
		ability1.setPreferredSize(new Dimension(100 , 50));
		ability2.setPreferredSize(new Dimension(100 , 50));
		ability3.setPreferredSize(new Dimension(100 , 50));

		ability1.setFocusable(false);
		ability2.setFocusable(false);
		ability3.setFocusable(false);

		ArrayList<String> abilities = new ArrayList<>();
		for(Ability ability : game.getCurrentChampion().getAbilities())
			abilities.add(ability.getName());

		ability1.setText(abilities.remove(0));
		ability2.setText(abilities.remove(0));
		ability3.setText(abilities.remove(0));
	}



	public void fillBoard() {
		cells = new JButton[5][5];
		board.removeAll();
		for(int i = Game.getBoardheight()-1 ; i>=0 ; i--) {
			for(int j = 0 ; j<Game.getBoardwidth() ; j++) {
				JButton cell = new JButton();
				cell.addActionListener(this);
				if(game.getBoard()[i][j] != null) {
					if(game.getBoard()[i][j] instanceof Cover){
						cell.setIcon(coverImage);
						JLabel coverHealth = new JLabel();
						coverHealth.setText("Current HP: "+String.valueOf(((Cover)game.getBoard()[i][j]).getCurrentHP()));
						coverHealth.setForeground(Color.WHITE);
						coverHealth.setOpaque(true);
						coverHealth.setBackground(Color.BLACK);
						coverHealth.setHorizontalAlignment(JLabel.CENTER);
						cell.add(coverHealth);
						this.revalidate();
						this.repaint();
					}
					else {
						Champion champion = (Champion)game.getBoard()[i][j];
						ImageIcon icon = new ImageIcon(champion.getName() +".jpg");
						cell.setIcon(icon);
					}
				}
				board.add(cell);
				cells[i][j]=cell;
			}
		}
		this.revalidate();
		this.repaint();
	}

	private void setCurrentChampion(){
		ImageIcon icon = new ImageIcon(game.getCurrentChampion().getName() +"2.jpg");

		championInfo.removeAll();

		champion = new JPanel();
		champion.setOpaque(false);
		champion.setPreferredSize(new Dimension(250, 400));
		champion.setLayout(new GridLayout(2,1));
		championInfo.add(champion);

		championImage = new JLabel(icon);
		championImage.setPreferredSize(new Dimension(80,50));
		championImage.setHorizontalAlignment(JLabel.CENTER);
		champion.add(championImage);

		info = new JLabel();
		info.setFont(new Font(Font.MONOSPACED , Font.BOLD , 15));
		info.setForeground(Color.white);
		info.setText("<html>" + game.getCurrentChampion().getStatus().replaceAll("\\n", "<br>") + "</html>");
		info.setPreferredSize(new Dimension(250,400));
		info.setHorizontalAlignment(JLabel.CENTER);
		champion.add(info);	

		abilities = new JPanel();
		abilities.setLayout(new GridLayout(4,1));
		abilities.setOpaque(false);
		abilities.setPreferredSize(new Dimension(250,40));
		abilities.setAlignmentX(FlowLayout.CENTER);
		championInfo.add(abilities);

		ability1 = new JButton();
		ability1.addActionListener(this);
		ability1.setPreferredSize(new Dimension(40,10));
		ability1.setHorizontalAlignment(JButton.CENTER);
		abilities.add(ability1);

		ability2 = new JButton();
		ability2.addActionListener(this);
		ability2.setPreferredSize(new Dimension(40,10));
		ability2.setHorizontalAlignment(JButton.CENTER);
		abilities.add(ability2);

		ability3 = new JButton();
		ability3.addActionListener(this);
		ability3.setPreferredSize(new Dimension(40,10));
		ability3.setHorizontalAlignment(JButton.CENTER);
		abilities.add(ability3);

		setAbilityButtons();

		currentAppliedEffects = new JComboBox<>();
		currentAppliedEffects.setPreferredSize(new Dimension(40, 10));
		getAppliedEffects(game.getCurrentChampion(), currentAppliedEffects);
		currentAppliedEffects.setAlignmentX(FlowLayout.CENTER);

		abilities.add(currentAppliedEffects);
		this.revalidate();
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(background,0,0,getWidth(),getHeight(),this);
	}
}
