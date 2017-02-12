
public interface Membrane {
	
	public float getAngle();
	public boolean willBounse(GasParticle p);
	public void draw();
	public float getThickness();
	public boolean isInside(GasParticle p);
}
