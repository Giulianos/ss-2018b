package ss_tp1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Particle {
	static private long nextId = 0;
	private long id;
	private double x;
	private double y;
	private double r;
	private Set<Particle> neighbours;
	public Particle(double x, double y, double r) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.id = nextId++;
		this.neighbours = new HashSet<>();
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
	public Set<Particle> getNeighbours() {
		return neighbours;
	}
	public Cell getCell(int m, double l) {
		return new Cell((int)Math.floor(y*m/l), (int)Math.floor(x*m/l));
	}
	public double distanceTo(Particle particle) {
		return Math.sqrt((x-particle.x)*(x-particle.x)+(y-particle.y)*(y-particle.y)) - r - particle.r;
	}
	@Override
	public String toString() {
		return "Particle (" + x + ", " + y + ", " + r + ")";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Particle other = (Particle) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
