package ss.g3.integrators;

import ss.g3.functions.Acceleration;
import ss.g3.Body;
import ss.g3.functions.Force;

public class Verlet implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {
        Double r = b.getX() + dt * b.getVx() + Math.pow(dt, 2) * f.evaluate(b.getX(), b.getVx()) / b.getM();
        Double v2 = b.getVx() + a.evaluate(b.getX(), b.getVx())*(dt/2);
        Double v = v2 + a.evaluate(r, v2)*(dt/2);

        return new Body(r, 0.0, v, 0.0, b.getM());
    }
}
