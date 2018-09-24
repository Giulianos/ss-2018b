package ss.g3.forces;

import ss.g3.types.Vector;

import java.util.Set;

public class SumForce implements Force {
    private Set<Force> forces;
    private static Integer showed = 10;

    public SumForce(Set<Force> forces) {
        this.forces = forces;
    }

    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        Vector ret = new Vector(0.0, 0.0);

        if(showed>0)
            System.err.println("Got a sum of " + forces.size() + " forces to sum");

        for(Force f : forces) {
            Vector evalForce = f.evaluate(position, velocity);
            ret = ret.add(evalForce);
            if(showed>0) {
                System.err.println(" -" + evalForce);
            }
        }

        if(showed > 0) {
            showed--;
            System.err.println("Resulting force: " + ret);
        }


        return ret;
    }
}
