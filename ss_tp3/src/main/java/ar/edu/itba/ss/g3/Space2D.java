package ar.edu.itba.ss.g3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Space2D {
    private Set<Particle> particles;
    private Double sideLength;
    private Double nextCollisionTime;
    private List<Collision> nextCollisions;

    public Space2D(Double sideLength) {
        this.particles = new HashSet<>();
        this.sideLength = sideLength;
    }

    public void brownianStep(Double maxDeltaT) {
        nextWallCollision();
        nextParticleCollision();
        Double tc = Math.min(nextCollisionTime, maxDeltaT);

        for(Particle p : particles) {
            p.updatePosition(tc);
        }

        /** If the considered time step is smaller than the time step till next collision
         *  then just update the positions (no collision needs to be calculated)
         */
        if(nextCollisions != null && maxDeltaT < nextCollisionTime) {
            return;
        }

        for(Collision collision : nextCollisions) {
            if(collision.getType() == Collision.CollisionType.PARTICLE) {
                collisionOperator(collision.getP1(), collision.getP2());
            } else {
                switch (collision.getWallType()) {
                    case TOP:
                    case BOTTOM:
                        collision.getP1().setVy(-1 * collision.getP1().getVy());
                        break;
                    case RIGHT:
                    case LEFT:
                        collision.getP1().setVx(-1 * collision.getP1().getVx());
                        break;
                }
            }
        }
    }

    public Integer particleQuantity() {
        return particles.size();
    }

    public void collisionOperator(Particle p1, Particle p2) {

        double deltaX = p2.getX() - p1.getX();
        double deltaY = p2.getY() - p1.getY();

        double sigma = p1.getR() + p2.getR();

        double deltaVx = p2.getVx() - p1.getVx();
        double deltaVy = p2.getVy() - p1.getVy();

        double vr = deltaVx*deltaX + deltaVy*deltaY;

        double j = 2*p1.getM()*p2.getM()*(vr)/(sigma*(p1.getM()+p2.getM()));
        double jx = j*deltaX/sigma;
        double jy = j*deltaY/sigma;

        p1.setVx(p1.getVx() + jx / p1.getM());
        p1.setVy(p1.getVy() + jy / p1.getM());
        p2.setVx(p2.getVx() + jx / p2.getM());
        p2.setVy(p2.getVy() + jy / p2.getM());
    }

    public void nextWallCollision() {
        Double time;
        if(nextCollisionTime == null) {
            nextCollisionTime = Double.MAX_VALUE;
        }
        for(Particle p : particles) {
            if(p.getVx() > 0) {
                /** Right Wall */
                time = (sideLength - p.getR() - p.getX()) / p.getVx();
                if(time < nextCollisionTime) {
                    nextCollisionTime = time;
                    nextCollisions = new LinkedList<>();
                    nextCollisions.add(new Collision(p, Collision.WallType.RIGHT));
                } else if(time == nextCollisionTime) {
                    nextCollisions.add(new Collision(p, Collision.WallType.RIGHT));
                }
            } else if(p.getVx() < 0){
                time = (p.getR() - p.getX()) / p.getVx();

                if(time < nextCollisionTime) {
                    nextCollisionTime = time;
                    nextCollisions = new LinkedList<>();
                    nextCollisions.add(new Collision(p, Collision.WallType.LEFT));
                } else if(time == nextCollisionTime) {
                    nextCollisions.add(new Collision(p, Collision.WallType.LEFT));
                }
            }
            if(p.getVy() > 0) {
                /** Top wall */
                time = (sideLength - p.getR() - p.getY()) / p.getVy();
                if(time < nextCollisionTime) {
                    nextCollisionTime = time;
                    nextCollisions = new LinkedList<>();
                    nextCollisions.add(new Collision(p, Collision.WallType.TOP));
                } else if(time == nextCollisionTime) {
                    nextCollisions.add(new Collision(p, Collision.WallType.TOP));
                }
            } else if(p.getVy() < 0){
                time = (p.getR() - p.getY()) / p.getVy();
                if(time < nextCollisionTime) {
                    nextCollisionTime = time;
                    nextCollisions = new LinkedList<>();
                    nextCollisions.add(new Collision(p, Collision.WallType.BOTTOM));
                } else if(time == nextCollisionTime) {
                    nextCollisions.add(new Collision(p, Collision.WallType.BOTTOM));
                }
            }
        }

    }

    public void nextParticleCollision() {
        Set<Particle> neighbours;
        Double time;
        for(Particle p : particles) {
            neighbours = getNeighbours(p);
            for(Particle neighbour : neighbours) {
                Double deltaX = neighbour.getX() - p.getX();
                Double deltaY = neighbour.getY() - p.getY();

                Double deltaVx = neighbour.getVx() - p.getVx();
                Double deltaVy = neighbour.getVy() - p.getVy();

                Double vr = deltaVx*deltaX + deltaVy*deltaY;
                Double vv = deltaVx*deltaVx + deltaVy*deltaVy;
                Double rr = deltaX*deltaX + deltaY*deltaY;

                Double sigma = neighbour.getR() + p.getR();

                Double d = vr*vr - vv*(rr - sigma*sigma);

                if(vr < 0 && d >= 0) {
                    time = -1 * (vr + Math.sqrt(d))/vv;
                    if(time < nextCollisionTime) {
                        nextCollisionTime = time;
                        nextCollisions = new LinkedList<>();
                        nextCollisions.add(new Collision(p, neighbour));
                    } else if(time == nextCollisionTime) {
                        nextCollisions.add(new Collision(p, neighbour));
                    }
                }
            }
        }
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
