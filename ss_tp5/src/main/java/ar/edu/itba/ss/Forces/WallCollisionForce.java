package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

public class WallCollisionForce implements Force {
    private static double kn;
    private static double gamma;
    private static double friction;
    private Body body;
    private Vector wall;

    public WallCollisionForce(Body body, Vector wall) {
        this.body = body;
        this.wall = wall;
    }

    public static void setKn(double kn) {
        WallCollisionForce.kn = kn;
    }

    public static void setFriction(double friction) {
        WallCollisionForce.friction = friction;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        Vector distance = body.getPosition().diff(wall);

        // Directions
        Vector en = distance.direction();
        Vector et = en.rotate(Math.PI/2);

        // Geometric variables
        Double epsilon = null;
        Double relativeVelocity = body.getVelocity().dot(et);


        // Forces
        Vector fn = en.multiply((-kn - gamma) * epsilon);
        Vector ft = et.multiply(-friction * fn.norm2() * Math.signum(relativeVelocity));

        return null;
    }
}
