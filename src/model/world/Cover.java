package model.world;

import java.awt.Point;

public class Cover implements Damageable {
	private int currentHP;
	private int maxHP;
	private Point location;

	public Cover(int x, int y) {
		this.maxHP = (int)(( Math.random() * 900) + 100);
		this.currentHP=maxHP;
		location = new Point(x, y);
	}

	public int getCurrentHP() {
		return this.currentHP;
	}

	public void setCurrentHP(int newHp) {
		if (newHp < 0) {
			currentHP = 0;
		
		} else if(currentHP>maxHP)
			currentHP=maxHP;
		else
			currentHP = newHp;
	}
	
	public int getMaxHP() {
		return this.currentHP;
	}

	public Point getLocation() {
		return location;
	}

	

	

}
