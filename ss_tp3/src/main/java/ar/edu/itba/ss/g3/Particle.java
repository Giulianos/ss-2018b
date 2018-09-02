package ar.edu.itba.ss.g3;

public class Particle {
    private static int nextID = 0;

    private final int id;
    private final double r;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private final double m;

    public Particle(double x, double y, double vx, double vy, double r, double m) {
        this.id = nextID++;
        this.r = r;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.m = m;
    }

    public double getX() {

        return x;
    }

    public double getY() {
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getM() {
        return m;
    }

    public double getR() {
        return this.r;
    }

    public int getId() {
        return this.id;
    }

    public void updatePosition(double deltaT) {
        this.x += this.vx * deltaT;
        this.y += this.vy * deltaT;
    }

    public void swapDirectionX() {
        this.vx *= (-1);

    }

    public void swapDirectionY() {
        this.vy *= (-1);

    }

    public void increaseVx(double inc) {
        this.vx += inc;
    }

    public void increaseVy(double inc) {
        this.vy += inc;
    }

    public double getAbsoluteSpeed() {
        return Math.sqrt(Math.pow(this.vx, 2) + Math.pow(this.vy, 2));
    }

    @Override
    public String toString() {
        String type = r>0.05 ? "1" : "0";
        return ""+x+" "+y+" "+r+" "+type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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