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
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Heart> hearts = new ArrayList<Heart>();	
	private ArrayList<Star> stars = new ArrayList<Star>();	
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.05;
	private double rateBullet = 0.1;
	private double rateHeart = 0.003;
	private double rateStar = 0.005;
	private boolean statusGame = true;
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
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

	private void generateBullet(){
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

		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Heart> h_iter = hearts.iterator();
		Iterator<Star> s_iter = stars.iterator();
		Iterator<Bullet> b_iter = bullets.iterator();
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
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double hr;
		Rectangle2D.Double sr;
		Rectangle2D.Double br;
		//Check Enermy intersect Spaceship and Enermy intersect Bullet
		for(Enemy e : enemies){
			er = e.getRectangle();
			for(Bullet b : bullets){
				br = b.getRectangle();
				if(br.intersects(er)){
					b.death();
					e.death();
					return;
				}
				if(er.intersects(vr)){
					//die();
					return;
				}
			}	
		}

		for(Heart h : hearts){
			hr = h.getRectangle();
			if(hr.intersects(vr)){
				h.getHeart();
				return;
			}
		}

		for(Star s : stars){
			sr = s.getRectangle();
			if(sr.intersects(vr)){
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
			v.move(-1,0);  // Move Left
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1,0);  // Move Right
			break;
		case KeyEvent.VK_UP:
			v.move(0,-1);  // Move Up
			break;
		case KeyEvent.VK_DOWN:
			v.move(0,1);  // Move Down
			break;
		case KeyEvent.VK_P:
			pauseGame();  // Pause Game
			break;
		case KeyEvent.VK_SPACE:
			generateBullet(); // Create Bullet
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
