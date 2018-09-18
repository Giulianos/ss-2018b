package ss.g3.functions;

public class Acceleration {
    private Double k;
    private Double gamma;
    private Double m;

    public Acceleration(Double k, Double gamma, Double m) {
        this.k = k;
        this.gamma = gamma;
        this.m = m;
    }

    public Double evaluate(Double r, Double r1) {
        return (-k*r - gamma*r1)/m;
    }
}