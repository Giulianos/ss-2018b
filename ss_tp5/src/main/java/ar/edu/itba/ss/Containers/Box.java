package ar.edu.itba.ss.Containers;

import ar.edu.itba.ss.Particles.Body;

public class Box extends Container {
    private double high;
    private double width;

    public Box(double friction, double od, double high, double width) {
        super(friction, od);
        this.high = high;
        this.width = width;
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
    public boolean touchesWall(Body body) {
        double x = body.getPosition().x;
        double y = body.getPosition().y;
        double r = body.getRadius();
       if(x<r || x> width-r || y<r || y>high-r){
           return true;
       }
       return false;
    }



}
