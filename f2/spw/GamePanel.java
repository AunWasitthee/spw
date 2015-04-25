package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics(); //create pen fpr drawing
		big.setBackground(Color.GRAY); //set color
	}

	//Function for draw GUI
	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600); //clear pannel
		big.setColor(Color.WHITE); // set pen color
		big.drawString(String.format("%08d", reporter.getScore()), 300, 20); //draw score

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

}
