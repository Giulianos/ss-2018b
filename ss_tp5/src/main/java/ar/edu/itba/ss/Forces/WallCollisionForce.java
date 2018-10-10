package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Types.Collision;
import ar.edu.itba.ss.Types.Vector;

public class WallCollisionForce implements Force {
    private static Double kn = 1e5;
    private static Double gamma = 0.0;
    private static Double friction = 0.7;
    private Body body;
    private Collision wallCollision;

    public WallCollisionForce(Body body, Collision wallCollision) {
        this.body = body;
        this.wallCollision = wallCollision;
    }

    public static void setKn(Double kn) {
        WallCollisionForce.kn = kn;
    }

    public static void setGamma(Double gamma) {
        WallCollisionForce.gamma = gamma;
    }

    public static void setFriction(Double friction) {
        WallCollisionForce.friction = friction;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        Vector distance = body.getPosition().diff(wallCollision.getPosition());

        // Directions
        Vector en = distance.direction();
        Vector et = en.rotate(Math.PI/2);

        // Geometric variables
        Double epsilon = wallCollision.getSuperposition();
        Double relativeVelocity = body.getVelocity().dot(et);

        // Forces
        Vector fn = en.multiply((-kn - gamma) * epsilon);
        Vector ft = et.multiply(-friction * fn.norm2() * Math.signum(relativeVelocity));

        return ft.add(fn);
    }
}
