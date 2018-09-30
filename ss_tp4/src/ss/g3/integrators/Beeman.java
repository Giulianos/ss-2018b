package ss.g3.integrators;

import ss.g3.types.Body;
import ss.g3.forces.Force;
import ss.g3.types.Vector;

public class Beeman implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f) {

        Integrator euler = new Euler();
        Body b2 = euler.calculate(b, -dt, f);

        Vector rB = b.getPosition();
        Vector vB = b.getVelocity();
        Vector rB2 = b2.getPosition();
        Vector vB2 = b2.getVelocity();

        Vector a = f.evaluate(rB, vB).divide(b.getM());
        Vector a2 = f.evaluate(rB2, vB2).divide(b.getM());

        Vector r = rB
                    .add(
                            vB.multiply(dt)
                    )
                    .add(
                            a.multiply(2*dt*dt/3)
                    )
                    .add(
                            a2.multiply(-dt*dt/6)
                    );

        Vector vp = vB
                    .add(
                            a.multiply(3*dt/2)
                    )
                    .add(
                            a2.multiply(-dt/2)
                    );

        Vector a3 = f.evaluate(r, vp).divide(b.getM());

        Vector vc = vB
                    .add(
                            a3.multiply(dt/3)
                    )
                    .add(
                            a.multiply(5*dt/6)
                    )
                    .add(
                            a2.multiply(-dt/6)
                    );

      return new Body(r, vc, b.getM(), b.getTag());
    }

    public String toString(){
        return "beeman";
    }
}
