package tp1;

public class Particle {
    private static int nextID = 0;

    private final int id;
    private final double radius;
    private final double mass;
    private double position;
    private double oldPosition;
    private double velocity;
    private double oldVelocity;
    private double acceleration;

    public Particle(double radius, double mass, double position, double velocity) {
        this.id = nextID++;
        this.radius = radius;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.oldPosition = -1;
        this.oldVelocity = -1;
    }

    public int getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public double getPosition() {
        return position;
    }

    public double getOldPosition() { return oldPosition; }

    public double getOldVelocity() {
        return oldVelocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setPosition(double position) {
        this.oldPosition = this.position;
        this.position = position;
    }

    public void setVelocity(double velocity) {
        this.oldVelocity = this.velocity;
        this.velocity = velocity;
    }
}