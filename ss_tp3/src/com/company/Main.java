package com.company;

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
            Double vx = random.nextDouble() * 0.2 - 0.1;
            Double vy = random.nextDouble() * 0.2 - 0.1;
            Double x = random.nextDouble() * l;
            Double y = random.nextDouble() * l;
            Particle p = new Particle(x, y, vx, vy, 0.1, 0.005);
            System.out.println(p);
            space.addParticle(p);
        }

    }
}
