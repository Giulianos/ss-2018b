package ar.edu.itba.ss.Particles;

import java.util.function.UnaryOperator;

public class Vector implements Cloneable{
    public Double x;
    public Double y;

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

    public Double norm2() {
        return Math.sqrt(x*x + y*y);
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

    public static double distanceBetween(Vector v1, Vector v2){
        return Math.sqrt(Math.pow(v2.x - v1.x,2)+Math.pow(v1.y - v2.y,2));

    }
}
