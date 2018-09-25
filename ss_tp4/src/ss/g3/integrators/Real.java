package ss.g3.integrators;

import ss.g3.types.Body;
import ss.g3.forces.Force;

public class Real implements Integrator {
    double time = 0.0;

    @Override
    public Body calculate(Body b, Double dt, Force f) {
        time += dt;
        Double r = Math.exp(-100.0 * time / (2 * b.getM())) * Math.cos(Math.sqrt(10000.0/b.getM()-Math.pow(100.0/(2*b.getM()),2))*time);
        return new Body(r,0.0,0.0,0.0,b.getM(),"");
//        return null;
    }
}
