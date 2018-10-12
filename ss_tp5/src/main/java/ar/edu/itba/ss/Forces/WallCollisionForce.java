package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Types.Collision;
import ar.edu.itba.ss.Types.Vector;

public class WallCollisionForce implements Force {
    private static Double kn = 1e5;
    private static Double kt = kn*2;
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
        Vector et = en.rotate(-Math.PI/2);

        // Geometric variables
        Double epsilon = wallCollision.getSuperposition();
        Double relativeVelocity = body.getVelocity().dot(et);

        // Forces
        Vector fn = en.multiply(-kn  * epsilon);
        Vector ft = et.multiply(-friction * fn.norm2() * Math.signum(relativeVelocity));

        Vector force = ft.add(fn);

        return force;
    }

    private double getENY(Body p, Body particle) {
        return (particle.getPosition().y - p.getPosition().y) / getDistance(particle.getPosition().x, particle.getPosition().y, p.getPosition().x, p.getPosition().y);
    }

    private double getENX(Body p, Body particle) {
        return (particle.getPosition().x - p.getPosition().x) / getDistance(particle.getPosition().x, particle.getPosition().y, p.getPosition().x, p.getPosition().y);
    }

    private double getFN(Body p, Body other) {
        return -kn * getEpsilon(p, other);
    }

    private double getFT(Body p, Body other) {
        return -kt * getEpsilon(p, other) * (((p.speedX - other.speedX) * (-getENY(p, other)))
                + ((p.speedY - other.speedY) * (getENX(p, other))));
    }

    private double getEpsilon(Body p, Body other) {
        return p.r + other.r - (getDistance(p.x, p.y, other.x, other.y));
    }

    private double getDistance(double x0, double y0, double x1, double y1) {
        return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
    }
}
