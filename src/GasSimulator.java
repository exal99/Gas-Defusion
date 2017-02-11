import processing.core.PApplet;
import processing.core.PVector;

public class GasSimulator extends PApplet{
	
	private GasContainer container;
	
	@Override
	public void settings() {
		size(800,600);
	}
	
	@Override
	public void setup() {
		Gas gasA = new Gas(2, 2.4f, 2000, color(0,50,255), new PVector(550, height/2), this);
		Gas gasB = new Gas(32, 3, 800, color(255, 0, 0), new PVector(250, height/2), this);
		container = new GasContainer(this, gasA, gasB);
	}
	
	@Override
	public void draw() {
		background(100);
		container.draw();
		container.update();
		textSize(32);
		fill(255);
		text(frameRate, 0, 32);
	}
	
	public static void main(String[] args) {
		PApplet.main("GasSimulator");
	}
}
