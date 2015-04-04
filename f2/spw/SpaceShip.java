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
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		path = "D:/GitHub/spw/f2/spw/Image/cat.png";//location image

		try{
			image = ImageIO.read(new File(path));//read file cat to image

		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program
	}
	//function draw graphic Spaceship
	@Override
	public void draw(Graphics2D g) {
		// g.setColor(Color.WHITE);
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

}
