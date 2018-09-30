package ss.g3.integrators;

import ss.g3.types.Body;
import ss.g3.forces.Force;
import ss.g3.types.Vector;

public class Euler implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f) {

        Vector bodyPosition = b.getPosition();
        Vector bodyVelocity = b.getVelocity();
        Vector evaluatedForce = f.evaluate(bodyPosition, bodyVelocity);

        Vector v = evaluatedForce.multiply(dt).divide(b.getM()).add(bodyVelocity);
        Vector r = evaluatedForce.multiply(dt*dt).divide(2*b.getM()).add(v.multiply(dt)).add(bodyPosition);

        return new Body(r, v, b.getM(), b.getTag());
    }

    public String toString(){
        return "euler";
    }
}
