package ss.g3.integrators;

import ss.g3.Body;
import ss.g3.functions.Acceleration;
import ss.g3.functions.Force;

public class GearPredictorCorrector implements Integrator {
    @Override
    public Body calculate(Body b, Double dt, Force f, Acceleration a) {

        Double rxCorrected[] = predictNcorrect(b,dt,a,true);
        Double ryCorrected[] = predictNcorrect(b,dt,a,false);

        return new Body(rxCorrected[0],ryCorrected[0],rxCorrected[1],ryCorrected[1],b.getM());
    }

    private Double[] predictNcorrect(Body b, Double dt, Acceleration a, boolean axis){
        Double rPredicted[] = new Double[6];
        Double rCorrected[];

        if(axis) {
            rPredicted[0] = b.getX();
            rPredicted[1] = b.getVx();
        }else{
            rPredicted[0] = b.getY();
            rPredicted[1] = b.getVy();
        }

        for(int i = 2; i < rPredicted.length; i++){
            rPredicted[i] = a.evaluate(rPredicted[i - 2], rPredicted[i - 1]);
        }

        rCorrected = rPredicted.clone();

        for(int i = 0; i < rCorrected.length; i++){
            for(int j = i+1, h = 1; j < rPredicted.length; j++, h++){
                rCorrected[i] += rPredicted[j] * Math.pow(dt,h) / factorial(h);
            }
        }

        Double dR2 = (rCorrected[2] - rPredicted[2]) * Math.pow(dt, 2) / factorial(2);

        Double alpha[] = {3.0/20,251.0/360,1.0,11.0/18,1.0/6,1.0/60};

        for(int i = 0 ; i < rCorrected.length ; i++){
            rCorrected[i] += alpha[i] * dR2 * (factorial(i)/Math.pow(dt,i));
        }

        return rCorrected;
    }

    private double factorial(int number) {
        int res=1;
        for(int i=number;i>0;i--){
            res *= i;
        }
        return res;
    }
}
