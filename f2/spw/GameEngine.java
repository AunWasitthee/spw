package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
	private ArrayList<SpaceShip> spaceships = new ArrayList<SpaceShip>();	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Heart> hearts = new ArrayList<Heart>();	
	private ArrayList<Star> stars = new ArrayList<Star>();	
	private int player;
	private SpaceShip v1;	
	private SpaceShip v2;	
	private Timer timer;
	private long score = 0;
	private double difficulty = 0.05;
	private double rateBullet = 0.1;
	private double rateHeart = 0.003;
	private double rateStar = 0.005;
	private boolean statusGame = true;


	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v1 = v;		
		this.player = 1;
		spaceships.add(v1);
		gp.sprites.add(v1);
		this.v2 = new SpaceShip(180, 525, 50, 50,2); // Create Player2

		//Timer for run process game
		timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});

		//Set timer do duplicate
		timer.setRepeats(true);
	}
	
	//Function for start timmer
	public void start(){
		timer.start();
	}
	
	//Function for pause game.
	public void pauseGame(){
		statusGame = !statusGame; //toggle status game
		//Check status game for pause or resume
		if(!statusGame){
			timer.stop();
		}
		else{
			timer.start();
		}
	}

	//Function stoptimer and write "Game Over" when gameover
	public void die(){
		timer.stop();
		gp.big.setColor(Color.WHITE);
		gp.big.drawString("Game Over", 150, 300);		
	}
	
	//Function for create Enermy
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390-30), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	//Function for create Heart
	private void generateHeart(){
		Heart h = new Heart((int)(Math.random()*390-20), 30);
		gp.sprites.add(h);
		hearts.add(h);
	}

	//Function for create Star
	private void generateStar(){
		Star s = new Star((int)(Math.random()*390-20), 30);
		gp.sprites.add(s);
		stars.add(s);
	}

	//Function for create bullet of Spaceship
	private void generateBullet(SpaceShip v){
		Bullet b = new Bullet((v.getXPos())+(v.getWidth()/2)-3, v.getYPos());
		gp.sprites.add(b);
		bullets.add(b);
	}

	//Function for check everything of game
	private void process(){
		//Condition create Enermy
		if(Math.random() < difficulty){
			generateEnemy();
		}

		//Condition create Heart
		if(Math.random() < rateHeart){
			generateHeart();
		}

		//Condition create Star
		if(Math.random() < rateStar){
			generateStar();
		}

		//Create iterator of object
		Iterator<SpaceShip> ship_iter = spaceships.iterator();
		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Heart> h_iter = hearts.iterator();
		Iterator<Star> s_iter = stars.iterator();
		Iterator<Bullet> b_iter = bullets.iterator();

		//Loop check status of Spaceship for drawing
		while(ship_iter.hasNext()){
			SpaceShip ship = ship_iter.next();
			if(!ship.isAlive()){
				ship_iter.remove();
				gp.sprites.remove(ship);
			}
		}

		//Loop check status of Enermy for drawing and plus score
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}

		//Loop check status of Heart for drawing
		while(h_iter.hasNext()){
			Heart h = h_iter.next();
			h.proceed();
			if(!h.isAlive()){
				h_iter.remove();
				gp.sprites.remove(h);
			}
		}
		
		//Loop check status of Star for drawing
		while(s_iter.hasNext()){
			Star s = s_iter.next();
			s.proceed();
			if(!s.isAlive()){
				s_iter.remove();
				gp.sprites.remove(s);
			}
		}

		//Loop check status of Bullet for drawing
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}

		gp.updateGameUI(this);//Update GUI or re-drawing
		
		//Create rectagle of object for check intersect
		Rectangle2D.Double vr1 = v1.getRectangle();
		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double hr;
		Rectangle2D.Double sr;
		Rectangle2D.Double br;

		//Check Enermy intersect Spaceship and Enermy intersect Bullet
		for(Enemy e : enemies){
			er = e.getRectangle();
			for(Bullet b : bullets){
				br = b.getRectangle();
				//Check Enermy and bullet
				if(br.intersects(er) && (!e.isExplosion())){
					b.death();
					e.setexplosion();
				}
			}

			//Check Enermy and player1
			if(er.intersects(vr1)){
				//v1.death();
				e.death();
			}

			//Check Enermy and player2
			if(v2.isAlive()){
				if(er.intersects(vr2)){
					//v2.death();
					e.death();
				}
			}

		}
	 
		//Check Gameover
		if(!v1.isAlive() && !v2.isAlive()){
			die();
			return;
		}	

		//Check Heart intersect Spaceship
		for(Heart h : hearts){
			hr = h.getRectangle();
			if(hr.intersects(vr1)){
				h.getHeart();
				return;
			}
		}

		//Check Star intersect Spaceship
		for(Star s : stars){
			sr = s.getRectangle();
			if(sr.intersects(vr1)){
				s.getStar();
				return;
			}
		}
		
	}

	//function control game for user's input (keyboard)
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(v1.isAlive()){
				v1.move(-1,0);  // Move Left Player1
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(v1.isAlive()){
				v1.move(1,0);  // Move Right Player1
			}
			break;
		case KeyEvent.VK_UP:
			if(v1.isAlive()){
				v1.move(0,-1);  // Move Up Player1
			}
			break;
		case KeyEvent.VK_DOWN:
			if(v1.isAlive()){
				v1.move(0,1);  // Move Down Player1
			}
			break;
		case KeyEvent.VK_L:
			if(v1.isAlive()){
				generateBullet(v1); // Create Bullet Player1
			}
			break;
		case KeyEvent.VK_SPACE:
			//Check player isn't more than 2 player
			if(this.player < 2){
				//add player2 to game
				v2.born();
				spaceships.add(v2);
				gp.sprites.add(v2);
				//plus number of player
				this.player++;
			}
			break;
		case KeyEvent.VK_A:
			if(v2.isAlive()){
				v2.move(-1,0);  // Move Left Player2
			}
		 	break;
		 case KeyEvent.VK_D:
			if(v2.isAlive()){
				v2.move(1,0);  // Move Right Player2
			}
		 	break;
		 case KeyEvent.VK_W:
			if(v2.isAlive()){
				v2.move(0,-1);  // Move Up Player2
			}
		 	break;
		 case KeyEvent.VK_S:
			if(v2.isAlive()){
				v2.move(0,1);  // Move Down Player2
			}
		 	break;
		 case KeyEvent.VK_G:
		 	if(v2.isAlive()){
				generateBullet(v2); // Create Bullet Player2
			}
			break;
		case KeyEvent.VK_P:
			pauseGame();  // Pause Game
			break;	
		// case KeyEvent.VK_D:
		// 	difficulty += 0.1; // Up level
		// 	break;
		}
	}

	//use interface for send score to GamePanel update
	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
