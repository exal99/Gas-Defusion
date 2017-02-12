import java.util.HashSet;

import processing.core.PApplet;
import processing.core.PVector;

public class GasContainer {
	
	private PApplet parrent;
	private Gas[] gases;
	private HashSet<GasParticle>[][] sections;
	private int sectionsWidth;
	private int sectionsHeight;
	private Membrane[] membranes;
	
	public GasContainer(PApplet parrent, Gas ... g) {
		sectionsWidth = 10;
		sectionsHeight = 10;
		
		gases = g;
		this.parrent = parrent;
		createSections();
		membranes = new Membrane[5];
		
		membranes[0] = new WallMembrane(0, new PVector(0, 0), GasSimulator.GAS_CONTAINER_WIDTH, parrent);
		membranes[1] = new WallMembrane(-PApplet.HALF_PI, new PVector(0,0), GasSimulator.GAS_CONTAINER_HEIGHT, parrent);
		membranes[2] = new WallMembrane(-PApplet.HALF_PI, new PVector(GasSimulator.GAS_CONTAINER_WIDTH, 0), GasSimulator.GAS_CONTAINER_HEIGHT, parrent);
		membranes[3] = new WallMembrane(0, new PVector(0, GasSimulator.GAS_CONTAINER_HEIGHT), GasSimulator.GAS_CONTAINER_WIDTH, parrent);
		membranes[4] = new PartialWallMembrane(-PApplet.HALF_PI, new PVector(GasSimulator.GAS_CONTAINER_WIDTH/2, 0), GasSimulator.GAS_CONTAINER_HEIGHT, parrent);
		
	}
	
	@SuppressWarnings("unchecked")
	private void createSections() {
		sections = new HashSet[sectionsWidth][sectionsHeight];
		for (int i = 0; i < sections.length; i++) {
			for (int j = 0; j < sections[0].length; j++) {
				sections[i][j] = new HashSet<GasParticle>();
			}
		}
		for (Gas gas : gases) {
			for (GasParticle p : gas.getParticles()) {
				//p.checkBoundaryCollision();
				if (p != null) {
					int row = (int) p.getPos().x / (GasSimulator.GAS_CONTAINER_WIDTH / sectionsWidth);
					int col = (int) p.getPos().y / (GasSimulator.GAS_CONTAINER_HEIGHT / sectionsHeight);
	//				System.out.println(row + " " + col);
	//				System.out.println(p.getPos().x + " " + p.getPos().y);
	//				System.out.println(p.getVel());
					sections[row][col].add(p);
				}
			}
		}
	}
	
	private void membraneBounce(Membrane m, GasParticle p) {
		float heading = p.getVel().copy().mult(-1).heading();
		float entryAngle = m.getAngle() - heading;
		float exitAngle = m.getAngle() - PApplet.PI + entryAngle;
		PVector newVel = PVector.fromAngle(exitAngle);
		newVel.setMag(p.getVel().mag());
		p.getVel().set(newVel);
		
	}
	
	public void update() {
		for (HashSet<GasParticle>[] row : sections) {
			for (HashSet<GasParticle> particles : row) {
				for (GasParticle p : particles) {
					for (GasParticle otherP : particles) {
						if (otherP != p) {
							p.checkCollision(otherP);
						}
					}
					for (Membrane m : membranes) {
						if (m.willBounse(p)) {
							membraneBounce(m, p);
						} while (m.isInside(p)) {
							p.update();
						}
					}
				}
			}
		}
		for (Gas g : gases) {
			g.update();
		}
		
		createSections();
	
	}
	
	public void draw() {
		for (Gas g : gases) {
			g.draw();
		}
		
		for (Membrane m : membranes) {
			m.draw();
		}
	}
	
	private class WallMembrane extends PartialWallMembrane {

		public WallMembrane(float angle, PVector pos, float length, PApplet parrent) {
			super(angle, pos, length, parrent);
		}
		
		
		@Override
		public boolean willBounse(GasParticle p) {
			return isInside(p);
		}
	}
}
