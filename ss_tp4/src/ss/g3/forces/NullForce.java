package ss.g3.forces;

import ss.g3.types.Vector;

/**
 * Created by giulianoscaglioni on 23/9/18.
 */
public class NullForce implements Force {
    @Override
    public Vector evaluate(Vector position, Vector velocity) {
        return new Vector(0.0, 0.0);
    }
}
