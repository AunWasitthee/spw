package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Heart extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;

	private Image image;
	private String path; 
	private int step = 12;
	private boolean alive = true;

	public Heart(int x, int y) {
		super(x, y, 20, 20);

		path = "f2/spw/Image/heart.png";//location image
		try{
			image = ImageIO.read(new File(path));//read file bomb to image

		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image,x,y,width,height,null);
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			getHeart();
		}
	}

	//Funtion set Heart be keep
	public void getHeart(){
		alive = false;
	}

	//Function get status Heart
	public boolean isAlive(){
		return alive;
	}
}