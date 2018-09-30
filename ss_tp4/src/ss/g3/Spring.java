package ss.g3;

import ss.g3.forces.SpringForce;
import ss.g3.integrators.*;
import ss.g3.types.Body;
import ss.g3.types.Vector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Spring {
    private Body body;
    private Double k;
    private Double gamma;

    public Spring(double mass, double k, double gamma, double x0, double y0, double vx0, double vy0) {
        body = new Body(new Vector(x0, y0),new Vector(vx0, vy0), mass, null);
        this.k = k;
        this.gamma = gamma;
    }

    void simulation(Double dt, Double totalTime, Integrator integrator){

        BufferedWriter writer=initalizeBW("/Users/florenciacavallin/Documents/4rto-2cuatri/SS/ss/ss_tp4/simulation/"+integrator.toString()+"_"+dt);

        SpringForce f = new SpringForce(k, gamma);

        Double time = 0.0;
        while(time < totalTime) {
            appendToFile(writer,time + "\t" + body.getPosition().x + "\n");
            body = integrator.calculate(body, dt, f);
            time+=dt;
        }
        closeBW(writer);
    }
    public static void appendToFile (BufferedWriter bw , String data) {
        try {
            bw.write(data);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void simulateError(Double dt, Double totalTime, Integrator integrator){
        BufferedWriter writer=initalizeBW("/Users/florenciacavallin/Documents/4rto-2cuatri/SS/ss/ss_tp4/simulation/"+"errores");

        SpringForce f = new SpringForce(k, gamma);
        Integrator real = new Real();
        Double time = 0.0;
        double error = 0;
        int i = 0;

        while(time < totalTime) {

            body = integrator.calculate(body, dt, f);
            error += Math.pow(body.getPosition().x-real.calculate(body,dt,f).getPosition().x,2);
            time+=dt;
            i++;

        }
        appendToFile(writer,integrator+"\t"+dt+"\t"+error/i+"\n");
        closeBW(writer);

    }

    void simulatedAll(Double totalTime){
        double[] dt = {1.0E-1,1.0E-2,1.0E-3,1.0E-4,1.0E-5,1.0E-6};

        for(int i = 0; i < dt.length ; i++){

            simulation(dt[i],totalTime,new Beeman());
            simulation(dt[i],totalTime,new Verlet());
            simulation(dt[i],totalTime,new GearPredictorCorrector());
            simulation(dt[i],totalTime,new Real());

            simulateError(dt[i],totalTime,new Beeman());
            simulateError(dt[i],totalTime,new Verlet());
            simulateError(dt[i],totalTime,new GearPredictorCorrector());
            simulateError(dt[i],totalTime,new Real());
        }
    }

    private BufferedWriter initalizeBW( String outPath) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(outPath+".txt", true));
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

}
