import processing.core.PApplet;
import processing.core.PVector;

public class Gas {
	
	private GasParticle[] particles;
	private PApplet parrent;
	
	public Gas(float mean, float std, int numParticles, PApplet parrent) {
		particles = new GasParticle[numParticles];
		this.parrent = parrent;
		for (int i = 0; i < numParticles; i++) {
			PVector vel = PVector.random2D();
			float speed = (parrent.randomGaussian() * std) + mean;
			vel.setMag(speed);
			System.out.println(speed);
			particles[i] = new GasParticle(vel, new PVector(parrent.width/2, parrent.height/2), parrent);
		}
	}
	
	public void draw() {
		for (GasParticle p : particles) {
			p.draw();
		}
	}
	
	public void update() {
		for (GasParticle p : particles) {
			p.update();
		}
	}

}
