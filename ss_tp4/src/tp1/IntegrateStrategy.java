package tp1;

public abstract class IntegrateStrategy {

    protected double k;
    protected double deltaT;

    public IntegrateStrategy(double k, double deltaT) {
        this.k = k;
        this.deltaT = deltaT;
    }

    public abstract void calculateVelocity(Particle particle);

    public abstract void calculatePosition(Particle particle);

}
