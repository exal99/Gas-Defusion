import java.util.Random;

import processing.core.PApplet;
import processing.core.PVector;

public class PartialWallMembrane implements Membrane {
	
	private float angle;
	private PApplet parrent;
	private PVector startPos;
	private PVector endPos;
	private float thickness;
	
	public PartialWallMembrane(float angle, PVector pos, float length, PApplet parrent) {
		this.angle = angle;
		this.parrent = parrent;
		this.startPos = pos;
		this.endPos = new PVector(startPos.x+PApplet.cos(angle) * length, startPos.y-PApplet.sin(angle) * length);
		thickness = 10;
	}

	@Override
	public float getAngle() {
		return angle;
	}
	
	@Override
	public float getThickness() {
		return thickness;
	}
	
	private float lineDistance(PVector lineStart, PVector lineEnd, PVector point) {
		  PVector projection, temp;
		  
		  temp = PVector.sub(lineEnd, lineStart);

		  float lineLength = temp.x * temp.x + temp.y * temp.y; //lineStart.dist(lineEnd);

		  if (lineLength == 0F) {
		    return point.dist(lineStart);
		  }

		  float t = PVector.dot(PVector.sub(point, lineStart), temp) / lineLength;

		  if (t < 0F) {
		    return point.dist(lineStart);
		  }

		  if (t > lineLength) {
		    return point.dist(lineEnd);
		  }

		  projection = PVector.add(lineStart, PVector.mult(temp, t));

		  return point.dist(projection);
		}

	@Override
	public boolean willBounse(GasParticle p) {
		if (isInside(p)) {
			Random rand = new Random();
			float prob = 1f;
			if (rand.nextFloat() < prob) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isInside(GasParticle p) {
		if (lineDistance(startPos, endPos, p.getPos()) < p.getSize() + thickness/2) {
			return true;
		}
		return false;
	}
	


	@Override
	public void draw() {
		parrent.stroke(255);
		parrent.strokeWeight(thickness);
		parrent.line(startPos.x, startPos.y, endPos.x, endPos.y);
	}

}
