package ss.g3.integrators;

import ss.g3.functions.Acceleration;
import ss.g3.Body;
import ss.g3.functions.Force;

public class Euler implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {

        Double vx = b.getVx() + dt * f.evaluate(b.getX(), b.getVx()) / b.getM();
        Double rx = b.getX() + dt * vx + Math.pow(dt, 2) * f.evaluate(b.getX(), b.getVx())/(2*b.getM());

        Double vy = b.getVy() + dt * f.evaluate(b.getY(), b.getVy()) / b.getM();
        Double ry = b.getY() + dt * vy + Math.pow(dt, 2) * f.evaluate(b.getY(), b.getVy())/(2*b.getM());

        return new Body(rx,ry, vx, vy, b.getM());
    }
}
