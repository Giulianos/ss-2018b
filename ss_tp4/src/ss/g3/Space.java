package ss.g3;

import java.util.HashSet;
import java.util.Set;

public class Space {
    private double g = 6.693e-11;
    private Set<Body> bodies;

    public Space() {
        this.bodies = new HashSet<>();
    }

    public void calculateForces(){
        double fn;
        double fx;
        double fy;
        double dist;

        for(Body b1: bodies){
            for(Body b2: bodies){
                if(!b1.equals(b2)){
                    dist = distanceBetween(b1,b2);
                    fn = g * b1.getM() * b2.getM() / Math.pow(dist,2);
                    fx = fn * (b2.getX()-b1.getX())/ dist;
                    fy = fn * (b2.getY()-b1.getY())/dist;
                }
            }
        }
    }

    private double distanceBetween(Body b1, Body b2) {
        return Math.sqrt(Math.pow(b2.getX()-b1.getX(),2)+Math.pow(b2.getY()-b1.getY(),2));
    }
}
