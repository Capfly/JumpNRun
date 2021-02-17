package thegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends JFrame implements KeyListener, Runnable {

	public static int GROUND = 260;
	public static final int BREAKX = 8;
	public static final int BREAKY = 30;
	private int width, height;
	
	private Spieler figure = new Spieler();
	ArrayList<Barrier> barriers = new ArrayList<Barrier>();
	
	Game(int width, int height) {
		
		// Window settings INIT
		super("Mein Spiel");
		setSize(width,height);
		this.width = width;
		this.height = height;
		GROUND = height-40;

		setLocation(50,50);
		setBackground(Color.black);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addKeyListener(this);
		setVisible(true);
		
		// Generate Barriers
		new Thread(this).start();
		
		// Game INIT
		figure.startAnimation(1,this); // start Animation for player
	}
	
	public static void main(String[] args) {
		
		Game aGame = new Game(720,200);
	}
	
	public void paint(Graphics g) {
		
		// set Gaming Area
		g.setColor(Color.white);
		g.fillRect(BREAKX,BREAKY,width-BREAKX*2,height-BREAKY*2);
		
		// Draw Player
		g.drawImage(toBufferedImage(figure.getImageIcon().getImage()), figure.getXLocation(), (GROUND-figure.getImageIcon().getIconHeight())-figure.getYLocation(), figure.getImageIcon().getIconWidth()*2, figure.getImageIcon().getIconHeight()*2, this);
		
		// Draw barriers
		BufferedImage bf = new BufferedImage(20,20, BufferedImage.TYPE_INT_RGB);
		g.drawImage(bf,barriers.get(0).getXLocation(), GROUND-barriers.get(0).getHeight()/2, barriers.get(0).getWidth(), barriers.get(0).getHeight(), this);
	}
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch(e.getKeyCode()) {

        default: System.out.println("Fehler #"+e.getKeyCode()+"# Die Taste ist nicht vergeben!"); break;
        case 27: System.exit(0); break;
        case 32:
        	figure.jumping = true;
        break;
		}
	}

	@Override
	public void run() {
		barriers.add(new Barrier(20,20,this.figure,this));

	}
	
	public static BufferedImage toBufferedImage(Image image) {
	    if (image instanceof BufferedImage)
	        return (BufferedImage) image;
	    
	    BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB); // transparency OK

	    Graphics2D big = bimage.createGraphics();
	    big.drawImage(image, 0, 0, null);
	    big.dispose();

	    return bimage;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
