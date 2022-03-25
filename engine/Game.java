package engine;

import model.abilities.*;
import model.effects.*;
import model.world.*;

import java.util.ArrayList;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;

public class Game {
	
	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private final static int BOARDHEIGHT = 5;
	private final static int BOARDWIDTH = 5;
	
	public Game(Player first, Player second){
		firstPlayer = first;
		secondPlayer = second;
		board = new Object[BOARDHEIGHT][BOARDWIDTH];
		availableAbilities = new ArrayList<>();
		availableChampions = new ArrayList<>();
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
	}
	
	
	public Player getFirstPlayer() {
		return firstPlayer;
	}


	public Player getSecondPlayer() {
		return secondPlayer;
	}


	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}


	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}


	public Object[][] getBoard() {
		return board;
	}


	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}


	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}


	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}


	public static int getBoardheight() {
		return BOARDHEIGHT;
	}


	public static int getBoardwidth() {
		return BOARDWIDTH;
	}


	private void placeChampions(){
		int i = 0;
		for(Champion champion : firstPlayer.getTeam()){
			board[0][++i] = champion;
			champion.setLocation(new Point(0,i));
		}
		i = 0;
		for(Champion champion : secondPlayer.getTeam()){
			board[4][++i] = champion;
			champion.setLocation(new Point(0,i));
		}
	}
	
	private void placeCovers(){
		boolean[][] hasCover = new boolean[5][5];
		int placedCovers = 0;
		while(placedCovers < 5){
			int x = (int) (5*Math.random());
			int y = (int) (5*Math.random());
			if(hasCover[x][y] || ((x == 0 || x == BOARDWIDTH - 1)))
				continue;
			else{
				placedCovers++;
				hasCover[x][y] = true;
				board[x][y] = new Cover(x,y);
			}
		}
	}
	
	public static void loadAbilities(String filePath) throws Exception{
		availableAbilities = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String ability;

		while((ability = br.readLine()) != null){
			String[] arr = ability.split(",");
			
			int manaCost = Integer.parseInt(arr[2]);
			int baseCooldown = Integer.parseInt(arr[4]);
			int castRange = Integer.parseInt(arr[3]);
			int requiredActionPoints = Integer.parseInt(arr[6]);
			AreaOfEffect castArea = AreaOfEffect.valueOf(arr[5]);
			
			Ability ab;
			
			if(arr[0].equals("CC")){
				Effect effect;
				
				switch(arr[7]){
				case "Shield":	effect = new Shield(Integer.parseInt(arr[8])); break;
				case "Disarm":	effect = new Disarm(Integer.parseInt(arr[8])); break;
				case "Dodge":	effect = new Dodge(Integer.parseInt(arr[8])); break;
				case "Embrace":	effect = new Embrace(Integer.parseInt(arr[8])); break;
				case "PowerUp":	effect = new PowerUp(Integer.parseInt(arr[8])); break;
				case "Root":	effect = new Root(Integer.parseInt(arr[8])); break;
				case "Shock":	effect = new Shock(Integer.parseInt(arr[8])); break;
				case "Silence":	effect = new Silence(Integer.parseInt(arr[8])); break;
				case "Stun":	effect = new Stun(Integer.parseInt(arr[8])); break;
				case "SpeedUp":	effect = new SpeedUp(Integer.parseInt(arr[8])); break;
				default: effect = new Effect(null,0,null);
				}
				
				ab = new CrowdControlAbility(arr[1],manaCost,baseCooldown,castRange,castArea,requiredActionPoints,effect);
			}
			
			else if(arr[0].equals("HEL"))
				ab = new HealingAbility(arr[1],manaCost,baseCooldown,castRange,castArea,requiredActionPoints,Integer.parseInt(arr[7]));
			
			else
				ab = new DamagingAbility(arr[1],manaCost,baseCooldown,castRange,castArea,requiredActionPoints,Integer.parseInt(arr[7]));
			
			availableAbilities.add(ab);

		}
	}
	
	public static void loadChampions(String filePath) throws Exception{
		availableChampions = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String champion;
		
		while((champion = br.readLine()) != null){
			String[] arr = champion.split(",");
			
			int maxHP = Integer.parseInt(arr[2]);
			int mana = Integer.parseInt(arr[3]);
			int maxActionPointsPerTurn = Integer.parseInt(arr[4]);
			int speed = Integer.parseInt(arr[5]);
			int attackRange = Integer.parseInt(arr[6]);
			int attackDamage = Integer.parseInt(arr[7]);
			
			int[] locations = new int[3];
			for(int j = 0; j < availableAbilities.size();j++){
				if(availableAbilities.get(j).getName().equals(arr[8]))
					locations[0] = j;
				else if(availableAbilities.get(j).getName().equals(arr[9]))
					locations[1] = j;
				else if(availableAbilities.get(j).getName().equals(arr[10]))
					locations[2] = j;
			} 
			
			Champion c;
			
			if(arr[0].equals("H"))
				c = new Hero(arr[1],maxHP,mana,maxActionPointsPerTurn,speed,attackRange,attackDamage);
			
			else if(arr[0].equals("V"))
				c = new Villain(arr[1],maxHP,mana,maxActionPointsPerTurn,speed,attackRange,attackDamage);
			
			else
				c = new AntiHero(arr[1],maxHP,mana,maxActionPointsPerTurn,speed,attackRange,attackDamage);
			
			availableChampions.add(c);
			c.getAbilities().add(availableAbilities.get(locations[0]));
			c.getAbilities().add(availableAbilities.get(locations[1]));
			c.getAbilities().add(availableAbilities.get(locations[2]));	

		}
	}
	public static void main(String[] args) throws Exception{
		//loadAbilities("C:\\Users\\User\\Downloads\\Telegram Desktop\\Abilities.csv");
		//loadChampions("C:\\Users\\User\\Downloads\\Telegram Desktop\\Champions.csv");

	}
}
