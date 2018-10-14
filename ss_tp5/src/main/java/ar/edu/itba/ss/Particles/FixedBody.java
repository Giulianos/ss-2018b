package ar.edu.itba.ss.Particles;

import ar.edu.itba.ss.Types.Vector;

public class FixedBody extends Body {

    public FixedBody(Vector position, Vector velocity, Double mass, Double radius) {
        super(position, velocity, mass, radius);
    }

    public FixedBody(Double x, Double y, Double vx, Double vy, Double mass, Double radius) {
        super(x, y, vx, vy, mass, radius);
    }

    @Override
    public Boolean isFixed() {
        return true;
    }

    @Override
    public void update() {
    }

    @Override
    public Vector getPreviousPosition() {
        return getPosition();
    }

    @Override
    public Vector getPreviousVelocity() {
        return getVelocity();
    }

    @Override
    public Vector getFuturePosition() {
        return getPosition();
    }

    @Override
    public Vector getFutureVelocity() {
        return getVelocity();
    }
}
