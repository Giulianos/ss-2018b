package ss.g3;

public class Body {
    private Double x;
    private Double y;
    private Double vx;
    private Double vy;
    private Double m;

    public Body(Double x, Double y, Double vx, Double vy, Double m) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.m = m;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getVx() {
        return vx;
    }

    public Double getVy() {
        return vy;
    }

    public Double getM() {
        return m;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setVx(Double vx) {
        this.vx = vx;
    }

    public void setVy(Double vy) {
        this.vy = vy;
    }

    @Override
    public String toString() {
        return x.toString() + "\t" + y.toString();
    }
}
