package ar.edu.itba.ss.g3;

public class Collision {
    final Particle p1;
    final Particle p2;
    final double seconds;
    WallType wallType;
    Double j;

    public enum WallType { VERTICAL, HORIZONTAL }

    public Particle getP1() {
        return p1;
    }

    public Particle getP2() {
        return p2;
    }

    public double getSeconds() {
        return seconds;
    }

    public boolean isWallType() {
        return wallType != null;
    }

    public boolean horizontalWallCollition() {
        return wallType == WallType.HORIZONTAL;
    }

    public boolean verticalWallCollition() {
        return wallType == WallType.VERTICAL;
    }

    public Collision(Particle p1, Particle p2, double collisionTime) {
        this.p1 = p1;
        this.p2 = p2;
        this.seconds = collisionTime;
        this.wallType = null;
        this.j = null;
        if(seconds<0) {
            throw new IllegalStateException();
        }
    }

    public Collision(Particle p1, double collisionTime, WallType wallType) {
        this.p1 = p1;
        this.p2 = null;
        this.seconds = collisionTime;
        this.wallType = wallType;
        this.j = null;
        if(collisionTime<0) {
            throw new IllegalStateException();
        }
    }

    public double getJ() {
        if (isWallType()) {
            throw new IllegalArgumentException();
        }
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
