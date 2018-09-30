package ar.edu.itba.ss;

import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Spring {
    private static Container box;
    private static Space space;
    private static Vector radiusInterval;
    private static int N;
    private static double mass;

    public Spring( Container container,double gravity,double min, double max, int N, double mass) {
        this.space = new Space(container,initParticles(),gravity);
        this.radiusInterval = new Vector(min,max);
        this.N = N;
        this.mass = mass;
    }

    public Set<Body> initParticles(){

        Set<Body> bodies = new HashSet<>();

        Random r = new Random();

        while(bodies.size() < N){
            Vector position = new Vector(r.nextDouble(),r.nextDouble());
            Vector velocity = new Vector(0.0,0.0);
            double radius = r.nextDouble()*(radiusInterval.y-radiusInterval.x)+radiusInterval.x;
            Body body = new Body(position,velocity,radius,mass);
            if(!box.touchesWall(body)) {
                bodies.add(body);
            }
        }

        return bodies;
    }

}
