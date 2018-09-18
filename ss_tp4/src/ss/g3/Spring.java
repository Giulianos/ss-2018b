package ss.g3;

import ss.g3.functions.Acceleration;
import ss.g3.functions.Force;
import ss.g3.integrators.*;

public class Spring {
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
        Integrator integrator = new Beeman();
        Force f = new Force(k, gamma);
        Acceleration a = new Acceleration(k, gamma, body.getM());

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f, a);
            System.out.println(time + "\t" + body);
            time+=dt;
        }
    }

    void simulateGearPredictorCorrector(Double dt, Double totalTime) {
        Integrator integrator = new GearPredictorCorrector();
        Force f = new Force(k, gamma);
        Acceleration a = new Acceleration(k, gamma, body.getM());

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f, a);
            System.out.println(time + "\t" + body);
            time+=dt;
        }
    }

    void simulateReal(Double dt, Double totalTime) {
        Integrator integrator = new Real();
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

    void simulateError(Integrator integrator, Double dt, Double totalTime){
        Integrator real = new Real();
        Force f = new Force(k, gamma);
        Acceleration a = new Acceleration(k, gamma, body.getM());

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f, a);
            double aux = Math.pow(body.getX()-real.calculate(body,dt,f,a).getX(),2)/(time/dt);
            System.out.println(time + "\t" + aux);
            time+=dt;
        }
    }

}
