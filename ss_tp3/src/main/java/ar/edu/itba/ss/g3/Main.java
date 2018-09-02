package ar.edu.itba.ss.g3;

import sun.jvm.hotspot.memory.Space;

import java.io.IOException;
import java.sql.Time;
import java.util.Random;

public class Main {

    public static void ovitoSimulation(Space2D space, double time) throws IOException {
        double simulationTimeLeft = time;
        Integer FPS = 25;
        Integer currentFrame = 0;
        double timeStep = 1.0/FPS;
        Long totalFrames = Math.round(time/timeStep);

        while(simulationTimeLeft > 0) {
            double currentTimeStepLeft = timeStep;
            do {
                space.calculateNextCollision();
                currentTimeStepLeft-=space.nextCollisionTime();
                simulationTimeLeft-=space.nextCollisionTime();
                //System.out.println(space.nextCollisionTime());
                space.updatePositions(space.nextCollisionTime());
                space.collisionOperator();
            } while(currentTimeStepLeft > 0d);
                System.out.println("Generated " + currentFrame + "/" + totalFrames + " frames!");
                System.out.println("Time left: " + simulationTimeLeft);
            space.printFrame(currentFrame++);
        }
    }

    public static void main(String[] args) throws IOException {
        double l = 0.5;
        Random random = new Random();
        Space2D space = new Space2D(l);
        Integer n = 300;

        space.addParticle(new Particle(l/2, l/2, 0.0, 0.0, 100.0, 0.05));

        while(space.particleQuantity() < n) {
            double theta = random.nextDouble() * 2 * Math.PI;
            double v = random.nextDouble() * 0.01;
            double vx = v * Math.cos(theta);
            double vy  = v * Math.sin(theta);
            double x = random.nextDouble() * l;
            double y = random.nextDouble() * l;
            Particle p = new Particle(x, y, vx, vy, 0.1, 0.005);
            space.addParticle(p);
        }
        ovitoSimulation(space, 10.0);
    }
}
