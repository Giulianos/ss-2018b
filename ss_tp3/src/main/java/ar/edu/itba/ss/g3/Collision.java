package ar.edu.itba.ss.g3;

public class Collision {
    private Particle p1;
    private Particle p2;
    private CollisionType type;
    private WallType wallType;

    public enum CollisionType { PARTICLE, WALL };
    public enum WallType { TOP, BOTTOM, RIGHT, LEFT };

    public Collision(Particle p1, Particle p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.type = CollisionType.PARTICLE;
    }

    public Collision(Particle p1, WallType wallType) {
        this.p1 = p1;
        this.type = CollisionType.WALL;
        this.wallType = wallType;
    }

    public Particle getP1() {
        return p1;
    }

    public Particle getP2() {
        return p2;
    }

    public CollisionType getType() {
        return type;
    }

    public WallType getWallType() {
        return wallType;
    }
}
