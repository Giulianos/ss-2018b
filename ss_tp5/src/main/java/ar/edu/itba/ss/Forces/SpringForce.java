package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Vector;

public class SpringForce implements Force{
    private Double k;
    private Double gamma;

    public SpringForce(Double k, Double gamma) {
        this.k = k;
        this.gamma = gamma;
    }

    public Double getGamma() {
        return gamma;
    }

    public Double getK() {
        return k;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        return new Vector(-k*position.x - gamma*velocity.x, 0.0);
    }
}
