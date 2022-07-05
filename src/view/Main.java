package view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;


public class Main extends JFrame  {

	public Main() throws IOException {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBackground(Color.black);
		this.setUndecorated(true);
		this.setVisible(true);
		this.add(new StartGameScreen(this));
		this.revalidate();
		this.repaint();
	}
	
	public static void main(String[] args) throws IOException {
		new Main();
	}
	

}
