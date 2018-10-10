package ar.edu.itba.ss.Types;

public class Collision {
    private Vector position;
    private Double superposition;

    public Collision(Vector position, Double superposition) {
        this.position = position;
        this.superposition = superposition;
    }

    public Vector getPosition() {
        return position;
    }

    public Double getSuperposition() {
        return superposition;
    }
}
