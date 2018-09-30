package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Vector;

public interface Force {
    public Vector evaluate(Vector position, Vector velocity);

}
