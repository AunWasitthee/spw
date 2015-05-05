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
	private ArrayList<BulletCat> bulletCats = new ArrayList<BulletCat>();
	private ArrayList<BulletDog> bulletDogs = new ArrayList<BulletDog>();
	private ArrayList<BulletStar> bulletStar = new ArrayList<BulletStar>();
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

	public GameEngine(GamePanel gp, SpaceShip v1, SpaceShip v2,int player) {
		this.gp = gp;
		this.v1 = v1;	
		this.v2 = v2;	
		this.player = player;

		spaceships.add(v1);
		gp.sprites.add(v1);
		//Check 2 player mode or not
		if(player == 2){
			v2.born();
			spaceships.add(v2);
			gp.sprites.add(v2);
		}
		// v2 = new SpaceShip(180, 525, 50, 50,2); // Create Player2

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
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	//Function for create Heart
	private void generateHeart(){
		Heart h = new Heart((int)(Math.random()*390), 30);
		gp.sprites.add(h);
		hearts.add(h);
	}

	//Function for create Star
	private void generateStar(){
		Star s = new Star((int)(Math.random()*390), 30);
		gp.sprites.add(s);
		stars.add(s);
	}

	//Function for create bulletCat of Spaceship
	private void generateBulletCat(SpaceShip v){
		BulletCat bc = new BulletCat((v.getXPos())+(v.getWidth()/2)-12, v.getYPos());
		gp.sprites.add(bc);
		bulletCats.add(bc);
	}

	//Function for create bulletStar of Spaceship
	private void generateBulletStar(SpaceShip v){
		if(v.getNumbulletStar() > 0){
			BulletStar bs = new BulletStar((v.getXPos())+(v.getWidth()/2)-25, v.getYPos());
			gp.sprites.add(bs);
			bulletStar.add(bs);
			v.minusNumbulletStar();
		}
	}

	//Function for create bulletDog of Spaceship
	private void generateBulletDog(SpaceShip v){
		BulletDog bd = new BulletDog((v.getXPos())+(v.getWidth()/2)-12, v.getYPos());
		gp.sprites.add(bd);
		bulletDogs.add(bd);
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
		Iterator<BulletCat> bc_iter = bulletCats.iterator();
		Iterator<BulletDog> bd_iter = bulletDogs.iterator();
		Iterator<BulletStar> bs_iter = bulletStar.iterator();

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
				//score += 100;
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

		//Loop check status of BulletCat for drawing
		while(bc_iter.hasNext()){
			BulletCat bc = bc_iter.next();
			bc.proceed();
			if(!bc.isAlive()){
				bc_iter.remove();
				gp.sprites.remove(bc);
				score += 100;
			}
		}

		//Loop check status of BulletDog for drawing
		while(bd_iter.hasNext()){
			BulletDog bd = bd_iter.next();
			bd.proceed();
			if(!bd.isAlive()){
				bd_iter.remove();
				gp.sprites.remove(bd);
				score += 100;
			}
		}

		//Loop check status of BulletStar for drawing
		while(bs_iter.hasNext()){
			BulletStar bs = bs_iter.next();
			bs.proceed();
			if(!bs.isAlive()){
				bs_iter.remove();
				gp.sprites.remove(bs);
				//score += 100;
			}
		}

		gp.updateGameUI(this);//Update GUI or re-drawing
		
		//Create rectagle of object for check intersect
		Rectangle2D.Double vr1 = v1.getRectangle();
		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double hr;
		Rectangle2D.Double sr;
		Rectangle2D.Double bcr;
		Rectangle2D.Double bdr;
		Rectangle2D.Double bsr;

		//Check Enermy intersect Spaceship and Enermy intersect BulletDog
		for(Enemy e : enemies){
			er = e.getRectangle();
			for(BulletCat bc : bulletCats){
				bcr = bc.getRectangle();
				//Check Enermy and bulletDog
				if(bcr.intersects(er) && (!e.isExplosion())){
					bc.death();
					e.setexplosion();
				}
			}

			for(BulletDog bd : bulletDogs){
				bdr = bd.getRectangle();
				//Check Enermy and bulletDog
				if(bdr.intersects(er) && (!e.isExplosion())){
					bd.death();
					e.setexplosion();
				}
			}

			for(BulletStar bs : bulletStar){
				bsr = bs.getRectangle();
				//Check Enermy and bulletDog
				if(bsr.intersects(er) && (!e.isExplosion())){
					//bd.death();
					e.setexplosion();
					score += 50;
				}
			}

			//Check Enermy and player1
			if(er.intersects(vr1)){
				v1.minusHP(10);
				e.death();
				if(v1.getHP() == 0){
					v1.death();
				}
			}

			//Check Enermy and player2
			if(v2.isAlive()){
				if(er.intersects(vr2)){
					v2.minusHP(10);
					e.death();
					if(v2.getHP() == 0){
						v2.death();
					}
				}
			}

		}
	 
		//Check Gameover
		if(!v1.isAlive() && !v2.isAlive()){
			gp.drawdata(this);
			die();
			return;
		}	

		//Check Heart intersect Spaceship
		for(Heart h : hearts){
			hr = h.getRectangle();
			//Check Heart and player1
			if(hr.intersects(vr1)){
				h.getHeart();
				v1.plusHP(10);
				return;
			}

			//Check Heart and player2
			if(v2.isAlive()){
				if(hr.intersects(vr2)){
					h.getHeart();
					v2.plusHP(10);
					return;
				}
			}
		}

		//Check Star intersect Spaceship
		for(Star s : stars){
			sr = s.getRectangle();
			//Check Star and player1
			if(sr.intersects(vr1)){
				s.getStar();
				v1.plusNumbulletStar();
				return;
			}

			//Check Star and player2
			if(v2.isAlive()){
				if(sr.intersects(vr2)){
					s.getStar();
					v2.plusNumbulletStar();
					return;
				}
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
				generateBulletCat(v1); // Create BulletCat Player1
			}
			break;
		case KeyEvent.VK_K:
			if(v1.isAlive()){
				generateBulletStar(v1); // Create BulletCat Player1
			}
			break;
		// case KeyEvent.VK_SPACE:
		// 	//Check player isn't more than 2 player
		// 	if(this.player < 2){
		// 		//add player2 to game
		// 		v2.born();
		// 		spaceships.add(v2);
		// 		gp.sprites.add(v2);
		// 		//plus number of player
		// 		this.player++;
		// 	}
		// 	break;
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
				generateBulletDog(v2); // Create BulletDog Player2
			}
			break;
		case KeyEvent.VK_H:
		 	if(v2.isAlive()){
				generateBulletStar(v2); // Create BulletDog Player2
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

	public int getplayer(){
		return player;
	}

	public int getHpv1(){
		return v1.getHP();
	}

	public int getHpv2(){
		return v2.getHP();
	}

	public int getnumbulletStar1(){
		return v1.getNumbulletStar();
	}

	public int getnumbulletStar2(){
		return v2.getNumbulletStar();
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
