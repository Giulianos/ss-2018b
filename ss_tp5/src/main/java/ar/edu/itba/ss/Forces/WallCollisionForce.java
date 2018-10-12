package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Types.Collision;
import ar.edu.itba.ss.Types.Vector;

public class WallCollisionForce implements Force {
    private static Double kn = 1e5;
    private static Double kt = kn*2;
    private static Double gamma = 100.0;
    private static Double friction = 0.1;
    private Body body;
    private Collision wallCollision;

    public WallCollisionForce(Body body, Collision wallCollision) {
        this.body = body;
        this.wallCollision = wallCollision;
    }

    public static void setKn(Double kn) {
        WallCollisionForce.kn = kn;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        Vector distance = wallCollision.getPosition().diff(body.getPosition());

        // Directions
        Vector en = distance.direction();
        Vector et = en.rotate(-Math.PI / 2);

        // Geometric variables
        Double epsilon = wallCollision.getSuperposition();
        Double relativeVelocity = body.getVelocity().dot(et);

        // Forces
        Vector fn = en.multiply(-kn * epsilon - gamma*epsilon);
        Vector ft = et.multiply(-friction * fn.norm2() * Math.signum(relativeVelocity));

        Vector force = ft.add(fn);

        return force;
    }
}
