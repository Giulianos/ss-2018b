package ar.edu.itba.ss.g3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Space2D {
    private Set<Particle> particles;
    private double sideLength;
    private Collision nextCollision;
    private Integer stepCount = 0;

    public Space2D(double sideLength) {
        this.particles = new HashSet<>();
        this.sideLength = sideLength;
    }

    public double brownianStep() {
        if(nextCollision == null) {
            calculateNextCollision();
        }
        double tc = nextCollision.getTimeToCollision();

        updatePositions(tc);

        collisionOperator();

        return tc;
    }

    public void updatePositions(double time) {
        for(Particle p : particles) {
            p.updatePosition(time);
        }
    }

    /**
     * Updates velocity of particles involved in collision
     */
    public void collisionOperator() {
        if(nextCollision.getType() == Collision.CollisionType.PARTICLE) {
            particleCollision(nextCollision);
        } else {
            wallCollision(nextCollision);
        }

        nextCollision = null;
    }

    /**
     * Updates velocity for a particle colliding against a wall
     * @param collision
     */
    private void wallCollision(Collision collision) {
        switch (collision.getWallType()) {
            case VERTICAL:
                collision.getP1().setVy(-collision.getP1().getVy());
                break;
            case HORIZONTAL:
                collision.getP1().setVx(-collision.getP1().getVx());
                break;
        }
    }

    /**
     * Updates velocity for particles colliding to each other
     * @param collision
     */
    public void particleCollision(Collision collision) {
        Particle p1 = collision.getP1();
        Particle p2 = collision.getP2();

        p1.incrementVelocity(collision.getJx()/p1.getM(), collision.getJy()/p1.getM());
        p2.incrementVelocity(collision.getJx()/p2.getM(), collision.getJy()/p2.getM());
    }

    public double nextCollisionTime() {
        return nextCollision == null ? Double.MAX_VALUE : nextCollision.getTimeToCollision();
    }


    public Integer particleQuantity() {
        return particles.size();
    }

    /**
     * Calculates time left for next collision
     */
    public void calculateNextCollision() {
        Collision nextCollision = null;

        double currentHorizTime = Double.MAX_VALUE;
        double currentVertTime = Double.MAX_VALUE;
        double currentMinTime;

        for(Particle p : particles) {
            /* Calculate wall collision */
            if(p.getVx() > 0) {
                currentHorizTime = (sideLength - p.getR() - p.getX()) / p.getVx();
            } else if(p.getVx() < 0) {
                currentHorizTime = (p.getR() - p.getX()) / p.getVx();
            }
            if(p.getVy() > 0) {
                currentVertTime = (sideLength - p.getR() - p.getY()) / p.getVy();
            } else if(p.getVy() < 0){
                currentVertTime = (p.getR() - p.getY()) / p.getVy();
            }
            currentMinTime = currentHorizTime < currentVertTime ? currentHorizTime : currentVertTime;
            if(nextCollision == null || currentMinTime < nextCollision.getTimeToCollision()) {
                nextCollision = new Collision(p, currentVertTime<currentHorizTime ? Collision.WallType.VERTICAL : Collision.WallType.HORIZONTAL, currentMinTime);
            }
            continue;
            /* Calculate collision against neighbours
            for(Particle n : getNeighbours(p)) {
                if(p.getId() >= n.getId()) {
                    continue;
                }
                double collisionTime = timeToCollide(p, n);
                if(collisionTime < nextCollision.getTimeToCollision()) {
                    nextCollision = new Collision(p, n, collisionTime);
                }
            }*/
        }
        this.nextCollision = nextCollision;
    }

    public static double timeToCollide(Particle a, Particle b) {
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

    public void addParticle(Particle p) {
        if(isSuperimposed(p) || isOnEdge(p)){
            return;
        }
        particles.add(p);
    }

    private boolean isSuperimposed(Particle current){
        for(Particle p: particles){
            if(Math.pow((p.getX() - current.getX()),2) + Math.pow((p.getY() - current.getY()),2) <= Math.pow((p.getR() + current.getR()),2)){
                return true;
            }
        }
        return false;
    }

    private boolean isOnEdge(Particle current){
        if((current.getX() <= current.getR()) || (current.getX() >= (sideLength - current.getR()))){
            return true;
        }

        if((current.getY() <= current.getR()) || (current.getY() >= (sideLength - current.getR()))){
            return true;
        }
        return false;
    }

    private Set<Particle> getNeighbours(Particle p) {
        Set<Particle> aux = new HashSet<>(particles);
        aux.remove(p);
        return aux;
    }



    public void printFrame(int frameNumber) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("simulation/frame_"+frameNumber+".txt"));
		writer.write(particles.size()+"\n");
		for (Particle p : particles) {
			writer.write("\n"+p);
		}

		writer.close();
	}

    public ArrayList<Particle> getParticles() {
        return new ArrayList(particles);
    }
}
