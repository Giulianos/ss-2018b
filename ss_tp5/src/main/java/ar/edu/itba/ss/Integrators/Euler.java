package ar.edu.itba.ss.Integrators;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Forces.Force;
import ar.edu.itba.ss.Types.Vector;

public class Euler implements Integrator {
    @Override
    public void calculate(Body b, Double dt, Force f) {

        Vector bodyPosition = b.getPosition();
        Vector bodyVelocity = b.getVelocity();
        Vector evaluatedForce = f.evaluate(bodyPosition, bodyVelocity);

        Vector v = evaluatedForce.multiply(dt).divide(b.getMass()).add(bodyVelocity);
        Vector r = evaluatedForce.multiply(dt*dt).divide(2*b.getMass()).add(v.multiply(dt)).add(bodyPosition);

        b.setPosition(r);
        b.setVelocity(v);

    }

    public String toString(){
        return "euler";
    }
}