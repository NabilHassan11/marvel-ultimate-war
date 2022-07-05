package view;

import static javax.swing.JOptionPane.showMessageDialog;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EnterPlayersNamesScreen extends JPanel implements ActionListener {

    private Main m;
    private JPanel container;
    private JPanel getNames;
    private JPanel enterOrExit;
    private JPanel panel;
    private JPanel gamePanel;
    private JLabel enterFirstName;
    private JLabel enterSecondName;
    private JLabel game;
    private JTextField firstPlayerName;
    private JTextField secondPlayerName;
    private Image background;


    private JButton enter;
    private JButton exit;
  



	public EnterPlayersNamesScreen(Main m) throws IOException{
		this.m = m;
		this.setBackground(Color.BLACK);
		this.setVisible(true);

		
		container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new FlowLayout());
		this.add(container);
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(0, 1));
		panel.setPreferredSize(new Dimension(700,850));
		container.add(panel);
		
		gamePanel = new JPanel();
		gamePanel.setOpaque(false);
		gamePanel.setPreferredSize(new Dimension(700,200));
		panel.add(gamePanel);
		
		game = new JLabel("Marvel: Ultimate War");
		game.setHorizontalAlignment(JLabel.CENTER);
		game.setForeground(Color.white);
		game.setFont(new Font(Font.MONOSPACED,Font.BOLD,50));
		panel.add(game);
		
		getNames = new JPanel();
		getNames.setOpaque(false);
		getNames.setLayout(new FlowLayout());
		getNames.setPreferredSize(new Dimension(700,250));
		panel.add(getNames);
		
		enterFirstName = new JLabel("Enter first player's name");
		enterFirstName.setPreferredSize(new Dimension(365,40));
		enterFirstName.setBackground(Color.BLACK);
		enterFirstName.setForeground(Color.WHITE);
		enterFirstName.setHorizontalAlignment(JLabel.CENTER);
		enterFirstName.setFont(new Font(Font.MONOSPACED,Font.BOLD,24));
		enterFirstName.setOpaque(true);
		getNames.add(enterFirstName);
		
		firstPlayerName = new JTextField();
		firstPlayerName.setPreferredSize(new Dimension(336,40));
		firstPlayerName.setBackground(Color.WHITE);
		firstPlayerName.setForeground(Color.BLACK);
		getNames.add(firstPlayerName);
		
		enterSecondName = new JLabel("Enter second player's name");
		enterSecondName.setPreferredSize(new Dimension(365,40));
		enterSecondName.setBackground(Color.BLACK);
		enterSecondName.setForeground(Color.WHITE);
		enterSecondName.setHorizontalAlignment(JLabel.CENTER);
		enterSecondName.setFont(new Font(Font.MONOSPACED,Font.BOLD,24));
		enterSecondName.setOpaque(true);
		getNames.add(enterSecondName);
		
		secondPlayerName = new JTextField();
		secondPlayerName.setPreferredSize(new Dimension(336,40));
		secondPlayerName.setBackground(Color.WHITE);
		secondPlayerName.setForeground(Color.BLACK);
		getNames.add(secondPlayerName);
		
		enterOrExit = new JPanel();
		enterOrExit.setOpaque(false);
		enterOrExit.setLayout(new FlowLayout());
		enterOrExit.setPreferredSize(new Dimension(250,100));
		panel.add(enterOrExit);
		
		enter = new JButton("Enter");
		enter.setPreferredSize(new Dimension(350,40));
		enter.setBackground(Color.BLACK);
		enter.setForeground(Color.WHITE);
		enter.addActionListener(this);
		enterOrExit.add(enter);
		
		exit = new JButton("Exit");
		exit.setPreferredSize(new Dimension(350,40));
		exit.setForeground(Color.white);
		exit.setBackground(Color.black);
		exit.addActionListener(this);
		enterOrExit.add(exit);
		background = ImageIO.read(new File("cap.jpg"));

		
		this.revalidate();
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
        g.drawImage(background,0,0,getWidth(),getHeight(),this);
      } 

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==enter) {
			if(firstPlayerName.getText().equals(""))
				showMessageDialog(this,"Enter a name for the first player!","Error",JOptionPane.ERROR_MESSAGE);
			else if(secondPlayerName.getText().equals(""))
				showMessageDialog(this,"Enter a name for the second player!","Error",JOptionPane.ERROR_MESSAGE);
			else{
				try{
					m.remove(this);
					m.add(new SelectChampionsScreen(m,firstPlayerName.getText(),secondPlayerName.getText()));
					m.revalidate();
					m.repaint();
				}
				catch (IOException ex){
					m.dispose();
				}
			}
		}
		else
			m.dispose();
	}
	


}