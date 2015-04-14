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
	private SpaceShip v1;	
	private SpaceShip v2;	
	private int player;
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
		this.v2 = new SpaceShip(180, 525, 70, 70,2); // Create Player2
		timer = new Timer(30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	

	private void generateHeart(){
		Heart h = new Heart((int)(Math.random()*390), 30);
		gp.sprites.add(h);
		hearts.add(h);
	}

	private void generateStar(){
		Star s = new Star((int)(Math.random()*390), 30);
		gp.sprites.add(s);
		stars.add(s);
	}

	private void generateBullet(SpaceShip v){
		Bullet b = new Bullet((v.x)+(v.width/2)-3, v.y);
		gp.sprites.add(b);
		bullets.add(b);
	}


	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}

		if(Math.random() < rateHeart){
			generateHeart();
		}

		if(Math.random() < rateStar){
			generateStar();
		}

		// if(Math.random() < rateBullet){
		// 	generateBullet();
		// }
		Iterator<SpaceShip> ship_iter = spaceships.iterator();
		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Heart> h_iter = hearts.iterator();
		Iterator<Star> s_iter = stars.iterator();
		Iterator<Bullet> b_iter = bullets.iterator();
		while(ship_iter.hasNext()){
			SpaceShip ship = ship_iter.next();
			if(!ship.isAlive()){
				ship_iter.remove();
				gp.sprites.remove(ship);
				score += 100;
			}
		}

		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}

		while(h_iter.hasNext()){
			Heart h = h_iter.next();
			h.proceed();
			if(!h.isAlive()){
				h_iter.remove();
				gp.sprites.remove(h);
				//score += 200;
			}
		}
		
		while(s_iter.hasNext()){
			Star s = s_iter.next();
			s.proceed();
			if(!s.isAlive()){
				s_iter.remove();
				gp.sprites.remove(s);
				//score += 200;
			}
		}

		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
				//score += 100;
			}
		}

		gp.updateGameUI(this);
		
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
				if(br.intersects(er)){
					b.death();
					e.death();
				}
			}

			//Check Enermy and player1
			if(er.intersects(vr1)){
				v1.death();
				e.death();
			}

			//Check Enermy and player2
			if(v2.isAlive()){
				if(er.intersects(vr2)){
					v2.death();
					e.death();
				}
			}

		}
	 
		//Check Gameover
		if(!v1.isAlive() && !v2.isAlive()){
			die();
			return;
		}	


		for(Heart h : hearts){
			hr = h.getRectangle();
			if(hr.intersects(vr1)){
				h.getHeart();
				return;
			}
		}

		for(Star s : stars){
			sr = s.getRectangle();
			if(sr.intersects(vr1)){
				s.getStar();
				return;
			}
		}

		
	}
	
	public void die(){
		timer.stop();
		gp.big.setColor(Color.WHITE);
		gp.big.drawString("Game Over", 150, 300);
	}
	//Function for pause game.
	public void pauseGame(){
		statusGame = !statusGame;
		if(!statusGame){
			timer.stop();
		}
		else{
			timer.start();
		}
		
	}
	//function control game for user's input (keyboard)
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v1.move(-1,0);  // Move Left Player1
			break;
		case KeyEvent.VK_RIGHT:
			v1.move(1,0);  // Move Right Player1
			break;
		case KeyEvent.VK_UP:
			v1.move(0,-1);  // Move Up Player1
			break;
		case KeyEvent.VK_DOWN:
			v1.move(0,1);  // Move Down Player1
			break;
		case KeyEvent.VK_P:
			pauseGame();  // Pause Game
			break;
		case KeyEvent.VK_L:
			generateBullet(v1); // Create Bullet Player1
			break;
		case KeyEvent.VK_SPACE:
			if(this.player < 2){
				v2.born();
				spaceships.add(v2);
				gp.sprites.add(v2);
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
		// case KeyEvent.VK_D:
		// 	difficulty += 0.1; // Up level
		// 	break;
		}
	}

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
