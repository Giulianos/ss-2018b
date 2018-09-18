package ss.g3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Space {
    private double g = 6.693e-11;
    private Set<Body> bodies;

    public Space() {
        this.bodies = new HashSet<>();
        Random r = new Random();
        Body sun = new Body(r.nextDouble(),r.nextDouble(),0.0,0.0,1988500e24);
        Body earth = new Body(r.nextDouble(),r.nextDouble(),0.0,0.0,5.97219e24);
        Body jupiter = new Body(r.nextDouble(),r.nextDouble(),0.0,0.0,1898.13e24);
        Body saturn = new Body(r.nextDouble(),r.nextDouble(),0.0,0.0,5.6834e26);
        Body spaceship = new Body(r.nextDouble(),r.nextDouble(),0.0,0.0,721.9);

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
