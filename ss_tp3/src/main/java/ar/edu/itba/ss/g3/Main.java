package ar.edu.itba.ss.g3;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        Double l = 0.5;
        Random random = new Random();
        Space2D space = new Space2D(l);
        Integer n = 200;
        Integer steps = 2000;

        space.addParticle(new Particle(l/2, l/2, 0.0, 0.0, 100.0, 0.05));

        while(space.particleQuantity() < n) {
            Double theta = random.nextDouble() * 2 * Math.PI;
            Double vx = 0.1 * Math.cos(theta);
            Double vy  = 0.1 * Math.sin(theta);
            Double x = random.nextDouble() * l;
            Double y = random.nextDouble() * l;
            Particle p = new Particle(x, y, vx, vy, 0.1, 0.005);
            space.addParticle(p);
            System.out.println(space.particleQuantity());
        }

        for(int i = 0; i < steps; i++ ) {
            System.out.println(i);
//            for(int j = 0 ; j < n ; j ++){
//                Particle p = space.getParticles().get(j);
//                System.out.println(j+"  "+p.getX()+"    "+p.getY()+"    "+p.getVx()+"   "+p.getVy());
//            }
            space.printFrame(i);
            space.brownianStep(0.5);

 //           System.out.println(space.getParticles());
        }
    }
}
