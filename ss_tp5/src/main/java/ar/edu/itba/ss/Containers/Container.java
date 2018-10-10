package ar.edu.itba.ss.Containers;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Types.Collision;

import java.util.Set;

public interface Container {

    /**
     * Calculates collisions between body and
     * the container's walls.
     * @param body
     * @return a collision if there's one, null otherwise
     */
    public Set<Collision> getWallCollision(Body body);

    /**
     * The bodies that represent opening edges
     * @return A set with the bodies
     */
    public Set<Body> getOpeningBodies();

    /**
     * The width of the container at
     * the specified depth
     * @param depth
     * @return The width of the container
     */
    public Double getWidth(Double depth);

    /**
     * The height of the container
     * @return
     */
    public Double getHeight();
}
