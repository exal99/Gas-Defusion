import processing.core.PApplet;
import processing.core.PVector;

public class GasParticle {
	
	private PVector vel;
	private PApplet parrent;
	private PVector pos;
	
	public GasParticle(PVector vel, PVector startPos, PApplet parrent) {
		this.vel = vel;
		this.parrent = parrent;
		pos = startPos;
	}
	
	public void draw() {
		parrent.stroke(255, 100);
		parrent.strokeWeight(2);
		parrent.point(pos.x, pos.y);
	}
	
	public void update() {
		pos.add(vel);
		checkBoundary();
	}
	
	private void checkBoundary() {
		if (pos.x > parrent.width) {
		      pos.x = parrent.width;
		      vel.x *= -1;
		    } 
		    else if (pos.x < 0) {
		      pos.x = 0;
		      vel.x *= -1;
		    } 
		    else if (pos.y > parrent.height) {
		      pos.y = parrent.height;
		      vel.y *= -1;
		    } 
		    else if (pos.y < 0) {
		      pos.y = 0;
		      vel.y *= -1;
		    }
	}
}
