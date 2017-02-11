import processing.core.PApplet;

public class GasSimulator extends PApplet{
	
	private GasContainer container;
	
	@Override
	public void settings() {
		size(800,600);
	}
	
	@Override
	public void setup() {
		Gas gasA = new Gas(5, 1, 1000, this);
		container = new GasContainer(this, gasA);
	}
	
	@Override
	public void draw() {
		fill(51, 50);
		rect(0,0,width,height);
		container.draw();
		container.update();
	}
	
	public static void main(String[] args) {
		PApplet.main("GasSimulator");
	}
}
