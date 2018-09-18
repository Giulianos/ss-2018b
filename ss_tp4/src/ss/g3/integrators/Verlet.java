package ss.g3.integrators;

import ss.g3.functions.Acceleration;
import ss.g3.Body;
import ss.g3.functions.Force;

public class Verlet implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {
        Double rx = b.getX() + dt * b.getVx() + Math.pow(dt, 2) * f.evaluate(b.getX(), b.getVx()) / b.getM();
        Double vx2 = b.getVx() + a.evaluate(b.getX(), b.getVx())*(dt/2);
        Double vx = vx2 + a.evaluate(rx, vx2)*(dt/2);

        Double ry = b.getY() + dt * b.getVy() + Math.pow(dt, 2) * f.evaluate(b.getY(), b.getVy()) / b.getM();
        Double vy2 = b.getVy() + a.evaluate(b.getY(), b.getVy())*(dt/2);
        Double vy = vy2 + a.evaluate(ry, vy2)*(dt/2);

        return new Body(rx, ry, vx, vy, b.getM());
    }
}
