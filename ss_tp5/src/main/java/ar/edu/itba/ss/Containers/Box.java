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

    private Double wallCollisionSuperposition(Body body, WallCollisionType type) {
        Double x = body.getPosition().x;
        Double y = body.getPosition().y;
        Double r = body.getRadius();
        if(type == WallCollisionType.VERTICAL) {
            if(x-r <= 0.0) {
                return r-x;
            } else if(x+r >= width) {
                return r-(x-width);
            }
        } else if(y-r <= 0.0) {
            return r-y;
        }
        return null;
    }

    @Override
    public Set<Collision> getWallCollision(Body body) {
        Set<Collision> wallCollision = new HashSet<>();

        // Check if there is a collision against a vertical wall
        Double superPosition = wallCollisionSuperposition(body, WallCollisionType.VERTICAL);
        if(superPosition !=  null) {
            wallCollision.add(new Collision(wallCollisionPosition(body, WallCollisionType.VERTICAL), superPosition));
        }

        // Check if there is a collision against a horizontal wall
        superPosition = wallCollisionSuperposition(body, WallCollisionType.HORIZONTAL);
        if(superPosition != null) {
            wallCollision.add(new Collision(wallCollisionPosition(body, WallCollisionType.HORIZONTAL), superPosition));
        }

        return wallCollision;
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
}
