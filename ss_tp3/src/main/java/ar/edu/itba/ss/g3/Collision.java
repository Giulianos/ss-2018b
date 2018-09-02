package ar.edu.itba.ss.g3;

public class Collision {
    private Particle p1;
    private Particle p2;
    private CollisionType type;
    private WallType wallType;
    private double timeToCollision;
    private Double j;

    public enum CollisionType { PARTICLE, WALL };
    public enum WallType { VERTICAL, HORIZONTAL };

    public Collision(Particle p1, Particle p2, double timeToCollision) {
        this.p1 = p1;
        this.p2 = p2;
        this.timeToCollision = timeToCollision;
        this.type = CollisionType.PARTICLE;
    }

    public Collision(Particle p1, WallType wallType, double timeToCollision) {
        this.p1 = p1;
        this.type = CollisionType.WALL;
        this.timeToCollision = timeToCollision;
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

    public double getTimeToCollision() {
        return timeToCollision;
    }

    private double getJ() {
        if (j != null)
            return j;
        j = 2 * p1.getM() * p2.getM() * Collision.getVR(p1, p2);
        double sigma = getSigma(p1, p2);
        j /= sigma * (p1.getM() + p2.getM());
        return j;
    }

    public double getJx() {
        return (getJ() * getDeltaX(p1, p2)) / getSigma(p1, p2);
    }

    public double getJy() {
        return (getJ() * getDeltaY(p1, p2)) / getSigma(p1, p2);
    }

    public static double getVR(Particle a, Particle b) {
        double deltaVX = b.getVx() - a.getVx();
        double deltaVY = b.getVy() - a.getVy();

        double deltaX = getDeltaX(a, b);
        double deltaY = getDeltaY(a, b);

        return deltaVX * deltaX + deltaVY * deltaY;
    }

    public static double getDeltaX(Particle a, Particle b) {
        return b.getX() - a.getX();
    }

    public static double getDeltaY(Particle a, Particle b) {
        return b.getY() - a.getY();
    }

    public static double getSigma(Particle a, Particle b) {
        return a.getR() + b.getR();
    }

}
