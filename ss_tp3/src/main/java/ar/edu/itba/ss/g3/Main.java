package ar.edu.itba.ss.g3;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        /* Check arguments */
        if(args.length < 3) {
            System.err.println("usage: java -jar program.jar [L] [N] [Time]");
            return;
        }

        /* Setup space with provided parameters */
        Space2D space = setupSpace(Double.valueOf(args[0]), Integer.valueOf(args[1]));

        /* Generate simulation with ovito output */
        space.runOvito(Double.valueOf(args[2]));
    }

    public static Space2D setupSpace(double l, int n) {
        Random random = new Random();
        Space2D space = new Space2D(l);

        /* Simulation parameters */
        Double speed = 0.01;

        /* Add big particle to space */
        Particle bigParticle = new Particle(l / 2, l / 2, 0, 0, 0.05, 1.0);
        space.addParticle(bigParticle, true);

        /* Generate small particles */
        for (int i = 0; i < n-1; i++) {
            Particle p = null;
            do {
                double x = Math.random() * (l - 2 * 0.005) + 0.005;
                double y = Math.random() * (l - 2 * 0.005) + 0.005;
                double theta = Math.random() * 2 * Math.PI;
                double speedX = Math.random() * speed * Math.cos(theta);
                double speedY = Math.random() * speed * Math.sin(theta);
                p = new Particle(x, y, speedX, speedY, 0.005, 0.1);
            } while (!space.addParticle(p, false));
        }

        return space;
    }
}
