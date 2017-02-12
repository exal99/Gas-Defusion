import processing.core.PApplet;
import processing.core.PVector;

public class GasParticle {
	
	private PVector vel;
	private PApplet parrent;
	private PVector pos;
	private float r;
	private float mass;
	private int color;
	
	public GasParticle(float mass, float size, PVector direction, PVector startPos, int color, PApplet parrent) {
		this.mass = mass;
		float std = 1;
		float meanSpeed = (float) Math.sqrt(2/mass * 0.1f);
		vel = direction.copy();
		vel.mult(parrent.randomGaussian() * std + meanSpeed);
		this.parrent = parrent;
		pos = startPos;
		r = size;
		this.color = color;
	}
	
	public void draw() {
		parrent.noStroke();
		parrent.fill(color);
		//parrent.strokeWeight(mass);
		//parrent.point(pos.x, pos.y);
		parrent.ellipse(pos.x, pos.y, r*2, r*2);
	}
	
	public PVector getVel() {
		return vel;
	}
	
	public void update() {
		pos.add(vel);
		checkBoundaryCollision();
	}
	
	public PVector getPos() {
		return pos;
	}
	
	public float getSize() {
		return r;
	}
	
	public void checkBoundaryCollision() {
	    if (pos.x > GasSimulator.GAS_CONTAINER_WIDTH-r) {
	      pos.x = GasSimulator.GAS_CONTAINER_WIDTH-r;
	      vel.x *= -1;
	    } 
	    else if (pos.x < r) {
	      pos.x = r;
	      vel.x *= -1;
	    } 
	    if (pos.y > GasSimulator.GAS_CONTAINER_HEIGHT-r) {
	      pos.y = GasSimulator.GAS_CONTAINER_HEIGHT-r;
	      vel.y *= -1;
	    } 
	    else if (pos.y < r) {
	      pos.y = r;
	      vel.y *= -1;
	    }
	}
	
	
	public void checkCollision(GasParticle other) {
//		if (other.pos.x == pos.x && other.pos.y == pos.y) {
//			System.out.println("inside");
//			other.pos.add(PVector.mult(PVector.fromAngle(other.vel.heading()), r * 3));
//		}
		
	    // get distances between the balls components
	    PVector bVect = PVector.sub(other.pos, pos);

	    // calculate magnitude of the vector separating the balls
	    float bVectMag = bVect.mag();

	    if (bVectMag < r + other.r) {
	      // get angle of bVect
	      float theta  = bVect.heading();
	      // precalculate trig values
	      float sine = PApplet.sin(theta);
	      float cosine = PApplet.cos(theta);

	      /* bTemp will hold rotated ball positions. You 
	       just need to worry about bTemp[1] position*/
	      PVector[] bTemp = {
	        new PVector(), new PVector()
	        };

	        /* this ball's position is relative to the other
	         so you can use the vector between them (bVect) as the 
	         reference point in the rotation expressions.
	         bTemp[0].position.x and bTemp[0].position.y will initialize
	         automatically to 0.0, which is what you want
	         since b[1] will rotate around b[0] */
	        bTemp[1].x  = cosine * bVect.x + sine * bVect.y;
	      bTemp[1].y  = cosine * bVect.y - sine * bVect.x;

	      // rotate Temporary velocities
	      PVector[] vTemp = {
	        new PVector(), new PVector()
	        };

	      vTemp[0].x  = cosine * vel.x + sine * vel.y;
	      vTemp[0].y  = cosine * vel.y - sine * vel.x;
	      vTemp[1].x  = cosine * other.vel.x + sine * other.vel.y;
	      vTemp[1].y  = cosine * other.vel.y - sine * other.vel.x;

	      /* Now that velocities are rotated, you can use 1D
	       conservation of momentum equations to calculate 
	       the final velocity along the x-axis. */
	      PVector[] vFinal = {  
	        new PVector(), new PVector()
	        };

	      // final rotated velocity for b[0]
	      vFinal[0].x = ((mass - other.mass) * vTemp[0].x + 2 * other.mass * vTemp[1].x) / (mass + other.mass);
	      vFinal[0].y = vTemp[0].y;

	      // final rotated velocity for b[0]
	      vFinal[1].x = ((other.mass - mass) * vTemp[1].x + 2 * mass * vTemp[0].x) / (mass + other.mass);
	      vFinal[1].y = vTemp[1].y;

	      // hack to avoid clumping
	      bTemp[0].x += vFinal[0].x;
	      bTemp[1].x += vFinal[1].x;

	      /* Rotate ball positions and velocities back
	       Reverse signs in trig expressions to rotate 
	       in the opposite direction */
	      // rotate balls
	      PVector[] bFinal = { 
	        new PVector(), new PVector()
	        };

	      bFinal[0].x = cosine * bTemp[0].x - sine * bTemp[0].y;
	      bFinal[0].y = cosine * bTemp[0].y + sine * bTemp[0].x;
	      bFinal[1].x = cosine * bTemp[1].x - sine * bTemp[1].y;
	      bFinal[1].y = cosine * bTemp[1].y + sine * bTemp[1].x;

	      // update balls to screen position
	      other.pos.x = pos.x + bFinal[1].x;
	      other.pos.y = pos.y + bFinal[1].y;
	      //System.out.println("before " + pos);
	      pos.add(bFinal[0]);
	      //System.out.println("after " + pos);

	      // update velocities
	      vel.x = cosine * vFinal[0].x - sine * vFinal[0].y;
	      vel.y = cosine * vFinal[0].y + sine * vFinal[0].x;
	      other.vel.x = cosine * vFinal[1].x - sine * vFinal[1].y;
	      other.vel.y = cosine * vFinal[1].y + sine * vFinal[1].x;
	      
//	      if (PApplet.dist(pos.x, pos.y, other.pos.x, other.pos.y) < r + other.r) {
//	    	  PVector temp = other.vel.copy();
//	    	  temp.setMag((other.r + r) * 2);
//	    	  other.pos.add(temp);
//	    	  //System.out.println("INSIDE " + parrent.frameCount + " " + this);
//	      }
	    }
	  }
	
	public boolean isInside(GasParticle other) {
		return PApplet.dist(pos.x, pos.y, other.pos.x, other.pos.y) < r + other.r;
	}
}
