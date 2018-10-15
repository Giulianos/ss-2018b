package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;

public class ParticleCollisionForce implements Force{
    private static Double kn = 1e5;
    private static Double gamma = 100.0;
    private static Double friction = 0.7;
    private Body b1;
    private Body b2;

    private static Double average = 0d;
    private static Double quantity = 0d;

    private Double x;
    private Double y;

    public ParticleCollisionForce(Body b1, Body b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    public static void setKn(Double kn) {
        ParticleCollisionForce.kn = kn;
    }

    public static void setGamma(Double gamma) {
        ParticleCollisionForce.gamma = gamma;
    }

    public static void setFriction(Double friction) {
        ParticleCollisionForce.friction = friction;
    }

    @Override
    public void evaluate() {

        // Distance
        Double rx = b2.getPositionX() - b1.getPositionX();
        Double ry = b2.getPositionY() - b1.getPositionY();
        Double rmod = Math.sqrt(rx*rx + ry*ry);

        // Relative velocity
        Double vx = b1.getVelocityX() - b2.getVelocityX();
        Double vy = b1.getVelocityY() - b2.getVelocityY();

        // Calculate normal direction
        Double enX = rx/rmod;
        Double enY = ry/rmod;

        // Calculate tangential direction
        Double etX = -enY;
        Double etY = enX;

        // Geometric variables
        Double epsilon = b1.getRadius() + b2.getRadius() - rmod;
        Double epsilondt = 0.0;


        Double relativeVelocity = vx*etX + vy*etY;

        Double fn = -kn * epsilon - gamma*epsilondt;
        Double ft = -friction * fn * Math.signum(relativeVelocity);

         //average = average*(quantity/(quantity+1.0)) + fn/(quantity+1.0);
         //quantity++;

         //System.out.println("Average: " + average);

        x = fn*enX + ft*etX;
        y = fn*enY + ft*etY;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }
}
