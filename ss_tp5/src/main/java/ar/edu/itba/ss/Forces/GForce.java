package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Types.Vector;

public class GForce implements Force {

    private static double gravity = 9.8;
    private Body body;

    public GForce(Body body) {
        this.body = body;
    }

    public static void setGravity(double gravity) {
        GForce.gravity = gravity;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        return new Vector(0.0,-1*gravity*body.getMass());
    }
}
