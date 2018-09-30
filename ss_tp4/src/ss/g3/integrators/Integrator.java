package ss.g3.integrators;

import ss.g3.types.Body;
import ss.g3.forces.Force;

public interface Integrator {
    public Body calculate(Body b, Double dt, Force f);

    public String toString();
}
