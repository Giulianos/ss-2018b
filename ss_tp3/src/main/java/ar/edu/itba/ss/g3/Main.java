package ar.edu.itba.ss.g3;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        /* Check arguments */
        if(args.length < 5) {
            System.err.println("usage: java -jar program.jar [L] [N] [M] [T] [FPS]");
            System.err.println("  [L]: side length of simulation space");
            System.err.println("  [N]: number of particles (big particle included)");
            System.err.println("  [M]: mass of big particle");
            System.err.println("  [T]: simulation time");
            System.err.println("  [FPS]: frames per second for ovito output");
            return;
        }

        /* Setup space with provided parameters */
        Space2D space = setupSpace(Double.valueOf(args[0]), Integer.valueOf(args[1]), Double.valueOf(args[2]));

        /* Generate simulation with ovito output */
        //space.runOvito(Double.valueOf(args[3]), Integer.valueOf(args[4]));
        //space.runSimulation(Double.valueOf(args[3]));
        space.runSimulationDCM(100.0);
    }

    public static Space2D setupSpace(double l, int n, double bigParticleMass) {
        Random random = new Random();
        Space2D space = new Space2D(l);

        /* Simulation parameters */
        Double maxSpeed = 0.1;

        /* Add big particle to space */
        Particle bigParticle = new Particle(l / 2, l / 2, 0, 0, 0.05, bigParticleMass);
        space.addParticle(bigParticle, true);

        /* Generate small particles */
        for (int i = 0; i < n-1; i++) {
            Particle p = null;
            do {
                double x = Math.random() * (l - 2 * 0.005) + 0.005;
                double y = Math.random() * (l - 2 * 0.005) + 0.005;
                double speed = Math.random() * maxSpeed;
                double theta = Math.random() * 2 * Math.PI;
                double speedX = speed * Math.cos(theta);
                double speedY = speed * Math.sin(theta);
                p = new Particle(x, y, speedX, speedY, 0.005, 0.1);
            } while (!space.addParticle(p, false));
        }

        return space;
    }
}
