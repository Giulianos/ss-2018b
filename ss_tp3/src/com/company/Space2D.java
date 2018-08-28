package com.company;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class Space2D {
    private Set<Particle> particles;
    private Double sideLength;
    private Collision collision;

    public Space2D(Double sideLength) {
        this.particles = new HashSet<>();
        this.sideLength = sideLength;
    }

    public void brownianStep() {
        Double wallTc = nextWallCollision();
        Double particleTc = nextParticleCollision();
        Double tc = Math.min(wallTc, particleTc);

        for(Particle p : particles) {
            p.updatePosition(tc);
        }

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

    public Double nextWallCollision() {
        Double minTime = Double.MAX_VALUE;
        Double time;
        for(Particle p : particles) {
            if(p.getVx() > 0) {
                /** Right Wall */
                time = (sideLength - p.getR() - p.getX()) / p.getVx();

                if(time < minTime) {
                    minTime = time;
                    collision = new Collision(p, Collision.WallType.RIGHT);
                }
            } else {
                time = (p.getR() - p.getX()) / p.getVx();

                if(time < minTime) {
                    minTime = time;
                    collision = new Collision(p, Collision.WallType.LEFT);
                }
            }
            if(p.getVy() > 0) {
                /** Top wall */
                time = (sideLength - p.getR() - p.getY()) / p.getVy();
                if(time < minTime) {
                    minTime = time;
                    collision = new Collision(p, Collision.WallType.TOP);
                }
            } else {
                time = (p.getR() - p.getY()) / p.getVy();
                if(time < minTime) {
                    minTime = time;
                    collision = new Collision(p, Collision.WallType.BOTTOM);
                }
            }

        }
        return minTime;
    }

    public Double nextParticleCollision() {
        Set<Particle> neighbours;
        Double minTime = Double.MAX_VALUE;
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
                    if(time < minTime) {
                        minTime = time;
                        collision = new Collision(p, neighbour);
                    }
                }
            }
        }
        return minTime;
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
