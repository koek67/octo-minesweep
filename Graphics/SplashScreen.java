package Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class SplashScreen extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image splash;
	public SplashScreen(){
		initImages();
	}
	public void initImages(){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream input = cl.getResourceAsStream("Minesweeper Splash.jpg");
		try {
			splash = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		if(splash != null){
			g2.drawImage(splash,0,0,null);
		}
	}
	
	public void excecute(){
		setSize(585,379);
		
//		int ppi = Toolkit.getDefaultToolkit().getScreenResolution();
//		double height = screen.getHeight();
//		double width = screen.getWidth();
//		double x = Math.pow(height, 2);
//		double y = Math.pow(width, 2);
//		setLocation((int)x/2,(int)y/2);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int widthWindow = getWidth();
		int heightWindow = getHeight();
		int X = (screen.width / 2) - (widthWindow / 2); // Center horizontally.
		int Y = (screen.height / 2) - (heightWindow / 2); // Center vertically.

		setBounds(X,Y , widthWindow,heightWindow);
		setUndecorated(true);
		setResizable(false);
		setVisible(true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setVisible(false);
	}
	public static void main(String[] args){
		SplashScreen s = new SplashScreen();
		s.excecute();
	}
	
}
