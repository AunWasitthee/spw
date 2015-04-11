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
	
	private Image image;
	private String path; 

	public Enemy(int x, int y) {
		super(x, y, 30, 30);

		path = "D:/GitHub/spw/f2/spw/Image/bomb1.png";//location image
		try{
			image = ImageIO.read(new File(path));//read file bomb to image

		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program
	}

	@Override
	public void draw(Graphics2D g) {
		// if(y < Y_TO_FADE)
		// 	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		// else{
		// 	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
		// 			(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		// }
		// g.setColor(Color.GREEN);
		// g.fillRect(x, y, width, height);
		g.drawImage(image,x,y,width,height,null);
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			death();
		}
	}

	public void death(){
		alive = false;
	}
	
	public boolean isAlive(){
		return alive;
	}
}