package ar.edu.itba.ss.g3;

import java.util.Objects;

public class Particle {

    private Double x;
    private Double y;
    private Double vx;
    private Double vy;

    private Double m;
    private Double r;

    public Particle(Double x, Double y, Double vx, Double vy, Double m, Double r) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.m = m;
        this.r = r;
    }

    public Double getX() { return x; }

    public Double getY() {
        return y;
    }

    public Double getVx() { return vx; }

    public void setVx(Double vx) {
        this.vx = vx;
    }

    public Double getVy() {
        return vy;
    }

    public void setVy(Double vy) {
        this.vy = vy;
    }

    public Double getM() {
        return m;
    }

    public Double getR() { return r; }

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
        return Objects.equals(getX(), particle.getX()) &&
                Objects.equals(getY(), particle.getY()) &&
                Objects.equals(getVx(), particle.getVx()) &&
                Objects.equals(getVy(), particle.getVy()) &&
                Objects.equals(getM(), particle.getM()) &&
                Objects.equals(getR(), particle.getR());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void updatePosition(Double deltaT) {
        x += vx * deltaT;
        y += vy * deltaT;
    }
}
