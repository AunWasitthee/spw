package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Enemy extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 5;
	private boolean alive = true;
	private int timeBomb = 13;
	private Image image;
	private String path = "f2/spw/Image/bomb1.png";//location image
	private String path2 = "f2/spw/Image/explosion.png";//location image
	private boolean explosion;

	public Enemy(int x, int y) {
		super(x, y, 30, 30);
		try{
			image = ImageIO.read(new File(path));//read file bomb to image

		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program

		explosion = false;
	}

	//Function for draw graphic
	@Override
	public void draw(Graphics2D g) {
		// g.setColor(Color.RED);
		// g.fillRect(x, y, width, height);
		g.drawImage(image,x,y,width,height,null);
	}

	public void proceed(){
		//Check bomb or not?
		if(!explosion){
			y += step;
			if(y > Y_TO_DIE){
				death();
			}
		}
		else{
			timeBomb--; //reduce time to show bomb
			y++;
		}

		//Check bomb shoe timeout
		if(timeBomb == 0){
			death();
		}
	}

	//Function set Enermy to die
	public void death(){
		alive = false;
	}

	//Function get status Enermy
	public boolean isAlive(){
		return alive;
	}

	//Function Changepicture Enermey to bomb and set status explosion
	public void setexplosion(){
		try{
			image = ImageIO.read(new File(path2));//read file bomb to image
		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program

		//set status explosion bomb
		explosion = true;
	}

	//Function get explosionstatus Enermy
	public boolean isExplosion(){
		return explosion;
	}
}