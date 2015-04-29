package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class BulletCat extends Sprite{
	public static final int Y_TO_FADE = 1000;
	public static final int Y_TO_DIE = 1000;
	private Image image;
	private String path = "f2/spw/Image/fishbone.png";//location image; 

	private int step = 12;
	private boolean alive = true;

	public BulletCat(int x, int y) {
		super(x, y, 25, 25);
		try{
			image = ImageIO.read(new File(path));//read file fishbone to image

		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program
	}

	//Function for draw graphic
	@Override
	public void draw(Graphics2D g) {
		// g.setColor(Color.GREEN);
		// g.fillRect(x, y, width, height);
		g.drawImage(image,x,y,width,height,null);
	}

	public void proceed(){
		y -= step;
		if(y > Y_TO_DIE){
			death();
		}
	}

	//Function set Bulletfish to die
	public void death(){
		alive = false;
	}

	//Function get status Bulletfish
	public boolean isAlive(){
		return alive;
	}
}