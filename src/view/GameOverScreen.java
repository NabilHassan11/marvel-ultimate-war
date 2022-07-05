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
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverScreen extends JPanel implements ActionListener{
	private Main m;
	private JPanel container;
	private JPanel panel;
	private JPanel text;
	private JPanel panel2;
	private JPanel buttons;
	private JLabel congrats;
	private JLabel playerName;
	private JLabel you;
	private JLabel win;
	private JButton exit;
	private Image background;
	
	
	public GameOverScreen(Main m, String name) throws IOException{
		this.m = m;
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		
		background = ImageIO.read(new File("over.jpeg"));
		
		container = new JPanel();
		container.setLayout(new FlowLayout());
		container.setOpaque(false);
		this.add(container);

		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		container.add(panel);
		
		text = new JPanel();
		text.setBackground(Color.BLACK);
		text.setOpaque(false);
		panel.add(text,BorderLayout.NORTH);
		
		congrats = new JLabel();
		congrats.setText("CONGRATULATIONS,");
		congrats.setFont(new Font(Font.MONOSPACED,Font.BOLD,40));
		congrats.setForeground(Color.RED);
		congrats.setBackground(Color.BLACK);
		text.add(congrats,BorderLayout.NORTH);
		
		playerName = new JLabel();
		playerName.setText(name.toUpperCase()+"!");
		playerName.setFont(new Font(Font.MONOSPACED,Font.BOLD,40));
		playerName.setForeground(Color.RED);
		playerName.setBackground(Color.BLACK);
		text.add(playerName,BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(150,750));
		panel2.setOpaque(false);
		panel.add(panel2,BorderLayout.CENTER);
		
		you = new JLabel();
		you.setText("YOU ");
		you.setFont(new Font(Font.MONOSPACED,Font.BOLD,40));
		you.setForeground(Color.RED);
		you.setBackground(Color.BLACK);
		panel2.add(you,BorderLayout.WEST);
		
		win = new JLabel();
		win.setText("WIN!");
		win.setFont(new Font(Font.MONOSPACED,Font.BOLD,40));
		win.setForeground(Color.RED);
		win.setBackground(Color.BLACK);
		panel2.add(win,BorderLayout.EAST);
		
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(2,1));
		buttons.setOpaque(false);
		panel.add(buttons,BorderLayout.SOUTH);
		
		exit = new JButton();
		exit.setText("Exit");
		exit.addActionListener(this);
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		exit.setPreferredSize(new Dimension(60,30));
		buttons.add(exit);
		
		this.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exit)
            m.dispose(); 
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(background,0,0,getWidth(),getHeight(),this);
	} 
	
	
}