package tp1;


public class Beeman extends IntegrateStrategy {


    public Beeman(double k, double deltaT) {
        super(k, deltaT);
    }

    @Override
    public void calculateVelocity(Particle particle) {
    }

    @Override
    public void calculatePosition(Particle particle) {
        double x = particle.getPosition();
        double m = particle.getMass();
        double ox = particle.getOldPosition();
        double v = particle.getVelocity();
        x = x + v * deltaT + 2.0/3 * calculateAcceleration(x,m) * deltaT * deltaT - 1.0/6 * calculateAcceleration(ox,m) * deltaT * deltaT;
        particle.setPosition(x);
    }

    private double calculateAcceleration(double x, double m) {
        return k * x * x / (2 * m );
    }
}
