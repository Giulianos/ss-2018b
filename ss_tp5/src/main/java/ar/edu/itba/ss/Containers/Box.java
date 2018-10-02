package ar.edu.itba.ss.Containers;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

public class Box extends Container {
    private double high;
    private double width;

    public Box(double friction, double od, double high, double width) {
        super(friction, od);
        this.high = high;
        this.width = width;
        this.leftPoint = new Body(new Vector(width/2 - od/2,high),new Vector(0.0,0.0), Double.MAX_VALUE,0.0);
        this.rightPoint = new Body(new Vector(width/2 + od/2,high),new Vector(0.0,0.0), Double.MAX_VALUE,0.0);
    }

    @Override
    public double getArea() {
        return high*width;
    }

    @Override
    public double getDiameterAt(double depth) {
        return width;
    }

    @Override
    public double getTotalLength() {
        return high;
    }

    @Override
    public Vector touchesWall(Body body) {
        double x = body.getPosition().x;
        double y = body.getPosition().y;
        double r = body.getRadius();
        if(x<r){
            return new Vector(0.0,y);
        }else if(x> width-r){
            return new Vector(width,y);
        }else if(y<r){
            return new Vector(x,0.0);
        }else if(y>high-r){
            return new Vector(x,high);
        }else{
            return null;
        }
    }

    public Body touchesEdge(Body body){
        if(Body.bodiesInTouch(leftPoint,body)){
            return leftPoint;
        }
        if(Body.bodiesInTouch(rightPoint,body)){
            return rightPoint;
        }
        return null;
    }



}
