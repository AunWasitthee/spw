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
	JButton bt_1player;
	JButton bt_2player;
	JButton bt_exit;;
	SpaceShip v;
	SpaceShip v2;

	GamePanel gp;
	GameEngine engine;
	
	public Main(){
		
		//Setting Main's frame
		frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(650, 620);
		frame.getContentPane().setLayout(new BorderLayout());

		//Setting button's panel
		buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setPreferredSize(new Dimension(500, 600));
        buttonPanel.setMaximumSize(new Dimension(500, 600));

        //Setting button start
		bt_1player = new JButton("1 Player");
		bt_1player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	//Create Spaceship, GamePanel, Game Engine
				v = new SpaceShip(180, 525, 50, 50,1);	// Create Player1
				v2 = new SpaceShip(180, 525, 50, 50,2); // Create Player2
				gp = new GamePanel();
				//New Game Single mode
				engine = new GameEngine(gp, v, v2,1);


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
		bt_2player = new JButton("2 Player");
		bt_2player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	//Create Spaceship, GamePanel, Game Engine
				v = new SpaceShip(120, 525, 50, 50,1);	// Create Player1
				v2 = new SpaceShip(230, 525, 50, 50,2); // Create Player2
				gp = new GamePanel();
				//New Game 2 players mode
				engine = new GameEngine(gp, v, v2,2);

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
        buttonPanel.add(bt_1player);
        buttonPanel.add(bt_2player);
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
