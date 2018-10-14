package ar.edu.itba.ss.Types;

import java.util.function.UnaryOperator;

public class Vector implements Cloneable {
    public Double x;
    public Double y;
    private Double norm2 = null;

    private static final Vector nullVector = new Vector(0.0, 0.0);

    public Vector(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Vector divide(final Double divider) {
        return apply((x)->x/divider);
    }

    public Vector multiply(final Double factor) {
        return apply((x)->x*factor);
    }

    public Vector add(final Vector v2) {
        return new Vector(x+v2.x, y+v2.y);
    }

    public Vector diff(final Vector v2) {
        return new Vector(x-v2.x, y-v2.y);
    }

    public Double dot(final Vector v2) {
        return x*v2.x + y*v2.y;
    }

    public Vector rotate(final Double angle) {
        /**
         * Transformation matrix:
         * 
         * | cos(angle) -sin(angle) |
         * | sin(angle)  cos(angle) |
         * 
         */
        return new Vector(x*Math.cos(angle) - y*Math.sin(angle), x*Math.sin(angle) + y*Math.cos(angle));
    }

    public Vector direction() {
        return new Vector(x/norm2(), y/norm2());
    }

    public Double norm2() {
        if(norm2 == null) {
            return (this.norm2=Math.sqrt(x*x + y*y));
        }
        return this.norm2;
    }

    public Double distanceTo(Vector v2) {
        return diff(v2).norm2();
    }

    public Vector apply(UnaryOperator<Double> operator) {
        return new Vector(operator.apply(x), operator.apply(y));
    }

    public Vector clone() {
        return new Vector(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static Vector getNullVector() {
        return nullVector;
    }
}
