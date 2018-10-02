package ar.edu.itba.ss.Containers;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

public abstract class Container {
    protected double friction;
    protected double od;
    protected Body leftPoint;
    protected Body rightPoint;

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

    public abstract Vector touchesWall(Body body);

    public abstract Body touchesEdge(Body body);

}
