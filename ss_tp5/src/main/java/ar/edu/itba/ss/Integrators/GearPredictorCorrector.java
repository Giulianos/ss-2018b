package ar.edu.itba.ss.Integrators;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Forces.Force;
import ar.edu.itba.ss.Types.Vector;

public class GearPredictorCorrector implements Integrator {

    public Body calculate(Body b, Double dt, Force f) {

        Vector[] rs = new Vector[6];
        rs[0] = b.getPosition();
        rs[1] = b.getVelocity();

        for(int i = 2; i < rs.length ; i++) {
            rs[i] = f.evaluate(rs[i-2],rs[i-1]).divide(b.getMass());
        }

        Vector[] rPredicted = new Vector[6];

        for(int i = 0; i < rPredicted.length; i++){

            rPredicted[i] = new Vector(0.0,0.0);

            for(int j = i, k = 0; j < rs.length ; j++, k++){

                rPredicted[i] = rPredicted[i].add(
                        rs[j].multiply(
                                Math.pow(dt,k)
                        ).divide(
                                factorial(k)
                        )
                );
            }
        }

        Vector dR2 = rs[2].add(rPredicted[2].multiply(-1.0)).multiply(dt*dt).divide(2.0);

        Double alpha[] = {3.0/20,251.0/360,1.0,11.0/18,1.0/6,1.0/60};

        Vector[] rCorrected = new Vector[6];

        for(int i = 0; i < rCorrected.length; i++){
            rCorrected[i] = new Vector(0.0,0.0).add(
                    rPredicted[i]
            ).add(
                    dR2.multiply(
                            alpha[i]*Math.pow(dt,i)/factorial(i)
                    )
            );
        }

        return new Body(rCorrected[0],rCorrected[1],b.getMass(),b.getRadius());

    }

    private double factorial(int number) {
        int res=1;
        for(int i=number;i>0;i--){
            res *= i;
        }
        return res;
    }

    public String toString(){
        return "gear";
    }
}
