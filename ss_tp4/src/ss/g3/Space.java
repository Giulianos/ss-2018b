package ss.g3;
import ss.g3.forces.Force;
import ss.g3.forces.GForce;
import ss.g3.forces.SumForce;
import ss.g3.integrators.Beeman;
import ss.g3.integrators.Integrator;
import ss.g3.types.Body;
import java.util.HashSet;
import java.util.Set;

public class Space {
    private double g = 6.693e-11;
    private Set<Body> bodies;

    public Space() {
        this.bodies = new HashSet<>();
        bodies.add(new Body(1000*0.0, 0.0, 0.0,0.0,1988500e24, "sun"));
        bodies.add(new Body(1.443667096759952E+11, -4.358205294442200E+10,
                8.134925514034244E+03,  2.840370427523522E+04, 5.97219E+24, "earth"));
        bodies.add(new Body(1.051808117769951E+11,  7.552752037839167E+11, -1.310827390034170E+04,
                2.413222412600538E+03, 1898.13E+24, "jupiter"));
        bodies.add(new Body(-1.075922360420873E+12,  8.540977704606439E+11,
                -6.538404943163649E+03, -7.592712627964003E+03, 5.6834E+26, "saturn"));
     }

    public void simulateSpace(Double dt, Double totalTime) {
        Integrator integrator = new Beeman();
        Double framesBetween = 86400.0/dt;

        Double time = 0.0;
        while(time < totalTime) {
            Set<Body> updatedBodies = new HashSet<>();
            Body currentBody;
            for(Body b : bodies) {
                if(b.getTag() != "sun") {
                    Set<Force> forces = calculateForces(b);
                    currentBody = integrator.calculate(b, dt, new SumForce(forces));
                    updatedBodies.add(currentBody);
                } else {
                    updatedBodies.add(b);
                }
            }
            bodies = updatedBodies;
            if(framesBetween <= 0.0) {
                printBodies();
                System.err.println("Completed: " + (time / totalTime) * 100 + "%");
                framesBetween = 86400.0/dt;
            }
            framesBetween-=1.0;
            time+=dt;
        }
    }

    public Set<Force> calculateForces(Body b){
        Set<Force> forces = new HashSet<>();
        for(Body b2 : bodies) {
            if(!b.equals(b2)) {
                forces.add(new GForce(b2, b));
            }
        }

        return forces;
    }

    public void printBodies() {
        System.out.println(bodies.size());
        System.out.println("some comment");
        for(Body b : bodies) {
            System.out.println(b);
        }
    }//83.813.080

    private Double kmDayToMSec(Double kmDay) {
        return 0.01157*kmDay;
    }
}
