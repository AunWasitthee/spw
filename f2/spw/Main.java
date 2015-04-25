package f2.spw;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
	JFrame frame = null;
	JFrame frameGame = null;
	JPanel buttonPanel;
	JButton bt_start;
	JButton bt_exit;;
	SpaceShip v;
	GamePanel gp;
	GameEngine engine;
	
	public Main(){
		
		//Setting Main's frame
		frame = new JFrame("Space War Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());

		//Setting button's panel
		buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.setPreferredSize(new Dimension(500, 600));
        buttonPanel.setMaximumSize(new Dimension(500, 600));

        //Setting button start
		bt_start = new JButton("Start Game");
		bt_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	//Create Spaceship, GamePanel, Game Engine
				v = new SpaceShip(180, 525, 50, 50,1);
				gp = new GamePanel();
				engine = new GameEngine(gp, v);

				//Remove button's panel from Main frame
				frame.getContentPane().remove(buttonPanel);

				//add KeyListener and GamePanel to Main frame
				frame.addKeyListener(engine);
				frame.getContentPane().add(gp,BorderLayout.CENTER);

				//Start timmer
				engine.start();

				//Setting MainFrame and re-panting
				frame.validate();
				frame.repaint();

				//Eable KeyListener
				frame.requestFocus();
            }
        });

        //Setting button exit
        bt_exit = new JButton("Close Game");
		bt_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	frame.dispose();
            }
        });

        //add button to button's panel
        buttonPanel.add(bt_start);
        buttonPanel.add(bt_exit);

        //add button's panel to Main frame
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        frame.setVisible(true);
	
	}
		
	public static void main(String[] args){
		//Create Main Class
		new Main();
	}
}
