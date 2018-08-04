package ss_tp1;

public class Particle {
	private double x;
	private double y;
	private double r;
	public Particle(double x, double y, double r) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getR() {
		return r;
	}
	public Cell getCell(double m) {
		return new Cell((int)Math.floor(y/m), (int)Math.floor(x/m));
	}
	public double distanceTo(Particle particle) {
		return Math.sqrt((x-particle.x)*(x-particle.x)+(y-particle.y)*(y-particle.y)) - r - particle.r;
	}
}
