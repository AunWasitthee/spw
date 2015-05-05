package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Star extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private Image image;
	private String path; 
	private int step = 12;
	private boolean alive = true;
	

	public Star(int x, int y) {
		super(x, y, 20, 20);

		path = "f2/spw/Image/star.png";//location image
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
			getStar();
		}
	}

	//Funtion set Star be keep
	public void getStar(){
		alive = false;
	}

	//Function get status Star
	public boolean isAlive(){
		return alive;
	}
}