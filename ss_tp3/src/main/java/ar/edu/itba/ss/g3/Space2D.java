package ar.edu.itba.ss.g3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Space2D {
    private Set<Particle> particles;
    private Double sideLength;
    private Double nextCollisionTime;
    private List<Collision> nextCollisions;

    public Space2D(Double sideLength) {
        this.particles = new HashSet<>();
        this.sideLength = sideLength;
    }

    public Double brownianStep(Double maxDeltaT) {
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
            return 0.0;
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
        return (maxDeltaT - nextCollisionTime);
    }

    public Integer particleQuantity() {
        return particles.size();
    }

    public void collisionOperator(Particle p1, Particle p2) {
        Double deltaX = p2.getX() - p1.getX();
        Double deltaY = p2.getY() - p1.getY();
        Double sigma = p1.getR() + p2.getR();
        Double deltaVx = p2.getVx() - p1.getVx();
        Double deltaVy = p2.getVy() - p1.getVy();
        Double deltaRx = p2.getX() - p1.getX();
        Double deltaRy = p2.getY() - p1.getY();
        Double deltaVdeltaR = deltaVx*deltaRx + deltaVy*deltaRy;
        Double J = 2 * p1.getM() * p2.getM() * deltaVdeltaR / (sigma * (p1.getM() + p2.getM()));
        Double Jx = J * deltaX / sigma;
        Double Jy = J * deltaY / sigma;

        p1.setVx(p1.getVx() + Jx / p1.getM());
        p1.setVy(p1.getVy() + Jy / p1.getM());
        p2.setVx(p2.getVx() + Jx / p2.getM());
        p2.setVy(p2.getVy() + Jy / p2.getM());
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
            } else {
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
                    nextCollisions.add(new Collision(p, Collision.WallType.LEFT));
                }
            } else {
                time = (p.getR() - p.getY()) / p.getVy();
                if(time < nextCollisionTime) {
                    nextCollisionTime = time;
                    nextCollisions = new LinkedList<>();
                    nextCollisions.add(new Collision(p, Collision.WallType.BOTTOM));
                } else if(time == nextCollisionTime) {
                    nextCollisions.add(new Collision(p, Collision.WallType.LEFT));
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
                Double deltaVx = neighbour.getVx() - p.getVx();
                Double deltaVy = neighbour.getVy() - p.getVy();
                Double deltaRx = neighbour.getX() - p.getX();
                Double deltaRy = neighbour.getY() - p.getY();
                Double deltaVdeltaR = deltaVx*deltaRx + deltaVy*deltaRy;
                Double deltaVdeltaV = deltaVx*deltaVx + deltaVy*deltaVy;
                Double deltaRdeltaR = deltaRx*deltaRx + deltaRy*deltaRy;
                Double sigma = neighbour.getR() + p.getR();
                Double d = deltaVdeltaR*deltaVdeltaR - deltaVdeltaV * (deltaRdeltaR - sigma*sigma);

                if(deltaVdeltaR < 0 && d >= 0) {
                    time = -1 * (deltaVdeltaR + Math.sqrt(d))/deltaVdeltaV;
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
        particles.add(p);
    }

    private Set<Particle> getNeighbours(Particle p) {
        Set<Particle> aux = new HashSet<>(particles);
        aux.remove(p);
        return aux;
    }
}
