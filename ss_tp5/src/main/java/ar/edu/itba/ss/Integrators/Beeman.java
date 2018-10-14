package ar.edu.itba.ss.Integrators;

import ar.edu.itba.ss.Forces.Force;
import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Types.Vector;

public class Beeman implements Integrator {
    @Override
    public void calculate(Body b, Double dt, Force f) {

        Vector rCurrent = b.getPosition();
        Vector vCurrent = b.getVelocity();
        Vector aCurrent = b.getAcceleration();
        Vector aPrevious = b.getPreviousAcceleration();
        Vector aFuture = f.evaluate(rCurrent, vCurrent);

        Vector rFuture = rCurrent.add(
          vCurrent.multiply(dt).add(
                  aCurrent.multiply(2.0/3.0).diff(aPrevious.multiply(1.0/1.6)).multiply(dt*dt)
          )
        );

        Vector vFuture = vCurrent.add(
                aFuture.multiply(1.0/3.0).add(aCurrent.multiply(5.0/6.0)).diff(aPrevious.multiply(1.0/6.0)).multiply(dt)
        );

        b.setPosition(rFuture);
        b.setVelocity(vFuture);
        b.setAcceleration(aFuture);
    }

    public String toString(){
        return "beeman";
    }
}