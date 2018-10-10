package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Types.Vector;

import java.util.Set;

public class SumForce implements Force {
    private Set<Force> forces;

    public SumForce(Set<Force> forces) {
        this.forces = forces;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        Vector ret = new Vector(0.0, 0.0);
        for(Force f : forces) {
            Vector evalForce = f.evaluate(position, velocity);
            ret = ret.add(evalForce);
        }
        return ret;
    }
}
