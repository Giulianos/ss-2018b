package ss_tp1;

import java.util.HashSet;
import java.util.Set;

public class Particle {
	static private long nextId = 0;
	static private double l;
	private long id;
	private double x;
	private double y;
	private double r;
	private double tita;
	private double newTita;
	private double v_x;
	private double v_y;

	private Set<Particle> neighbours;
	private Set<Particle> ghostNeighbours;
	public Particle(double x, double y, double r, double v_x, double v_y) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.id = nextId++;
		this.neighbours = new HashSet<>();
		this.ghostNeighbours = new HashSet<>();
		this.v_x = v_x;
		this.v_y = v_y;
        this.tita = Math.atan(v_y/v_x);
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

    public double getV_x() {
        return v_x;
    }

    public double getV_y() {
        return v_y;
    }

    public double getTita() {
        return tita;
    }

    public void setNewTita(double newTita) {
        this.newTita = newTita;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setTita(double tita) {
        this.tita = tita;
    }

    public void setV_x(double v_x) {
        this.v_x = v_x;
    }

    public void setV_y(double v_y) {
        this.v_y = v_y;
    }

    public Set<Particle> getNeighbours() {
		return neighbours;
	}
	public Set<Particle> getGhostNeighbours() {
		return ghostNeighbours;
	}
	public Cell getCell(int m, double l) {
		return new Cell((int)Math.floor(y*m/l), (int)Math.floor(x*m/l));
	}
	public double distanceTo(Particle particle) {
		return Math.sqrt((x-particle.x)*(x-particle.x)+(y-particle.y)*(y-particle.y)) - r - particle.r;
	}
	public long getId() {
		return id;
	}
	public static void setL(double l) {
		Particle.l = l;
	}

	public void updateVelocity() {
	    this.tita = newTita;
	    this.v_x = Math.cos(tita);
        this.v_y = Math.sin(tita);
    }

	public double ghostDistanceTo(Particle particle) {
//		System.out.println("Calculating distance between " + id + " and " + particle.id);
		double distX = Math.abs(x-particle.x);
		double distY = Math.abs(y-particle.y);
		distX = (distX < l - distX) ? distX : l-distX;
		distY = (distY < l - distY) ? distY : l-distY;
		return Math.sqrt(distX*distX+distY*distY) - r - particle.r;
	}
	@Override
	public String toString() {
		return Long.toString(id);
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
