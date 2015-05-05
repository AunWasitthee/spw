package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class SpaceShip extends Sprite{
	int step = 8;
	private Image image;
	private String path;
	private boolean alive;
	private int hp;
	private int numbulletStar;
	
	public SpaceShip(int x, int y, int width, int height,int player) {
		super(x, y, width, height);
		hp = 100;
		numbulletStar = 0;
		//Select image for player
		if(player == 1){
			path = "f2/spw/Image/cat.png";//location image player1 
			alive = true;
		}
		else{
			path = "f2/spw/Image/dog.png";//location image player2
			alive = false;
		}

		try{
			image = ImageIO.read(new File(path));//read file cat to image
		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program
	}

	//function draw graphic Spaceship
	@Override
	public void draw(Graphics2D g) {
		// g.setColor(Color.GREEN);
		// g.fillRect(x, y, width, height);
		g.drawImage(image,x,y,width,height,null);
	}

	//Funcytion move Space ship
	public void move(int directionX, int directionY){

		//Calculate positionX, positionY of Spaceship
		x += (step * directionX);
		y += (step * directionY);

		//Condition check out of Frame
		//X-ais
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
		//Y-ais
		if( y <= 30 )
			y = 30;
		if(y >= 600 - height)
			y = 600 - height;
	}

	//Function get X-Position Spaceship
	public int getXPos(){
		return x;
	}

	//Function get Y-Position Spaceship
	public int getYPos(){
		return y;
	}

	//Function get Width Spaceship
	public int getWidth(){
		return width;
	}

	//Function get Height Spaceship
	public int getHeight(){
		return height;
	}

	//Function set Spaceship die
	public void death(){
		alive = false;
	}

	//Function set Spaceship born
	public void born(){
		alive = true;
	}

	//Function get status Spacship
	public boolean isAlive(){
		return alive;
	}

	//Function minus HP Spaceship
	public void minusHP(int num){
		hp -= num;
	}

	//Function plus HP Spaceship
	public void plusHP(int num){
		if(hp+num <= 100){
			hp += num;
		}
	}

	//Function get HP Spaceship
	public int getHP(){
		return hp;
	}
	
	//Function minus BulletStar
	public void minusNumbulletStar(){
		numbulletStar--;
	}

	//Function plus BulletStar
	public void plusNumbulletStar(){
		numbulletStar++;
	}

	//Function get BulletStar
	public int getNumbulletStar(){
		return numbulletStar;
	}
}
