package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Types.Vector;

public class ParticleCollisionForce implements Force{
    private static Double kn = 1e5;
    private static Double gamma = 100.0;
    private static Double friction = 0.1;
    private Body b1;
    private Body b2;

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
    public Vector evaluate(Vector position, Vector velocity) {
        Vector distance = b2.getPosition().diff(b1.getPosition());
        Vector distancePrev = null;
        if(b1.getPreviousPosition() != null && b2.getPreviousPosition() != null)
            distancePrev = b1.getPreviousPosition().diff(b2.getPreviousPosition());

        // Directions
        Vector en = distance.direction(); // Normal to contact
        Vector et = en.rotate(Math.PI/2); // Tangent to contact

        // Geometric variables
        Double epsilon = b1.getRadius() + b2.getRadius() - distance.norm2();
        Double epsilonPrev = 0.0;
        if(distancePrev != null)
            epsilonPrev = b1.getRadius() + b2.getRadius() - distancePrev.norm2();
        Double dEpsilonDt = (epsilon-epsilonPrev) / b1.getDtBetweenStates();

        Double relativeVelocity = b1.getVelocity().diff(b2.getVelocity()).dot(et); // Relative velocity in tangent direction

        // Forces
        Vector fn = en.multiply(-kn*epsilon -gamma*dEpsilonDt);
        Vector ft = et.multiply(-friction * fn.norm2() * Math.signum(relativeVelocity));

        Vector force = ft.add(fn);

        return force;
    }
}
