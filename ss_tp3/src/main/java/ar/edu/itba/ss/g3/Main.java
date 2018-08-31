package ar.edu.itba.ss.g3;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Double l = 50.0;
        Random random = new Random();
        Space2D space = new Space2D(l);
        Integer n = 100;
        Integer steps = 700;

        space.addParticle(new Particle(l/2, l/2, 0.0, 0.0, 100.0, 0.05));

        while(space.particleQuantity() < n - 1) {
            Double theta = random.nextDouble() * 2 * Math.PI;
            Double vx = 0.01 * Math.cos(theta);
            Double vy  = 0.01 * Math.sin(theta);
            Double x = random.nextDouble() * l;
            Double y = random.nextDouble() * l;
            Particle p = new Particle(x, y, vx, vy, 0.1, 0.005);
            System.out.println(p);
            space.addParticle(p);
        }

        while(steps-- > 0) {
            while(space.brownianStep(0.5) == 0) {}
            System.out.println("Time step generated");
        }
    }
}