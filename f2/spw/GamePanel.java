package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Image image;
	private Image bg;
	private String path = "f2/spw/Image/star.png";
	private String path1 = "f2/spw/Image/bg.jpg";
	public GamePanel() {
		bi = new BufferedImage(650, 650, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics(); //create pen fpr drawing
		big.setBackground(Color.GRAY); //set color
		try{
			image = ImageIO.read(new File(path));//read file star to image

		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program
		try{
			bg = ImageIO.read(new File(path1));//read file star to image

		}catch(IOException e){//focus input output
			e.printStackTrace();
		}// check error for run program
	}

	//Function for draw GUI
	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 650, 650); //clear pannel
		big.drawImage(bg, 0,0 , 450, 600, null);//draw BG
		//draw BG information
		big.setColor(Color.PINK);
		big.fillRect(450, 0, 250, 600);
		big.setColor(Color.BLACK); // set pen color
		big.drawString(String.format("Score : %08d", reporter.getScore()), 480, 20); //draw score
		drawdata(reporter);
		//loop for draw object which is extends Sprite
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}
	public void drawdata(GameReporter reporter) {
		
		big.drawString((String.format("Mode : %d player",reporter.getplayer())), 480,50);
		big.drawString((String.format("Player1 's HP")),480,80);
		big.setColor(Color.RED);
		big.fillRect(480, 90, 100, 20);
		big.setColor(Color.GREEN);
		for(int i = reporter.getHpv1(),j=0 ; i>0 ; i-=10,j+=10){
			big.fillRect(480+j, 90, 10, 20);
		}
		big.drawImage(image, 480,110 , 20, 20, null);
		big.setColor(Color.BLACK);
		big.drawString(String.format("%d",reporter.getnumbulletStar1() ) ,510,125);

		if(reporter.getplayer() > 1){
			big.drawString((String.format("Player2 's HP")),480,160);
			big.setColor(Color.RED);
			big.fillRect(480, 170, 100, 20);
			big.setColor(Color.GREEN);
			for(int i = reporter.getHpv2(),j=0 ; i>0 ; i-=10,j+=10){
				big.fillRect(480+j, 170, 10, 20);
			}
			//big.drawString("HP " + (String.format("%d", reporter.getHpv2())),450,180);
			big.drawImage(image, 480, 190, 20, 20, null);
			big.setColor(Color.BLACK);
			big.drawString(String.format("%d", reporter.getnumbulletStar2()),510,205);
			
		}
	}

}
