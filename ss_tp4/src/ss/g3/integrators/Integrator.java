package ss.g3.integrators;

import ss.g3.Body;
import ss.g3.functions.Acceleration;
import ss.g3.functions.Force;

public interface Integrator {
    public Body calculate(Body b, Double dt, Force f, Acceleration a);
}
