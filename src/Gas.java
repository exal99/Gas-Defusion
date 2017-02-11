import processing.core.PApplet;
import processing.core.PVector;

public class Gas {
	
	private GasParticle[] particles;
	private PApplet parrent;
	private int toAdd;
	private int ind;
	private PVector pos;
	private float particleMass;
	private float particleSize;
	private int color;
	
	public Gas(float particleMass, float particleSize, int numParticles, int color, PVector start, PApplet parrent) {
		particles = new GasParticle[numParticles];
		pos = start.copy();
		this.parrent = parrent;
		toAdd = numParticles;
		ind = 0;
		
		this.particleMass= particleMass;
		this.particleSize = particleSize;
		this.color = color;
		addParticle();
	}
	
	private void addParticle() {
		if (ind < particles.length) {
			for (int toTry = 0; toTry < toAdd/10; toTry++) {
				PVector vel = PVector.random2D();
				GasParticle p = new GasParticle(particleMass, particleSize, vel, new PVector(parrent.random(pos.x-50, pos.x+50), parrent.random(pos.y-50, pos.y + 50)), color, parrent);
				for (int i = ind - 1; i >= 0; i--) {
					if (p.isInside(particles[i])) {
						p = null;
						break;
					}
				}
				if (p != null) {
					particles[ind] = p;
					ind++;
				}
				if (ind >= particles.length) {
					break;
				}
			}
			
			//ind++;
		}
	}
	
	public void draw() {
		for (GasParticle p : particles) {
			if (p != null) {
				p.draw();
			}
		}
	}
	
	public void update() {
		if (ind < particles.length) {
			addParticle();
		}
		for (GasParticle p : particles) {
			if (p != null) {
				p.update();
			}
			
		}
	}
	
	public GasParticle[] getParticles() {
		return particles;
	}

}
