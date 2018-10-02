package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

public class ParticleCollisionForce implements Force{
    protected static double kn;
    protected static double kt;
    protected static double friction;
    protected Body b1;
    private Body b2;

    public ParticleCollisionForce(Body b1, Body b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    public static void setKn(double kn) {
        ParticleCollisionForce.kn = kn;
        ParticleCollisionForce.kt = 2*kn;
    }

    public static void setFriction(double friction) {
        ParticleCollisionForce.friction = friction;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {

        Vector aux = b1.getPosition().add(b2.getPosition().multiply(-1.0));
        Vector fn = aux.divide(aux.norm2()).multiply(-1*kn*( b2.getRadius() + b1.getRadius() - Vector.distanceBetween(b1.getPosition(),b2.getPosition())));

        Vector ft = new Vector(Math.signum(fn.x),Math.signum(fn.y)).multiply(-friction*fn.norm2());

        return ft.add(fn);

    }
}
