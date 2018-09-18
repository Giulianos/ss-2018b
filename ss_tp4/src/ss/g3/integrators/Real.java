package ss.g3.integrators;

import ss.g3.Body;
import ss.g3.functions.Acceleration;
import ss.g3.functions.Force;

public class Real implements Integrator {
    double time = 0;

    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {
        time += dt;
        Double r = Math.exp(-f.getGamma() * time / (2 * b.getM())) * Math.cos(Math.sqrt(f.getK()/b.getM()-Math.pow(f.getGamma()/(2*b.getM()),2))*time);
        return new Body(r,0.0,0.0,0.0,b.getM());
    }
}
