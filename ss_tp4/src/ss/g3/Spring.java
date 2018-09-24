package ss.g3;

import ss.g3.forces.SpringForce;
import ss.g3.integrators.*;
import ss.g3.types.Body;

public class Spring {
    private Body body;
    private Double k;
    private Double gamma;

    public Spring(double mass, double k, double gamma, double x0, double y0, double vx0, double vy0) {
        body = new Body(x0, y0, vx0, vy0, mass, null);
        this.k = k;
        this.gamma = gamma;
    }


    void simulateEuler(Double dt, Double totalTime) {
        Integrator integrator = new  Euler();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            System.out.println(time + ", " + body);
            time+=dt;
        }
    }

    void simulateBeeman(Double dt, Double totalTime) {
        Integrator integrator = new Beeman();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            System.out.println(time + ", " + body);
            time+=dt;
        }
    }

    void simulateGearPredictorCorrector(Double dt, Double totalTime) {
        Integrator integrator = new GearPredictorCorrector();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            System.out.println(time + ", " + body);
            time+=dt;
        }
    }

    void simulateReal(Double dt, Double totalTime) {
        Integrator integrator = new Real();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            System.out.println(time + ", " + body);
            time+=dt;
        }
    }

    void simulateVerlet(Double dt, Double totalTime) {
        Integrator integrator = new Verlet();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            System.out.println(time + ", " + body);
            time+=dt;
        }
    }

    void simulateError(Integrator integrator, Double dt, Double totalTime){
//        Integrator real = new Real();
//        SpringForce f = new SpringForce(k, gamma);
//
//        Double time = 0.0;
//        while(time < totalTime) {
//            body = integrator.calculate(body, dt, f, a);
//            double auxX = Math.pow(body.getX()-real.calculate(body,dt,f).getX(),2)/(time/dt);
//            double auxY = Math.pow(body.getY()-real.calculate(body,dt,f).getY(),2)/(time/dt);
//            System.out.println(time + ", " + auxX + "\t" +auxY);
//            time+=dt;
//        }
    }

}
