package ss.g3.integrators;

import ss.g3.functions.Acceleration;
import ss.g3.Body;
import ss.g3.functions.Force;

/**
 * Created by giulianoscaglioni on 18/9/18.
 */
public class Beeman implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {
        Integrator euler = new Euler();
        Body b2 = euler.calculate(b, -1*dt, f, a);
        Double r = b.getX() + b.getVx()*dt + (2/3) * a.evaluate(b.getX(), b.getVx())*Math.pow(dt, 2) - (1/6)*a.evaluate(b2.getX(), b2.getVx())*Math.pow(dt, 2);
        Double vPredicted = b.getVx() + (3/2) * a.evaluate(b.getX(), b.getVx())*dt - (1/2) * a.evaluate(b2.getX(), b2.getVx())*dt;
        Double vCorrected = b.getVx() + (1/3) * a.evaluate(r, vPredicted) * dt + (5/6) * a.evaluate(b.getX(), b.getVx()) * dt - (1/6) * a.evaluate(b2.getX(), b2.getVx())*dt;

        return new Body(r, 0.0, vCorrected, 0.0, b.getM());
    }
}
