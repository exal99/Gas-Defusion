import processing.core.PApplet;

public class GasContainer {
	
	private PApplet parrent;
	private Gas[] gases;
	
	public GasContainer(PApplet parrent, Gas ... g) {
		gases = g;
		this.parrent = parrent;
	}
	
	public void update() {
		for (Gas g : gases) {
			g.update();
		}
	}
	
	public void draw() {
		for (Gas g : gases) {
			g.draw();
		}
	}
}
