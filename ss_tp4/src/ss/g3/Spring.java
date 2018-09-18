package ss.g3;

import ss.g3.functions.Acceleration;
import ss.g3.functions.Force;
import ss.g3.integrators.Beeman;
import ss.g3.integrators.Euler;
import ss.g3.integrators.Integrator;
import ss.g3.integrators.Verlet;

public class Spring {
    // TODO: change velocity value
    private Body body = new Body(1.0, 0.0, 0.7142857143, 0.0, 70.0);
    private Double k = 10000.0;
    private Double gamma = 100.0;


    void simulateEuler(Double dt, Double totalTime) {
        Integrator integrator = new  Euler();
        Force f = new Force(k, gamma);
        Acceleration a = new Acceleration(k, gamma, body.getM());

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f, a);
            System.out.println(time + "\t" + body);
            time+=dt;
        }
    }

    void simulateBeeman(Double dt, Double totalTime) {
        Integrator integrator = new  Beeman();
        Force f = new Force(k, gamma);
        Acceleration a = new Acceleration(k, gamma, body.getM());

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f, a);
            System.out.println(time + "\t" + body);
            time+=dt;
        }
    }

    void simulateVerlet(Double dt, Double totalTime) {
        Integrator integrator = new Verlet();
        Force f = new Force(k, gamma);
        Acceleration a = new Acceleration(k, gamma, body.getM());

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f, a);
            System.out.println(time + "\t" + body);
            time+=dt;
        }
    }

}
