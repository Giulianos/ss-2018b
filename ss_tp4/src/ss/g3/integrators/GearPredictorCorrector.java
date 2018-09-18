package ss.g3.integrators;

import ss.g3.Body;
import ss.g3.functions.Acceleration;
import ss.g3.functions.Force;

public class GearPredictorCorrector implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {
        Double rPred = b.getX();
        Double r1Pred = b.getVx();
        Double r2Pred = a.evaluate(rPred, r1Pred);
        Double r3Pred = (-f.getK() * r1Pred - f.getGamma()*r2Pred) / b.getM();
        Double r4Pred = (-f.getK() * r2Pred - f.getGamma()*r3Pred) / b.getM();
        Double r5Pred = (-f.getK() * r3Pred - f.getGamma()*r4Pred) / b.getM();

        Double r = rPred + r1Pred*dt + r2Pred*Math.pow(dt, 2)/2 + r3Pred*Math.pow(dt, 3)/6 + r4Pred*Math.pow(dt, 4)/24 + r5Pred*Math.pow(dt, 5)/120;
        Double r1 = r1Pred + r2Pred*dt + r3Pred*Math.pow(dt, 2)/2 + r4Pred*Math.pow(dt, 3)/6 + r5Pred*Math.pow(dt, 4)/24;
        Double r2 = r2Pred + r3Pred*dt + r4Pred*Math.pow(dt, 2)/2 + r5Pred*Math.pow(dt, 3)/6;
        Double r3 = r3Pred + r4Pred*dt + r5Pred*Math.pow(dt, 2)/2;
        Double r4 = r4Pred + r5Pred*dt;
        Double r5 = r5Pred;

        Double dR2 = (r2 - r2Pred) * Math.pow(dt, 2) / 2;

        
    }
}
