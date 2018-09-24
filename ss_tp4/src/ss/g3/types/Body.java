package ss.g3.types;

public class Body {
    private Vector position;
    private Vector velocity;
    private String tag;
    private Double m;

    public Body(Double x, Double y, Double vx, Double vy, Double m, String tag) {
        this.position = new Vector(x, y);
        this.velocity = new Vector(vx, vy);
        this.m = m;
        this.tag = tag;
    }

    public Body(Vector position, Vector velocity, Double m, String tag) {
        this.position = position.clone();
        this.velocity = velocity.clone();
        this.m = m;
        this.tag = tag;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Double getM() {
        return m;
    }

    public String getTag() {
        return tag;
    }

    public Integer stringToNum() {
        switch(tag) {
            case "jupiter":
                return 1;
            case "saturn":
                return 2;
            case "sun":
                return 3;
            case "earth":
                return 4;
            case "spaceship":
                return 5;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        //return (tag != null ? tag : " ") + ", " + position.x.toString() + ", " + position.y.toString();
        return stringToNum() + ", " + position.x.toString() + ", " + position.y.toString();
    }
}
