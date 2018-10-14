package ar.edu.itba.ss.Containers;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.FixedBody;
import ar.edu.itba.ss.Types.Collision;
import ar.edu.itba.ss.Types.Vector;
import ar.edu.itba.ss.Types.WallCollisionType;

import java.util.HashSet;
import java.util.Set;

public class Box implements Container {
    private Double height;
    private Double width;
    private Double openingDiameter;
    private Set<Body> openingEdgeBodies;

    public Box(Double openingDiameter, Double height, Double width) {
        this.height = height;
        this.width = width;
        this.openingDiameter = openingDiameter;
        this.openingEdgeBodies = new HashSet<>();
        this.openingEdgeBodies.add(
                new FixedBody(new Vector(width/2 - openingDiameter/2,0.0),new Vector(0.0,0.0), Double.MAX_VALUE,0.0)
        );
        this.openingEdgeBodies.add(
                new FixedBody(new Vector(width/2 + openingDiameter/2,0.0),new Vector(0.0,0.0), Double.MAX_VALUE,0.0)
        );
    }

    private Vector wallCollisionPosition(Body body, WallCollisionType type) {
        /**
         * Box diagram:
         *
         *              _ height
         *   |         |
         *   |   box   |
         *   |         |
         *   |___   ___|_ 0
         *   |   |  |  |
         *   0  x1 x2  width
         *
         *   x1 = width/2 - openingDiameter/2
         */

        Double x = body.getPosition().x;
        Double y = body.getPosition().y;
        Double r = body.getRadius();
        Double x1 = width/2-openingDiameter/2;
        Double x2 = width/2+openingDiameter/2;

        if(x-r > x1 && x+r < x2) {
            return null;
        }

        if(type == WallCollisionType.VERTICAL) {
            if(x-r <= 0.0) {
                return new Vector(0.0, y);
            } else if (x+r >= width) {
                return new Vector(width, y);
            }
        } else {
            if(y-r <= 0.0) {
                return new Vector(x, 0.0);
            }
        }

        return null;
    }

    @Override
    public Set<Body> getWallCollision(Body body) {
        Set<Body> bodies = new HashSet<>();

        // Check if there is a collision against a vertical wall
        Vector position = wallCollisionPosition(body, WallCollisionType.VERTICAL);
        if(position != null) {
            bodies.add(new Body(position, Vector.getNullVector(), 0.0, 0.0));
        }

        // Check if there is a collision against a horizontal wall
        position = wallCollisionPosition(body, WallCollisionType.HORIZONTAL);
        if(position != null) {
            bodies.add(new Body(position, Vector.getNullVector(), 0.0, 0.0));
        }

        return bodies;
    }

    @Override
    public Set<Body> getOpeningBodies() {
        return openingEdgeBodies;
    }

    @Override
    public Double getWidth(Double depth) {
        return this.width;
    }

    @Override
    public Double getHeight() {
        return this.height;
    }

    @Override
    public Boolean touchesWall(Body b) {
        // Check if there is a collision against a vertical wall
        Vector position = wallCollisionPosition(b, WallCollisionType.VERTICAL);
        if(position != null) {
            return true;
        }

        // Check if there is a collision against a horizontal wall
        position = wallCollisionPosition(b, WallCollisionType.HORIZONTAL);
        if(position != null) {
           return true;
        }

        return false;
    }
}
