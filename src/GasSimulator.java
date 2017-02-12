import processing.core.PApplet;
import processing.core.PVector;

public class GasSimulator extends PApplet{
	
	public static final int GAS_CONTAINER_WIDTH = 800;
	public static final int GAS_CONTAINER_HEIGHT = 600;
	
	private GasContainer container;
	
	@Override
	public void settings() {
		size(1000,800);
		
	}
	
	@Override
	public void setup() {
		frame.setResizable(true);
		Gas gasA = new Gas(2, 2.4f, 100, color(0,50,255), new PVector(550, height/2), this);
		Gas gasB = new Gas(32, 3, 100, color(255, 0, 0), new PVector(250, height/2), this);
		container = new GasContainer(this, gasA, gasB);
		System.out.println((new PVector(-1, -1).heading()));
		System.out.println(-(HALF_PI/2 + HALF_PI));
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
