package ar.edu.itba.ss.g3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Space2D {
    private Set<Particle> particles;
    private Particle bigParticle;
    private double L;
    private Collision nextCollision;

    public Space2D(double L) {
        particles = new HashSet<>();
        this.L = L;
    }

    /**
     * Checks whether p is over another particle
     * @param p
     * @return true if it's superimposed, false otherwise
     */
    private boolean isSuperimposed(Particle p) {
        for (Particle particle : particles) {
            if (Math.sqrt(Math.pow(particle.getX() - p.getX(), 2)
                    + Math.pow(particle.getY() - p.getY(), 2)) < (particle.getR() + p.getR()))
                return true;
        }
        return false;
    }

    /**
     * Adds p1 particle to the space, if it overlaps with another
     * particle, it's not added. If its the big particle, it always replaces
     * the current big particle.
     * @param p The particle to add
     * @param isBig If it is the big particle
     * @return
     */
    public boolean addParticle(Particle p, boolean isBig) {
        if(isBig) {
            particles.remove(bigParticle);
            bigParticle = p;
            particles.add(bigParticle);
            return true;
        }

        if(isSuperimposed(p))
            return false;

        particles.add(p);
        return true;
    }

    public void runOvito(double seconds, int fps) throws IOException {
        double secondsLeft = seconds;
        final int FPS = fps;
        double frameTimeStep = 1.0/FPS;
        long totalFrames = (long)Math.ceil(seconds)*FPS;
        int frame = 0;

        while (secondsLeft > 0) {
            Collision nextCrash = calculateNextCollision();
            if (nextCrash.getSeconds() > secondsLeft) {
                return;
            }
            updatePositions(nextCrash.getSeconds());
            double currentTime = seconds - secondsLeft + nextCrash.getSeconds();
            int currentFrame = (int) Math.floor(currentTime / frameTimeStep);
            while (frame < currentFrame) {
                printOvitoFrame(frame, currentTime - frame * frameTimeStep);
                frame++;
                System.out.println("Simulation status: " + 100.0*(1.0*frame/totalFrames) + "%");
            }
            crash(nextCrash);
            secondsLeft -= nextCrash.getSeconds();
        }
    }


    public void runSimulation(double endTime) throws IOException {
        double time=0;
        double checkpoint = 1;
        int crashed =0;
        while (time < endTime) {
            Collision nextCrash = calculateNextCollision();
            updatePositions(nextCrash.getSeconds());

            crashed++;
            time += nextCrash.getSeconds();
            if(time > checkpoint){
                   System.out.println(crashed+" in "+checkpoint+" seconds");
                crashed = 0;
                checkpoint++;
            }
            crash(nextCrash);
        }
    }

    public void runSimulationDCM(double endTime) throws IOException {
        double time=0.0;
        double checkpoint = 0.0;
        while (time < endTime && !bigParticle.didCollideAgainstWall()) {
            Collision nextCrash = calculateNextCollision();
            if((time + nextCrash.getSeconds()) > checkpoint) {
                double x = 0.25 - bigParticle.getXat(checkpoint-time);
                double y = 0.25 - bigParticle.getYat(checkpoint-time);
                System.out.println((x*x + y*y));
                checkpoint+=1.0;
            }
            updatePositions(nextCrash.getSeconds());
            time += nextCrash.getSeconds();
            crash(nextCrash);
        }
    }


    private void updatePositions(double deltaT) {
        for (Particle particle : particles) {
            particle.updatePosition(deltaT);
        }
    }

    private void crash(Collision collision) {
        if (collision.isWallType()) {
            if (collision.horizontalWallCollition()) {
                collision.getP1().swapDirectionY();
            } else {
                collision.getP1().swapDirectionX();
            }
        } else {
            collision.getP1().increaseVx(collision.getJx() / collision.getP1().getM());
            collision.getP2().increaseVx(-collision.getJx() / collision.getP2().getM());
            collision.getP1().increaseVy(collision.getJy() / collision.getP1().getM());
            collision.getP2().increaseVy(-collision.getJy() / collision.getP2().getM());
        }

    }

    private Collision calculateNextCollision() {
        Collision minCrash = null;

        for (Particle a : particles) {

            double crashVerticalWall = Double.MAX_VALUE;
            double crashHorizontalWall = Double.MAX_VALUE;

            if (a.getVx() > 0) {
                crashVerticalWall = (this.L - a.getR() - a.getX()) / a.getVx();
            } else if (a.getVx() < 0) {
                crashVerticalWall = (0 + a.getR() - a.getX()) / a.getVx();
            }

            if (a.getVy() > 0) {
                crashHorizontalWall = (this.L - a.getR() - a.getY()) / a.getVy();
            } else if (a.getVy() < 0) {
                crashHorizontalWall = (0 + a.getR() - a.getY()) / a.getVy();
            }

            double timeToCrash = Math.min(crashHorizontalWall, crashVerticalWall);

            if (minCrash == null || timeToCrash < minCrash.getSeconds()) {
                minCrash = new Collision(a, timeToCrash,
                        crashHorizontalWall < crashVerticalWall ? Collision.WallType.HORIZONTAL : Collision.WallType.VERTICAL);
            }

            for (Particle b : particles) {
                if (a.getId() >= b.getId()) {
                    continue;
                }
                timeToCrash = timeToCrash(a, b);
                if (minCrash == null || timeToCrash < minCrash.getSeconds()) {
                    minCrash = new Collision(a, b, timeToCrash);
                }
            }
        }
        if(minCrash.getSeconds() < 0) {
            throw new IllegalStateException();
        }
        return minCrash;
    }

    public static double timeToCrash(Particle a, Particle b) {
        double sigma = a.getR() + b.getR();
        double deltaVX = b.getVx() - a.getVx();
        double deltaVY = b.getVy() - a.getVy();

        double deltaX = b.getX() - a.getX();
        double deltaY = b.getY() - a.getY();

        double vr = deltaVX * deltaX + deltaVY * deltaY;

        if (vr >= 0) {
            return Double.MAX_VALUE;
        }

        double vv = Math.pow(deltaVX, 2) + Math.pow(deltaVY, 2);
        double rr = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);

        double d = Math.pow(vr, 2) - vv * (rr - Math.pow(sigma, 2));

        if (d < 0) {
            return Double.MAX_VALUE;
        }

        return -(vr + Math.sqrt(d)) / vv;
    }

    public void printOvitoFrame(int frameNumber, double delta) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("simulation/frame_"+frameNumber+".txt"));
        int n = particles.size();
        writer.write(n+"\n");
        double maxSpeed = 0;
        for (Particle particle : particles) {
            double aux = particle.getAbsoluteSpeed();
            if (aux > maxSpeed)
                maxSpeed = aux;
        }
        for (Particle movingParticle : particles) {
            double x = movingParticle.getX() - (movingParticle.getVx() * delta);
            double y = movingParticle.getY() - (movingParticle.getVy() * delta);
            double speedAux = movingParticle.getAbsoluteSpeed() / maxSpeed;
            String type = movingParticle.getR()>0.005 ? "1" : "0";
            writer.write("\n" + x + " " + y + " " + movingParticle.getR() + " " + type);
        }

        writer.close();
    }
}
