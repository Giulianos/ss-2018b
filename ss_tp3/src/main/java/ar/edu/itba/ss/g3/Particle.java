package ar.edu.itba.ss.g3;

import java.util.Objects;

public class Particle {

    private static Integer nextID = 0;

    private double x;
    private double y;
    private double vx;
    private double vy;

    private double m;
    private double r;

    private Integer id;

    public Particle(double x, double y, double vx, double vy, double m, double r) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.m = m;
        this.r = r;
        this.id = nextID++;
    }

    public double getX() { return x; }

    public double getY() {
        return y;
    }

    public double getVx() { return vx; }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getM() {
        return m;
    }

    public double getR() { return r; }

    public Integer getId() {
        return id;
    }

    public double distanceTo(Particle p) {
        return Math.sqrt(Math.pow(x-p.x, 2) + Math.pow(y-p.y, 2));
    }

    @Override
    public String toString() {
        String color = ((r>0.005) ? "1" : "0");
        return "" + x + "\t" + y + "\t" + r + "\t" + color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return distanceTo(particle) < 0.0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void updatePosition(double deltaT) {
        x += vx * deltaT;
        y += vy * deltaT;
    }

    public void incrementVelocity(double incX, double incY) {
        vx *= incX;
        vy += incY;
    }
}
