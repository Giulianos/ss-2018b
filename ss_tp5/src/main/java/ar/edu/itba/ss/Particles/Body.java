package ar.edu.itba.ss.Particles;

import java.util.Objects;

public class Body {
    private Vector position;
    private Vector velocity;
    private double mass;
    private double radius;

    public Body(Vector position, Vector velocity, double mass, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.radius = radius;
    }

    public Body(Vector position, Vector velocity, double mass, String s) {
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setPosition(Vector position) { this.position = position; }

    public void setVelocity(Vector velocity) { this.velocity = velocity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return Vector.distanceBetween(this.position, body.position) > this.radius+ body.radius;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPosition(), getVelocity(), getMass(), getRadius());
    }

    public String getTag() {
        return "";
    }
}
