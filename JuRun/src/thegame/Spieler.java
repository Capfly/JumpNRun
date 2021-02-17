package thegame;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Spieler implements Runnable {
	
	static final String IDLE = "idle.png";
	static final String MOVEA = "moveb.png";
	static final String MOVEB = "movea.png";
	static final String JUMP = "jump.png";
	
	String curImagePath;
	int xLocation,yLocation;
	ArrayList<String> animations;
	int animSpeed;
	Game windowReference;
	boolean jumping = false;

	Spieler() {
		
		this.yLocation = 0; // position from the ground
		this.xLocation = 50; // position from left
		this.curImagePath = IDLE;
	}
	
	public ImageIcon getImageIcon() {
		
		File checkFile = new File(curImagePath);
		ImageIcon image;
		
		if(checkFile.exists() && !checkFile.isDirectory()) {
			image = new ImageIcon(curImagePath);
		}
		else {
			System.out.println("Konnte Datei nicht laden: "+curImagePath);
			System.exit(0);
			image = new ImageIcon(); // undecidable problem
		}
		return image;
	}
	
	public int getYLocation() {
		return this.yLocation;
	}
	public int getXLocation() {
		return this.xLocation;
	}
	
	public void startAnimation(int speed, Game reference) {
		
		if(!(this.animations instanceof ArrayList<?>))
			this.animations = new ArrayList<String>();

		this.windowReference = reference;
		this.animations.add(MOVEA);
		this.animations.add(MOVEB);
		
		if(speed > 1000) speed = 1000; // TODO
		this.animSpeed = speed;
		
		new Thread(this).start(); // init run-Method
	}

	@Override
	public void run() { // moving animation
		
		int counter = 0;
		int animSize = this.animations.size();
		
		while(true) {
			if(this.yLocation < 0) this.yLocation = 0;
			if(!this.jumping) { // normal moving
				
				try {
					++counter;
					for(int i = 0; i <= this.animSpeed*10; i++) {
					
						if(this.jumping) break;
						Thread.sleep(10);
					}
					//Thread.sleep(this.animSpeed*100);
	
					this.curImagePath = this.animations.get((counter%animSize));
					windowReference.repaint();
					
					counter = (counter % animSize);
				}
				catch(Exception e) {
					System.out.println("ERROR: Animation stopped: "+e.getMessage());
				}
			}
			else { // while jumping
				
				this.curImagePath = JUMP;
				try {
					
					int time = 0;
					int jHeight = 4;
					while(this.getYLocation() >= 0 && this.jumping) {
						
						Thread.sleep(this.animSpeed*jHeight*12); // sleep a bit before repaint the jump
						this.yLocation = jHeight*(-1*((time-5)*(time-5))+25); // parable function
						
						if(this.yLocation == 0 && time > 1) {
							this.jumping = false;
							this.curImagePath = this.animations.get(0);
						}
						
						time++;
						windowReference.repaint();
					}
				}
				catch(Exception e) { System.out.println("ERROR. Jump interrupted: "+e.getMessage()); }
			}
		}
	}
}
