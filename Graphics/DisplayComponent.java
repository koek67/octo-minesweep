package Graphics;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MouseInputAdapter;

import Game.Minesweeper;

public class DisplayComponent extends JComponent{
	private static final long serialVersionUID = 2841666517956746362L;
	// 1 = 8x8; 2 = 16x16; 3 = 16x30;
	private int difficulty = -1;
	private int numRows,numCols;
	private Rectangle2D.Double[][] rectangles;
	private Minesweeper game;
	private int bombIndex = 0;
	private int CELL_SIZE = 28;
	static int width,height;
	JFrame f;
	private boolean isFirstClick = true;
	private Image mine = null;
	private Image flag = null;
	private Image one = null;
	private Image two = null;
	private Image three = null;
	private Image four = null;
	private Image five = null;
	private Image six = null;
	private Image seven = null;
	private Image eight = null;
	private int timeSecs = 0;
	private int timeMilli = 0;
	private int timeMicro = 0;
	private int timeMoreSecs = 0;
	private int flagCount = 0;
	private JLabel timerLabel = new JLabel("0" + timeSecs + ":" + "0" + timeMilli + "." + "0" + timeMicro);
	private JLabel flagsLabel = new JLabel(flagCount + " bombs");
	private JPanel labelPanel = new JPanel(new FlowLayout());
	private GraphicsDevice vc;
	private JButton newGame = new JButton("New Game");
	private JMenuBar mb = new JMenuBar();
	private Point highlightedRect = null;
	private Timer timer;
	private JMenu settingsMenu;
	private JMenu setColorMenu;
	private JMenuItem heat,celtics,patriots,knicks,bobcats;
	private Color currentColor;
	private Color borderColor;
	private Color highlightedColor;
	private JMenu gameMenu;
	private JMenu newGameMenu;
	private JMenuItem beginner;
	private JMenuItem inter;
	private JMenuItem exprt;
	private JMenuItem closeMenuItem;
	private JMenu about;
	private boolean menusAdded = false;
	private SplashScreen splash = new SplashScreen();
	
	// constructor: set difficulty
	public DisplayComponent(){
		currentColor = new Color(0,139,69);
		borderColor = Color.black;
		highlightedColor = Color.white;
		initImages();
	}
	public void makeMenus(){
		gameMenu = new JMenu("Game");
		newGameMenu = new JMenu("New Game");
		beginner = new JMenuItem("Beginner");
		beginner.setActionCommand("Beginner");
		inter = new JMenuItem("Intermediate");
		inter.setActionCommand("Intermediate");
		exprt = new JMenuItem("Expert");
		exprt.setActionCommand("Expert");
		JLabel info = new JLabel("  BOSS Minesweeper Inc.");
		JLabel author = new JLabel("  Copyright " + "\u00a9" + " 2012 Koushik Krishnan  ");
		newGameMenu.add(beginner);
		newGameMenu.add(inter);
		newGameMenu.add(exprt);
		closeMenuItem = new JMenuItem("Exit");
		closeMenuItem.setActionCommand("Close");
		gameMenu.add(newGameMenu);
		gameMenu.add(new JSeparator());
		gameMenu.add(closeMenuItem);
		about = new JMenu("About");
		about.add(info);
		about.add(new JSeparator());
		about.add(author);
		
		settingsMenu = new JMenu("Settings");
		setColorMenu = new JMenu("Themes");
		heat = new JMenuItem("Heat");
		celtics = new JMenuItem("Celtics");
		patriots = new JMenuItem("Patriots");
		knicks = new JMenuItem("Knicks");
		bobcats = new JMenuItem("Bobcats");
		
		heat.setActionCommand(heat.getName());
		celtics.setActionCommand(celtics.getName());
		patriots.setActionCommand(patriots.getName());
		knicks.setActionCommand(knicks.getName());
		bobcats.setActionCommand(bobcats.getName());
		
		setColorMenu.add(heat);
		setColorMenu.add(celtics);
		setColorMenu.add(patriots);
		setColorMenu.add(knicks);
		setColorMenu.add(bobcats);
		
		settingsMenu.add(setColorMenu);
		mb.add(gameMenu);
		mb.add(settingsMenu);
		mb.add(about);
		menusAdded = true;
	}
	private class MenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String com = arg0.getActionCommand();
			if(com.equals("Beginner")){
				difficulty = 1;
				newGame();
			}
			else if(com.equals("Intermediate")){
				difficulty = 2;
				newGame();
			}
			else if(com.equals("Expert")){
				difficulty = 3;
				newGame();
			}
			else if(com.equals("Close")){
				System.exit(0);
			}
			else if(com.equals("Heat")){
				currentColor = Color.black;
				borderColor = Color.red;
				highlightedColor = Color.white;
				repaint();
			}
			else if(com.equals("Celtics")){
				currentColor = new Color(0,139,69);
				borderColor = Color.black;
				highlightedColor = Color.white;
				repaint();
			}
			else if(com.equals("Patriots")){
				currentColor = new Color(0,0,170);
				borderColor = new Color(207,207,207);
				highlightedColor = new Color(235,0,0);
				repaint();
			}
			else if(com.equals("Knicks")){
				currentColor = new Color(255,97,3);
				borderColor = Color.white;
				highlightedColor = new Color(28,134,238);
				repaint();
			}
			else if(com.equals("Bobcats")){
				currentColor = new Color(69,89,171);
				borderColor = new Color(158,158,158);
				highlightedColor = new Color(205,55,0);
				repaint();
			}
		}
		
	}
	// given difficulty, set numRows and numCols
	public void setDimensions(){
		switch(difficulty){
		case 1:{
			numRows = 8;
			numCols = 8;
			bombIndex = 10;
			flagCount = bombIndex;
			flagsLabel.setText(flagCount + " bombs");
			width = numCols*CELL_SIZE + 120;
			height = numRows*CELL_SIZE + 10;
			break;
		}
		case 2:{
			numRows = 16;
			numCols = 16;
			bombIndex = 40;
			flagCount = bombIndex;
			flagsLabel.setText(flagCount + " bombs");
			width = numCols*CELL_SIZE + 120;
			height = numRows*CELL_SIZE + 10;
			break;
		}
		case 3:{
			numRows = 32;
			numCols = 16;
			bombIndex = 99;
			flagCount = bombIndex;
			flagsLabel.setText(flagCount + " bombs");
			width = numCols*CELL_SIZE + 120;
			height = numRows*CELL_SIZE + 10;
			break;
		}
		}
	}
	
	public void drawFlag(int i, int j, Graphics2D g2){
		Color initial = g2.getColor();
		int rectHeight = (int)(CELL_SIZE/2.5);
		int rectWidth = (int)(CELL_SIZE/3);
		int buffer = (int)(CELL_SIZE/5);
		if(currentColor.equals(Color.black) || currentColor.equals(Color.blue)){
			g2.setColor(Color.white);
		}
		else {
			g2.setColor(Color.black);
		}
		
		g2.drawRect(CELL_SIZE*i +buffer,CELL_SIZE*j +buffer,rectHeight,rectWidth);
		
		g2.fillRect(CELL_SIZE*i +buffer,CELL_SIZE*j +buffer,rectHeight,rectWidth);
		
		int startX = CELL_SIZE*i +buffer + rectHeight;
		int startY = CELL_SIZE*j +buffer + rectWidth;
		g2.drawLine(startX, startY, startX, startY+ (int)(CELL_SIZE/2.8));
		g2.setColor(initial);
		
	}
	
	//ublic static void main(String)
	public void initRectangles(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				rectangles[i][j] = new Rectangle2D.Double(i*CELL_SIZE,j*CELL_SIZE,CELL_SIZE,CELL_SIZE);
			}
		}
	}
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(game.hasFlag(i,j)){
					if(!game.hasBomb(i, j) && game.getEndGame()){
						g2.setColor(Color.LIGHT_GRAY);
						g2.fill(rectangles[i][j]);
						//g2.fill3DRect((int)rectangles[i][j].getX(),(int)rectangles[i][j].getY(),(int)rectangles[i][j].getWidth(),(int)rectangles[i][j].getHeight(),true);
						g2.setStroke(new BasicStroke(2));
						g2.setColor(borderColor);
						g2.draw(rectangles[i][j]);
						g2.setStroke(new BasicStroke(1));
						placeWrongFlag(g2,i,j);
					}
					else{
						g2.setColor(currentColor);
						g2.fill(rectangles[i][j]);
						//g2.fill3DRect((int)rectangles[i][j].getX(),(int)rectangles[i][j].getY(),(int)rectangles[i][j].getWidth(),(int)rectangles[i][j].getHeight(),true);
						g2.setColor(borderColor);
						g2.setStroke(new BasicStroke(2));
						g2.draw(rectangles[i][j]);
						g2.setStroke(new BasicStroke(1));
						drawFlag(i,j,g2);
						//placeFlag(g2,i,j);
					}
					continue;
				}
				if(game.isPressed(i,j) == false){
					if((new Point(i,j)).equals(highlightedRect)){
						g2.setColor(highlightedColor);
						g2.fill3DRect((int)rectangles[i][j].getX(),(int)rectangles[i][j].getY(),(int)rectangles[i][j].getWidth(),(int)rectangles[i][j].getHeight(),true);
						g2.setColor(borderColor);
						g2.setStroke(new BasicStroke(2));
						g2.draw(rectangles[i][j]);
						g2.setStroke(new BasicStroke(1));
					}
					else{
						g2.setColor(currentColor);
						g2.fill(rectangles[i][j]);
						//g2.fill3DRect((int)rectangles[i][j].getX(),(int)rectangles[i][j].getY(),(int)rectangles[i][j].getWidth(),(int)rectangles[i][j].getHeight(),true);
						g2.setColor(borderColor);
						g2.setStroke(new BasicStroke(2));
						g2.draw(rectangles[i][j]);
						g2.setStroke(new BasicStroke(1));
					}


				}
				else{
					g2.setColor(Color.LIGHT_GRAY);
					g2.fill(rectangles[i][j]);
					g2.setColor(borderColor);
					g2.setStroke(new BasicStroke(2));
					g2.draw(rectangles[i][j]);
					g2.setStroke(new BasicStroke(1));
					if(game.hasBomb(i, j)){
						placeBomb(g2,i,j);
					}		
					if(game.getBombNum(i,j) != 0)
						placeNum(g2,i,j,game.getBombNum(i, j));

				}
			}
		}
	}
	public void placeBomb(Graphics2D g2, int i, int j){
		int x = i*CELL_SIZE;
		int y = j*CELL_SIZE;
		int gap = (CELL_SIZE - 24)/2;
		g2.drawImage(mine, x+gap, y+gap, null);
	}
	public void placeFlag(Graphics2D g2, int i, int j){
		int x = i*CELL_SIZE;
		int y = j*CELL_SIZE;
		int gap = (CELL_SIZE - 24)/2;
		g2.drawImage(flag, x+gap, y+gap, null);
	}
	public void placeWrongFlag(Graphics2D g2, int i, int j){
		int x = i*CELL_SIZE;
		int y = j*CELL_SIZE;
		int gap = (CELL_SIZE - 24)/2;
		g2.drawImage(mine, x+gap, y+gap, null);
		g2.setColor(Color.magenta);
		g2.setStroke(new BasicStroke(2));
		g2.draw(new Line2D.Double(i*CELL_SIZE,j*CELL_SIZE,i*CELL_SIZE+CELL_SIZE,j*CELL_SIZE+CELL_SIZE));
		g2.draw(new Line2D.Double(i*CELL_SIZE+CELL_SIZE,j*CELL_SIZE,i*CELL_SIZE,j*CELL_SIZE+CELL_SIZE));
		g2.setStroke(new BasicStroke(1));
	}
	public void placeNum(Graphics2D g2, int i, int j, int num){
		int x = i*CELL_SIZE;
		int y = j*CELL_SIZE;
		int gap = (CELL_SIZE - 24)/2;
		x += gap;
		y += gap;
		switch(num){
		case 1:{
			g2.drawImage(one,x,y,null);
			break;
		}
		case 2:{
			g2.drawImage(two,x,y,null);
			break;
		}
		case 3:{
			g2.drawImage(three,x,y,null);
			break;
		}
		case 4:{
			g2.drawImage(four,x,y,null);
			break;
		}
		case 5:{
			g2.drawImage(five,x,y,null);
			break;
		}
		case 6:{
			g2.drawImage(six,x,y,null);
			break;
		}
		case 7:{
			g2.drawImage(seven,x,y,null);
			break;
		}
		case 8:{
			g2.drawImage(eight,x,y,null);
			break;
		}
		}
	}
	@SuppressWarnings("unused")
	private class NewGameListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			timer.stop();
			isRunning = false;
			newGame();
		}

	}
	private boolean isRunning = false;
	private class ClickListener extends MouseInputAdapter{

		public ClickListener(){}
		public void mousePressed(MouseEvent e){
			if(isFirstClick && e.getButton() == MouseEvent.BUTTON3){
				handleRightClick(e.getX(), e.getY());
				return;
			}
			if(!isRunning){
				timer.start();
				isRunning = true;
			}
			game.calculateWin();
			if (!game.getEndGame()) {
				if (isFirstClick && e.getButton() == MouseEvent.BUTTON1) {
					handleFirstClick(e.getX(), e.getY());
					isFirstClick = false;
				}
				if (e.getModifiersEx() == 5120) {
					handleDualClick(e.getX(), e.getY());
				} else {
					switch (e.getButton()) {
					case MouseEvent.BUTTON1: {
						handleLeftClick(e.getX(), e.getY());
						break;
					}
					case MouseEvent.BUTTON3: {
						handleRightClick(e.getX(), e.getY());
						break;
					}
					}
				}
			}
			game.calculateWin();
			if(game.getEndGame()){
				gameOver();
			}
			
			repaint();
		}
		public void handleFirstClick(int x, int y){
			System.out.println("first click");
			for(int i = 0; i < numRows; i++){
				for(int j = 0; j < numCols; j++){

					if(rectangles[i][j].contains(new Point(x,y))){
						//						System.out.println("First");
						//						System.out.println(game.getBombsGrid());
						game.fillWithBombs(i,j);
						game.placeNumbers();
						game.expand(i, j);
						//						System.out.println("second");
						//						System.out.println(game.getBombsGrid());
						repaint();
						return;
					}	
				}
			}
		}
		public void handleLeftClick(int x, int y){
			System.out.println("l");
			for(int i = 0; i < numRows; i++){
				for(int j = 0; j < numCols; j++){

					if(rectangles[i][j].contains(new Point(x,y))){
						game.expand(i, j);
						repaint();
						return;
					}	
				}
			}	
		}

		public void handleRightClick(int x, int y){
			System.out.println("r");
				for (int i = 0; i < numRows; i++) {
					for (int j = 0; j < numCols; j++) {
						if (rectangles[i][j].contains(new Point(x, y))) {
							if (game.isPressed(i, j) == false) {
								game.toggleFlag(i, j);
								if (game.hasFlag(i, j)) {
									flagCount--;
									flagsLabel.setText(flagCount + " bombs");
								} else {
									flagCount++;
									flagsLabel.setText(flagCount + " bombs");
								}

							}

							repaint();
							return;
						}

					}
				}

		}
		public void handleDualClick(int x, int y){
			for(int i = 0; i < numRows; i++){
				for(int j = 0; j < numCols; j++){

					if(rectangles[i][j].contains(new Point(x,y))){
						game.dualClick(i, j);
						repaint();
						return;
					}	
				}
			}
		}

	}
	private class MotionListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			for(int i = 0; i < numRows; i++){
				for(int j = 0; j < numCols; j++){
					if(rectangles[i][j].contains(e.getX(),e.getY())){
						highlightedRect = new Point(i,j);
						break;
					}
				}
			}
			repaint();
		}

	}
	private class TimerListener implements ActionListener{

		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(timeMicro == 9){
				if(timeMilli == 59){
					timeSecs++;
					timeMicro = 0;
					timeMilli = 0;
				}
				else{
					timeMicro = 0;
					timeMilli++;
				}
			}
			else{
				timeMicro++;
			}
			String micro = "0" + timeMicro;
			String milli = "" + timeMilli;
			String secs = "" + timeSecs;
			String more = "" + timeMoreSecs;
			if(timeMilli < 10)
				milli = "0" + timeMilli;
			if(timeSecs < 10)
				secs = "0" + timeSecs;
			//System.out.println("secs: " + secs);
			timerLabel.setText(secs + ":" + milli + "." + micro);
			repaint();
		}

	}
	public void gameOver(){
		timer.stop();

		if(game.getWin()){
			JOptionPane.showMessageDialog(null, "You win!");
			removeMouseListener();
			repaint();
		}
		else{
			game.showAllBombs();
			JOptionPane.showMessageDialog(null, "You lose!");
			removeMouseListener();
			repaint();
		}
	}
	@SuppressWarnings("unused")
	private JButton close = new JButton("close");

	public void initImages(){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		InputStream input = cl.getResourceAsStream("bomb.jpg");
		try {
			mine = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("flag.jpg");
		try {
			flag = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("1.jpg");
		try {
			one = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("2.jpg");
		try {
			two = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("3.jpg");
		try {
			three = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("4.jpg");
		try {
			four = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("5.jpg");
		try {
			five = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("6.jpg");
		try {
			six = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("7.jpg");
		try {
			seven = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input = cl.getResourceAsStream("8.jpg");
		try {
			eight = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void excecute(){
		splash.excecute();
		newGame();
	}
	public void restart(){
//		Object[] options = {"Beginner",
//				"Intermediate","Expert"};
//		int n = 9;
//		do {
//			n = JOptionPane.showOptionDialog(f, "Choose your difficulty",
//					"New Game", JOptionPane.YES_NO_CANCEL_OPTION,
//					JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
//			if(n == -1){
//				System.exit(0);
//			}
//		} while (n == 9);
		isRunning = false;
		timeMilli = 0;
		timeSecs = 0;
		timeMicro = 0;
		timeMoreSecs = 0;
		timerLabel.setText("0" + timeSecs + ":" + "0" + timeMilli + "." + "0" + timeMicro);
		if(difficulty == -1){
			difficulty = 1;
		}
		setDimensions();
		game = new Minesweeper(numRows, numCols, bombIndex);
		rectangles = new Rectangle2D.Double[numRows][numCols];
		initRectangles();
		//		game.fillWithBombs();
		//		game.placeNumbers();
		isFirstClick = true;
		initImages();
		removeListener();
		repaint();
		start();
	}
	public void newGame(){
//		Object[] options = {"Beginner",
//				"Intermediate","Expert"};
//		int n = 9;
//		do {
//			n = JOptionPane.showOptionDialog(f, "Choose your difficulty",
//					"New Game", JOptionPane.YES_NO_CANCEL_OPTION,
//					JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
//			if(n == -1){
//				System.exit(0);
//			}
//		} while (n == 9);
		isRunning = false;
		timeMilli = 0;
		timeSecs = 0;
		timeMicro = 0;
		timeMoreSecs = 0;
		timerLabel.setText("0" + timeSecs + ":" + "0" + timeMilli + "." + "0" + timeMicro);
		if(difficulty == -1){
			difficulty = 1;
		}
		setDimensions();
		game = new Minesweeper(numRows, numCols, bombIndex);
		rectangles = new Rectangle2D.Double[numRows][numCols];
		initRectangles();
		//		game.fillWithBombs();
		//		game.placeNumbers();
		isFirstClick = true;
		initImages();
		removeListener();
		repaint();
		start();
	}
	private ActionListener timerListener;
	private MouseListener click;
	private ActionListener ng;
	private MouseMotionListener motion;
	private ActionListener menuListener;
	private Action action;
	JPanel buttonPanel = new JPanel();
	private boolean actionAdded = false;
	@SuppressWarnings("unused")
	private boolean buttonAdded = false;
	@SuppressWarnings("unused")
	private boolean sepAdded = false;
	JLabel spacer = new JLabel(" || ");
	@SuppressWarnings("serial")
	public void init(final JFrame f){
		Container cp = f.getContentPane();

		removeSpacer();
		this.f = f;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

//		int X = screen.width/2;
//		int Y = screen.height/2;
//		int dx = width/2;
//		int dy = height/2;
//		System.out.println(X + "  " + Y+ "  " +dx+ "  " +dy);
//		f.setLocation(X-dx,Y-dy);
//		f.setLocationRelativeTo(getRootPane());
		// Determine the new location of the window
		int w = f.getSize().width;
		int h = f.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		 
		// Move the window
		f.setLocation(x, y);
		click = new ClickListener();
		this.addMouseListener(click);
			action = new AbstractAction("New Game") {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					isRunning = false;
					timeMilli = 0;
					timeSecs = 0;
					timeMicro = 0;
					timeMoreSecs = 0;
					timerLabel.setText("0" + timeSecs + ":" + "0" + timeMilli + "." + "0" + timeMicro);
					if(difficulty == -1){
						difficulty = 1;
					}
					setDimensions();
					game = new Minesweeper(numRows, numCols, bombIndex);
					rectangles = new Rectangle2D.Double[numRows][numCols];
					initRectangles();
					//		game.fillWithBombs();
					//		game.placeNumbers();
					isFirstClick = true;
					initImages();
					removeListener();
					repaint();
					init(f);
				}

			};
		
		System.out.println("ng" + newGame != null);
		if(newGame != null)
			newGame.setText("New Game");
		newGame.addActionListener(ng);
		motion = new MotionListener();
		menuListener = new MenuItemListener();
//		newGame.addActionListener(ng);
		this.addMouseMotionListener(motion);
		timerListener = new TimerListener();
		timer = new Timer(100, timerListener);
		int fSize = 20;
		timerLabel.setFont(new Font("Times New Roman", Font.BOLD, fSize));
		
		timerLabel.setBackground(Color.white);
		timerLabel.setForeground(new Color(0,0,255));
		
		flagsLabel.setFont(new Font("Times New Roman", 0, fSize));
		labelPanel.add(flagsLabel);
		
		spacer.setFont(new Font("Times New Roman", 0, fSize));
		if (buttonAdded == false) {
			ng = new NewGameListener();
			newGame = new JButton(action);
			
			buttonPanel.add(newGame);
			buttonAdded = true;
		}
		labelPanel.add(spacer);
		labelPanel.add(timerLabel);

		if(!menusAdded)
			makeMenus();
		f.setSize(height,width);
		beginner.addActionListener(menuListener);
		inter.addActionListener(menuListener);
		exprt.addActionListener(menuListener);
		closeMenuItem.addActionListener(menuListener);
		heat.addActionListener(menuListener);
		celtics.addActionListener(menuListener);
		knicks.addActionListener(menuListener);
		patriots.addActionListener(menuListener);
		bobcats.addActionListener(menuListener);

		f.setJMenuBar(mb);
		cp.add(labelPanel, BorderLayout.NORTH);
		
		cp.add(this);
		cp.add(buttonPanel, BorderLayout.SOUTH);
		
		
		repaint();

		
	}
	private boolean change = false;
	public void initScreen(){
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = env.getDefaultScreenDevice();
	}
	public void setFullScreen(DisplayMode dm, JFrame window){
		window.setUndecorated(true);
		window.setResizable(false);
		vc.setFullScreenWindow(window);

		if(dm != null && vc.isDisplayChangeSupported()){
			try{
				vc.setDisplayMode(dm);
			}catch(Exception e){}
		}
	}
	public Window getFullScreenWindow(){
		return vc.getFullScreenWindow();
	}
	public void restoreScreen(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			w.dispose();
		}
		vc.setFullScreenWindow(null);
	}
	public void start(){
		if(f == null)
			f = new JFrame("Minesweeper");
		init(f);
		if (!change) {
			Point screen = f.getLocation();
			f.setLocation(screen.x - width / 2, screen.y - height / 2);
			System.out.printf("%5s%5d%5d%5d%5d\n", screen, f.getLocation().x,
					f.getLocation().y, f.getWidth(), f.getHeight());
			change = true;
		}
		//f.setUndecorated(true);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void removeSpacer(){
		labelPanel.remove(spacer);
	}
	public void run(DisplayMode dm){
		try {
			setFullScreen(dm,f);
		} catch(Exception e){
			setFullScreen(dm,f);
		}
	}
	public void removeMouseListener(){
		if(click != null)
			removeMouseListener(click);
	}
	public void removeListener(){
		if(click != null)
			removeMouseListener(click);
		if(timerListener != null)
			timer.removeActionListener(timerListener);
		if(ng != null)
			newGame.removeActionListener(ng);
		if(motion != null)
			removeMouseMotionListener(motion);
		if(menuListener != null){
			beginner.removeActionListener(menuListener);
			inter.removeActionListener(menuListener);
			exprt.removeActionListener(menuListener);
			closeMenuItem.removeActionListener(menuListener);
		}
		if(action != null){
			newGame.removeActionListener(action);
		}
	}
	public static void main(String[] args){
		try {
            // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
        		"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }
		DisplayComponent c = new DisplayComponent();
		c.excecute();
	}

}
