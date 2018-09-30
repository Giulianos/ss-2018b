package ar.edu.itba.ss.Containers;

import ar.edu.itba.ss.Particles.Body;

public abstract class Container {
    private double friction;
    private double od;

    public Container(double friction, double od) {
        this.friction = friction;
        this.od = od;
    }

    public double getFriction() {
        return friction;
    }

    public abstract double getArea();

    public abstract double getDiameterAt(double depth);


    public double getOpeningDiameter() {
        return od;
    }

    public abstract double getTotalLength();

    public abstract boolean touchesWall(Body body);

}
