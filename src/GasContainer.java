import java.util.HashSet;

import processing.core.PApplet;

public class GasContainer {
	
	private PApplet parrent;
	private Gas[] gases;
	private HashSet<GasParticle>[][] sections;
	private int sectionsWidth;
	private int sectionsHeight;
	
	public GasContainer(PApplet parrent, Gas ... g) {
		sectionsWidth = 10;
		sectionsHeight = 10;
		
		gases = g;
		this.parrent = parrent;
		createSections();
		
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
					int row = (int) p.getPos().x / (parrent.width / sectionsWidth);
					int col = (int) p.getPos().y / (parrent.height / sectionsHeight);
	//				System.out.println(row + " " + col);
	//				System.out.println(p.getPos().x + " " + p.getPos().y);
	//				System.out.println(p.getVel());
					sections[row][col].add(p);
				}
			}
		}
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
	}
}
