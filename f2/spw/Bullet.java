package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Bullet extends Sprite{
	public static final int Y_TO_FADE = 1000;
	public static final int Y_TO_DIE = 1000;
	private Image image;
	private String path; 

	private int step = 12;
	private boolean alive = true;

	public Bullet(int x, int y) {
		super(x, y, 7, 12);
	}

	//Function for draw graphic
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
	}

	public void proceed(){
		y -= step;
		if(y > Y_TO_DIE){
			death();
		}
	}

	//Function set Bullet to die
	public void death(){
		alive = false;
	}

	//Function get status Bullet
	public boolean isAlive(){
		return alive;
	}
}