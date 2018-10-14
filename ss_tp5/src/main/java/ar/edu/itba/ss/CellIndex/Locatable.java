package ar.edu.itba.ss.CellIndex;

import ar.edu.itba.ss.Types.Vector;

/**
 * Created by giulianoscaglioni on 14/10/18.
 */

/**
 * Provides a inteface to define locatable (has position)
 * behavior on an Object.
 */
public interface Locatable {
    /**
     * Returns the position of the object
     * @return
     */
    public Vector getPosition();
}
