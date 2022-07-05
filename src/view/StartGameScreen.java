package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StartGameScreen extends JPanel implements ActionListener{
    private Main m;
	private JButton startButton;
    private JButton exitButton;
    private JPanel panel;
    private JPanel tmp;
    private JPanel container;
	private Image marvelLogo;

	public StartGameScreen(Main m) throws IOException {
	        this.m = m;
	        this.setBackground(Color.BLACK);
	        
	        container = new JPanel();
	        container.setOpaque(false);
	        this.add(container);
	        
	        tmp = new JPanel();
	        tmp.setPreferredSize(new Dimension(this.getWidth(),1200));
	        container.add(tmp,BorderLayout.NORTH);
	        
	        panel = new JPanel();
		    panel.setLayout(new GridLayout(2,0));
		    panel.setPreferredSize(new Dimension(350,80));
            panel.setOpaque(false);
	        container.add(panel,BorderLayout.SOUTH);
	        
	        startButton = new JButton("Play");
	        startButton.setPreferredSize(new Dimension(250,35));
	        startButton.setForeground(Color.WHITE);
	        startButton.setBackground(Color.BLACK);
	        startButton.addActionListener(this);
	        panel.add(startButton);
	        
	        exitButton = new JButton("Exit");
	        exitButton.setPreferredSize(new Dimension(250,35));
	        exitButton.setForeground(Color.WHITE);
	        exitButton.setBackground(Color.BLACK);
	        exitButton.addActionListener(this);
	        panel.add(exitButton);
	        
		    marvelLogo = ImageIO.read(new File("backg.jpeg"));
	        this.revalidate();
	        this.repaint();
	        setVisible(true);
		
	}
	
	public void paintComponent(Graphics g) {
        g.drawImage(marvelLogo,0,0,getWidth(),getHeight(),this);
      } 

	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startButton) {
            m.remove(this);
            try {
				m.add(new EnterPlayersNamesScreen(m));
			} catch (IOException ex) {
				m.dispose();
			}
            m.revalidate();
            m.repaint();
        }
        else {
            m.dispose();
        }   
    }


}
