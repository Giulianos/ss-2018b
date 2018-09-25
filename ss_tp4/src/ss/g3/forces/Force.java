package ss.g3.forces;

import ss.g3.types.Vector;

public interface Force {
    public Vector evaluate(Vector position, Vector velocity);

}
