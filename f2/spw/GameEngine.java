package f2.spw;

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
	private ArrayList<Heart> heart = new ArrayList<Heart>();	
	private ArrayList<Star> star = new ArrayList<Star>();	
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	private double rateHeart = 0.06;
	private double rateStar = 0.01;
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
		heart.add(h);
	}

	private void generateStar(){
		Star s = new Star((int)(Math.random()*390), 30);
		gp.sprites.add(s);
		star.add(s);
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
		
		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Heart> h_iter = heart.iterator();
		Iterator<Star> s_iter = star.iterator();
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

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double hr;
		Rectangle2D.Double sr;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				//die();
				return;
			}
		}

		for(Heart h : heart){
			hr = h.getRectangle();
			if(hr.intersects(vr)){
				//die();
				return;
			}
		}

		for(Star s : star){
			sr = s.getRectangle();
			if(sr.intersects(vr)){
				//die();
				return;
			}
		}
	}
	
	public void die(){
		timer.stop();
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
		case KeyEvent.VK_D:
			difficulty += 0.1; // Up level
			break;
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
