package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

public class WallCollisionForce extends ParticleCollisionForce {
    private Body body;
    private Vector wall;

    public WallCollisionForce(Body body, Vector wall) {
        super(body, null);
        this.body = body;
        this.wall = wall;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        Vector aux = body.getPosition().add(wall.multiply(-1.0));
        Vector fn = aux.divide(aux.norm2()).multiply(-1*kn*( b1.getRadius() - Vector.distanceBetween(b1.getPosition(),wall)));

        Vector ft = new Vector(Math.signum(fn.x),Math.signum(fn.y)).multiply(-friction*fn.norm2());

        return ft.add(fn);
    }
}
