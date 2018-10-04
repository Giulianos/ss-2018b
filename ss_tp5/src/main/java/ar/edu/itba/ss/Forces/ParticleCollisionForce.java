package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

public class ParticleCollisionForce implements Force{
    private static double kn;
    private static double gamma;
    private static double friction;
    private Body b1;
    private Body b2;

    public ParticleCollisionForce(Body b1, Body b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    public static void setKn(double kn) {
        ParticleCollisionForce.kn = kn;
    }

    public static void setFriction(double friction) {
        ParticleCollisionForce.friction = friction;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        Vector distance = b1.getPosition().diff(b2.getPosition());

        // Directions
        Vector en = distance.direction(); // Normal to contact
        Vector et = en.rotate(Math.PI/2); // Tangent to contact

        // Geometric variables
        Double epsilon = b1.getRadius() + b2.getRadius() - distance.norm2();
        Double relativeVelocity = b1.getVelocity().diff(b2.getVelocity()).dot(et); // Relative velocity in tangent direction

        // Forces
        Vector fn = en.multiply((-kn - gamma)*epsilon);
        Vector ft = et.multiply(-friction * fn.norm2() * Math.signum(relativeVelocity));

        return ft.add(fn);

    }
}
