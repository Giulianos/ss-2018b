package ss_tp1;

import java.util.HashSet;
import java.util.Set;

public class Particle {
	static private long nextId = 0;
	static private double l;
	static private boolean punctual = false;
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
        this.tita = Math.atan2(v_y,v_x);
	}

	public static void setL(double l) {
		Particle.l = l;
	}

	public double getX() {
		return x;
	}

    public static void setPunctual(boolean punctual) {
        Particle.punctual = punctual;
    }

    public void setX(double x) {
	    while(x < 0) {
	        x += l;
        }
        while(x > l) {
	        x-=l;
        }
        this.x = x;
	    if(Double.isNaN(this.x)) {
	        throw new IllegalStateException();
        }
    }

	public double getY() {
		return y;
	}

    public void setY(double y) {
	    while(y < 0) {
            y += l;
        }
        while(y > l) {
	        y-=l;
        }
        this.y = y;
        if(Double.isNaN(this.y)) {
            throw new IllegalStateException();
        }
    }

	public double getR() {
		return r;
	}

    public void setR(double r) {
        this.r = r;
    }

    public double getV_x() {
        return v_x;
    }

    public void setV_x(double v_x) {
        this.v_x = v_x;
    }

    public double getV_y() {
        return v_y;
    }

    public void setV_y(double v_y) {
        this.v_y = v_y;
    }

    public double getTita() {
        return tita;
    }

    public void setTita(double tita) {
        this.tita = tita;
    }

    public void setNewTita(double newTita) {
        this.newTita = newTita;
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

	public void removeCurrentNeighbours() {
        this.neighbours = new HashSet<>();
        this.ghostNeighbours = new HashSet<>();
    }

	public double distanceTo(Particle particle) {
	    if(!punctual)
		    return Math.sqrt((x-particle.x)*(x-particle.x)+(y-particle.y)*(y-particle.y)) - r - particle.r;
        return Math.sqrt((x-particle.x)*(x-particle.x)+(y-particle.y)*(y-particle.y));
	}

	public long getId() {
		return id;
	}

	public void updateVelocity() {
	    this.tita = newTita;
	    this.v_x = 0.03*Math.cos(tita);
        this.v_y = 0.03*Math.sin(tita);
    }

	public double ghostDistanceTo(Particle particle) {
//		System.out.println("Calculating distance between " + id + " and " + particle.id);
		double distX = Math.abs(x-particle.x);
		double distY = Math.abs(y-particle.y);
		distX = (distX < l - distX) ? distX : l-distX;
		distY = (distY < l - distY) ? distY : l-distY;
		if(!punctual)
		    return Math.sqrt(distX*distX+distY*distY) - r - particle.r;
		return Math.sqrt(distX*distX+distY*distY);
	}

	private String getColor() {
        /*double red   = 128 + 127 * Math.sin(tita);
        double green = 128 + 127 * Math.sin(tita + 2*Math.PI / 3.); // + 60°
        double blue  = 128 + 127 * Math.sin(tita + 4*Math.PI / 3.);

        return Integer.toString((int)red) + " " + Integer.toString((int)green) + " " + Integer.toString((int)blue);
        */

        return Double.toString(tita<0 ? tita+Math.PI*2 : tita);
    }

	@Override
	public String toString() {
		return x + " " + y + " " + v_x + " " + v_y + " " + getColor();
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
