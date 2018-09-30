package ss.g3.integrators;

import ss.g3.types.Body;
import ss.g3.forces.Force;
import ss.g3.types.Vector;

public class Verlet implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f) {

        Vector bodyPosition = b.getPosition();
        Vector bodyVelocity = b.getVelocity();
        Vector evaluatedForce = f.evaluate(bodyPosition, bodyVelocity);

        Vector r = evaluatedForce
                    .multiply(dt*dt/b.getM())
                    .add(
                            bodyVelocity.multiply(dt)
                    )
                    .add(bodyPosition);

        Vector v2 = evaluatedForce
                    .multiply(dt/(2*b.getM()))
                    .add(bodyVelocity);

        Vector evaluatedForce2 = f.evaluate(bodyPosition, v2);

        Vector v = evaluatedForce2
                    .multiply(dt/(2*b.getM()))
                    .add(v2);

        return new Body(r, v, b.getM(), b.getTag());
    }

    public String toString(){
        return "verlet";
    }
}
