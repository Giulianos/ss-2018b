package com.company;

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

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getVx() {
        return vx;
    }

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

    public void setM(Double m) {
        this.m = m;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        // TODO: two particles are the same if their distance is 0.
        Double difX = this.x - particle.getX();
        Double difY = this.y - particle.getY();
        Double sumR = this.r + particle.getR();

        return (difX*difX + difY*difY) <= (sumR*sumR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void updatePosition(Double deltaT) {
        x = x + vx * deltaT;
        y = y + vy * deltaT;
    }
}
