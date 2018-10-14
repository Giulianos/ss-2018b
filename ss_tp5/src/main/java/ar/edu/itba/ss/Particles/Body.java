package ar.edu.itba.ss.Particles;

import ar.edu.itba.ss.CellIndex.Locatable;
import ar.edu.itba.ss.Types.Vector;

import java.util.Objects;
import java.util.Set;

public class Body implements Locatable {
    private static Integer nextID = 0;

    // Position vectors
    private Vector previousPosition;
    private Vector currentPosition;
    private Vector futurePosition;

    // Velocity vectors
    private Vector previousVelocity;
    private Vector currentVelocity;
    private Vector futureVelocity;

    // Acceleration vectors
    private Vector previousAcceleration;
    private Vector currentAcceleration;
    private Vector futureAcceleration;

    // State info
    private Double dtBetweenStates;
    private Boolean shouldResetMovement = false;

    // Properties
    private Double mass;
    private Double radius;
    private Integer id;


    // Constructors
    public Body(Vector position, Vector velocity, Double mass, Double radius) {
        this.currentPosition = position;
        this.currentVelocity = velocity;
        this.mass = mass;
        this.radius = radius;
        this.id = nextID++;
    }

    public Body(Double x, Double y, Double vx, Double vy, Double mass, Double radius) {
        this.currentPosition =  new Vector(x, y);
        this.previousPosition = this.currentPosition;
        this.currentVelocity = new Vector(vx, vy);
        this.previousVelocity = this.currentVelocity;
        this.currentAcceleration = Vector.getNullVector();
        this.previousAcceleration = this.currentAcceleration;
        this.mass = mass;
        this.radius = radius;
        this.id = nextID++;
    }


    // Getters and setters
    @Override
    public Vector getPosition() {
        return currentPosition;
    }

    public Vector getVelocity() {
        return currentVelocity;
    }

    public Vector getAcceleration() {
        return currentAcceleration;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public Vector getPreviousPosition() {
        return previousPosition;
    }

    public Vector getPreviousVelocity() {
        return previousVelocity;
    }

    public Vector getPreviousAcceleration() {
        return previousAcceleration;
    }

    public Vector getFuturePosition() {
        return futurePosition;
    }

    public Vector getFutureVelocity() {
        return futureVelocity;
    }

    public Vector getFutureAcceleration() {
        return futureAcceleration;
    }

    public Double getDtBetweenStates() {
        return dtBetweenStates;
    }

    public void setPosition(Vector position) { this.futurePosition = position; }

    public void setVelocity(Vector velocity) { this.futureVelocity = velocity; }

    public void setAcceleration(Vector acceleration) {
        this.futureAcceleration = acceleration;
    }

    public void setDtBetweenStates(Double dtBetweenStates) {
        this.dtBetweenStates = dtBetweenStates;
    }

    // Other methods
    public void update() {
        if(this.futurePosition == null || this.futureVelocity == null || this.futureAcceleration == null) {
            throw new IllegalStateException("Updating body without future state!");
        }

        // Update position
        this.previousPosition = this.currentPosition;
        this.currentPosition = this.futurePosition;
        this.futurePosition = null;

        if(shouldResetMovement) {
            // Update velocity
            this.previousVelocity = Vector.getNullVector();
            this.currentVelocity = Vector.getNullVector();
            this.futureVelocity = null;

            // Update acceleration
            this.previousAcceleration = Vector.getNullVector();
            this.currentAcceleration = Vector.getNullVector();
            this.futureAcceleration = null;

            shouldResetMovement = false;

            return;
        }

        // Update velocity
        this.previousVelocity = this.currentVelocity;
        this.currentVelocity = this.futureVelocity;
        this.futureVelocity = null;

        // Update acceleration
        this.previousAcceleration = this.currentAcceleration;
        this.currentAcceleration = this.futureAcceleration;
        this.futureAcceleration = null;
    }

    public void shouldResetMovement() {
        this.shouldResetMovement = true;
    }

    public boolean touches(Body b){
        if(b == this){
            return false;
        }
        Double distance = currentPosition.distanceTo(b.currentPosition);
        double minDistance = radius + b.radius;
        return distance < minDistance;
    }

    public String toString(){
        return this.getPosition().x +"\t"+this.getPosition().y + "\t" + this.getRadius();
    }

    public Boolean isFixed() {
        return false;
    }


    // HashCode and Equals
    @Override
    public int hashCode() {

        return Objects.hash(getPosition(), getVelocity(), getMass(), getRadius());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Body body = (Body) o;

        return id.equals(body.id);
    }
}
