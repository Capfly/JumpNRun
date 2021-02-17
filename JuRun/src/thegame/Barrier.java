package thegame;

import java.util.Random;

public class Barrier implements Runnable {

	private int height, width;
	private int xLocation = 800;
	private Spieler figureReference;
	Game windowReference;
	
	Thread runThread;
	
	Barrier(int height, int width, Spieler figureReference, Game windowReference) {
		this.height = height;
		this.width = width;
		this.figureReference = figureReference;
		this.windowReference = windowReference;
		
		this.runThread = new Thread(this);
		this.runThread.start();
	}
	
	public void run() {
		
		Random rZ = new Random();
		int rand = rZ.nextInt(20)+15;
		
		while(this.getXLocation() > -20) { // when out of range, we don't need to move
			if(	this.getXLocation() < figureReference.getXLocation()+figureReference.getImageIcon().getIconWidth()*2 &&
				this.getXLocation()+this.width > figureReference.getXLocation() &&
				figureReference.getYLocation()-figureReference.getImageIcon().getIconHeight()*2 < this.height) {
					System.out.println("COLLISION!"); // COLLISION
					break;
			}
			else {
				
				try {
					Thread.sleep(50);
					this.xLocation -= rand;
					windowReference.repaint();
				}
				catch(Exception e) { System.out.println("ERROR! Moving interrupted: "+e.getMessage()); }
			}
		}
		
		this.xLocation = 800;
		run();
	}
	
	public int getXLocation() {
		return this.xLocation;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
}
