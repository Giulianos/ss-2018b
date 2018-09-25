package ss.g3;

import ss.g3.forces.SpringForce;
import ss.g3.integrators.*;
import ss.g3.types.Body;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

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
            System.out.println(time + "\t" + body);
            time+=dt;
        }
    }

    void simulateBeeman(Double dt, Double totalTime) throws IOException {

        BufferedWriter writer=initalizeBW("/Users/florenciacavallin/Documents/4rto-1cuatri/SS/tp/ss/ss_tp4");

        Integrator integrator = new Beeman();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            appendToFile(writer,time + "\t" + body + "\n");
            time+=dt;
        }
        closeBW(writer);
    }

    private BufferedWriter initalizeBW( String outPath) {
        BufferedWriter bw;
        try {

            bw = new BufferedWriter(new FileWriter(outPath+"/spring" + LocalDateTime.now() + ".txt", true));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return bw;
    }

    private void closeBW(BufferedWriter bw) {
        if (bw != null) try {
            bw.flush();
            bw.close();
        } catch (IOException ioe2) {
            // just ignore it
        }
    }

    public static void appendToFile (BufferedWriter bw , String data) {
        try {
            bw.write(data);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void simulateGearPredictorCorrector(Double dt, Double totalTime) {
        BufferedWriter writer=initalizeBW("/Users/florenciacavallin/Documents/4rto-1cuatri/SS/tp/ss/ss_tp4");
        Integrator integrator = new GearPredictorCorrector();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            appendToFile(writer,time + "\t" + body + "\n");
//            System.out.println(time + "\t" + body);
            time+=dt;
        }
        closeBW(writer);
    }

    void simulateReal(Double dt, Double totalTime) {
        BufferedWriter writer=initalizeBW("/Users/florenciacavallin/Documents/4rto-1cuatri/SS/tp/ss/ss_tp4");
        Integrator integrator = new Real();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            appendToFile(writer,time + "\t" + body + "\n");
//            System.out.println(time + "\t" + body);
            time+=dt;
        }
        closeBW(writer);
    }

    void simulateVerlet(Double dt, Double totalTime) {
        BufferedWriter writer=initalizeBW("/Users/florenciacavallin/Documents/4rto-1cuatri/SS/tp/ss/ss_tp4");
        Integrator integrator = new Verlet();
        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            appendToFile(writer,time + "\t" + body + "\n");
//            System.out.println(time + "\t" + body);
            time+=dt;
        }
        closeBW(writer);
    }

    void simulateError(Integrator integrator, Double dt, Double totalTime){
        Integrator real = new Real();
        SpringForce f = new SpringForce(k, gamma);
        double error = 0;
        Double time = 0.0;
        int i = 0;
        while(time < totalTime) {
            body = integrator.calculate(body, dt, f);
            error += Math.pow(body.getPosition().x-real.calculate(body,dt,f).getPosition().x,2);
 //           System.out.println(time + "\t" + auxX);
            time+=dt;
            i++;
        }
        System.out.println(error/i);
    }

}
