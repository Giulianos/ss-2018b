package ar.edu.itba.ss.Particles;

import java.util.Objects;
import java.util.Set;

public class Body {
    private Vector position;
    private Vector velocity;
    private double mass;
    private double radius;
    private static int quantity = 0;
    private int id;

    public Body(Vector position, Vector velocity, double mass, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.radius = radius;
        this.id = quantity;
        quantity++;
    }


    public static boolean bodyInTouch(Set<Body> bodies, Body body) {
        for(Body b: bodies){
            if(!body.equals(b) && Body.bodiesInTouch(b,body)){
                return true;
            }
        }
        return false;
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
    public int hashCode() {

        return Objects.hash(getPosition(), getVelocity(), getMass(), getRadius());
    }

    public String getTag() {
        return "";
    }

    public static boolean bodiesInTouch(Body b1, Body b2){
        if(b1 == b2){
            return false;
        }
        double distance = Vector.distanceBetween(b1.position, b2.position);
        double minDistance = b1.radius + b2.radius;
        return distance < minDistance;
    }

    public String toString(){
        return this.getPosition().x +"\t"+this.getPosition().y+"\n";
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
