package ss.g3.integrators;

import ss.g3.functions.Acceleration;
import ss.g3.Body;
import ss.g3.functions.Force;

public class Beeman implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {
        Integrator euler = new Euler();
        Body b2 = euler.calculate(b, -dt, f, a);
        Double rx = b.getX() + b.getVx()*dt + (2.0/3) * a.evaluate(b.getX(), b.getVx()) * Math.pow(dt, 2) - (1.0/6) * a.evaluate(b2.getX(), b2.getVx()) * Math.pow(dt, 2);
        Double vxPredicted = b.getVx() + (3.0/2) * a.evaluate(b.getX(), b.getVx()) * dt - (1.0/2) * a.evaluate(b2.getX(), b2.getVx()) * dt;
        Double vxCorrected = b.getVx() + (1.0/3) * a.evaluate(rx, vxPredicted) * dt + (5.0/6) * a.evaluate(b.getX(), b.getVx()) * dt - (1.0/6) * a.evaluate(b2.getX(), b2.getVx()) * dt;

        Double ry = b.getY() + b.getVy()*dt + (2.0/3) * a.evaluate(b.getY(), b.getVy()) * Math.pow(dt, 2) - (1.0/6) * a.evaluate(b2.getY(), b2.getVy()) * Math.pow(dt, 2);
        Double vyPredicted = b.getVy() + (3.0/2) * a.evaluate(b.getY(), b.getVy()) * dt - (1.0/2) * a.evaluate(b2.getY(), b2.getVy()) * dt;
        Double vyCorrected = b.getVy() + (1.0/3) * a.evaluate(ry, vyPredicted) * dt + (5.0/6) * a.evaluate(b.getY(), b.getVy()) * dt - (1.0/6) * a.evaluate(b2.getY(), b2.getVy()) * dt;

        return new Body(rx, ry, vxCorrected, vyCorrected, b.getM());
    }
}
