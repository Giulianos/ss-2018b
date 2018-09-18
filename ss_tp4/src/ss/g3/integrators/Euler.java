package ss.g3.integrators;

import ss.g3.functions.Acceleration;
import ss.g3.Body;
import ss.g3.functions.Force;

public class Euler implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {
        // Double v = b.getVx() + dt * f.evaluate(b.getX(), b.getVx()) / b.getM();
        // Double r = b.getX() + dt * b.getVx() + Math.pow(dt, 2) * f.evaluate(b.getX(), b.getVx()) / (2*b.getM());

        Double v = b.getVx() + dt * f.evaluate(b.getX(), b.getVx()) / b.getM();
        Double r = b.getX() + dt * v + Math.pow(dt, 2) * f.evaluate(b.getX(), b.getVx())/(2*b.getM());

        return new Body(r, 0.0, v, 0.0, b.getM());
    }
}
