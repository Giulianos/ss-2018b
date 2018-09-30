package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

/**
 * Force exerted by Body b1 on b2 (whose current state is passed to the evaluate method)
 */

public class GForce implements Force {

    /** G constant converted to km^3/(kg*day^2) */
    private static final double G = 6.693E-11;

    private Body b1;
    private Body b2;
    static private Integer showed = 10;

    public GForce(Body b1, Body b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        Vector distanceVector = b1.getPosition().add(position.multiply(-1.0));

        Vector force = distanceVector
                .multiply(
                        G*b1.getMass() * b2.getMass() / Math.pow(distanceVector.norm2(), 3)
                );
        if(showed>0 && b1.getTag().equals("sun")) {
            showed--;
            System.err.println(b1.getTag() + " to " + b2.getTag() + ": " + force.norm2());
        }
        return force;
    }
}
