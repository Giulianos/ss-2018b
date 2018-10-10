package ar.edu.itba.ss.Particles;

import ar.edu.itba.ss.Types.Vector;

import java.util.Objects;
import java.util.Set;

public class Body implements Cloneable {
    private Vector position;
    private Vector velocity;
    private Double mass;
    private Double radius;
    private static Integer quantity = 0;
    private Integer id;

    public Body(Vector position, Vector velocity, Double mass, Double radius) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.radius = radius;
        this.id = quantity;
        quantity++;
    }

    public Body(Double x, Double y, Double vx, Double vy, Double mass, Double radius) {
        this.position =  new Vector(x, y);
        this.velocity = new Vector(vx, vy);
        this.mass = mass;
        this.radius = radius;
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

    public Body clone() {
        Body ret = new Body(position, velocity, mass, radius);
        ret.id = id;
        return ret;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPosition(), getVelocity(), getMass(), getRadius());
    }

    public String getTag() {
        return "";
    }

    public boolean touches(Body b){
        if(b == this){
            return false;
        }
        Double distance = position.distanceTo(b.position);
        double minDistance = radius + b.radius;
        return distance < minDistance;
    }

    public String toString(){
        return this.getPosition().x +"\t"+this.getPosition().y + "\t" + this.getRadius();
    }

    public Boolean isFixed() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return Double.compare(body.getMass(), getMass()) == 0 &&
                Double.compare(body.getRadius(), getRadius()) == 0 &&
                id == body.id &&
                Objects.equals(getPosition(), body.getPosition()) &&
                Objects.equals(getVelocity(), body.getVelocity());
    }
}
