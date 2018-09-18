package ss.g3.functions;


public class Force {
    private Double k;
    private Double gamma;

    public Force(Double k, Double gamma) {
        this.k = k;
        this.gamma = gamma;
    }

    public Double evaluate(Double r, Double r1) {
        return -k*r - gamma*r1;
    }
}
