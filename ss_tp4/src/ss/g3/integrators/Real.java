package ss.g3.integrators;

import ss.g3.types.Body;
import ss.g3.forces.Force;
import ss.g3.types.Vector;

public class Real implements Integrator {
    double time = 0.0;
    double gamma = 100.0;
    double k = 10000.0;

    @Override
    public Body calculate(Body b, Double dt, Force f) {
        time += dt;
        Double r = Math.exp(-gamma * time / (2 * b.getM())) * Math.cos(Math.sqrt(k/b.getM()-Math.pow(gamma/(2*b.getM()),2))*time);
        return new Body(new Vector(r,0.0),new Vector(0.0,0.0),b.getM(),"");
    }

    public String toString(){
        return "real";
    }
}
